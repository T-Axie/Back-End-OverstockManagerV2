package digitalcity.demeyert.overstockmanager.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import digitalcity.demeyert.overstockmanager.model.dto.KeyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    //    private final String KEY = "Y2nr@Svs1#Rume@v9^r$$hwTm563QpxLfdDTO$VdLLMnNRJmcQ";
//    private final String PREFIX = "Bearer ";
//    private final long EXPIRED_AFTER = 846_000;
//    private final String HEADER_KEY = "Authorization";
//    Logger log = LoggerFactory.getLogger(JwtProvider.class);
    private final JwtProperties properties;
    private final UserDetailsService service;

    public JwtProvider(JwtProperties properties, UserDetailsService service) {
        this.properties = properties;
        this.service = service;
    }

    public KeyDTO createToken(Authentication auth){
        KeyDTO key = new KeyDTO();
        key.setKey( JWT.create()
                // Declarer les claims du payload
                .withExpiresAt( Instant.now().plusSeconds(properties.getExpiresAt()) )
                .withSubject(auth.getName())
                .withClaim(
                        "roles",
                        auth.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                // Declarer la signature
                .sign( Algorithm.HMAC512(properties.getSecret()) ));
        return key;
    }

    public String extractToken(HttpServletRequest request){
        String authHeader = request.getHeader(properties.getHeaderKey());
        return authHeader == null ? null : authHeader.replace(properties.getHeaderPrefix(), "");
    }

    public boolean validate(String token) {
        try{
            /* DecodedJWT decodedJWT = */ JWT.require( Algorithm.HMAC512(properties.getSecret()) )
                    .acceptExpiresAt(properties.getExpiresAt())
                    .build().verify(token);

            // d'autres verification par rapport au claims peuvent encore être réalisées
//            return decodedJWT.getSubject().startsWith("C");
            // par exemple

            return true;
        }
        catch (JWTVerificationException ex){
            log.warn(ex.getMessage());
            return false;
        }

    }

    public Authentication generateAuth(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);

        UserDetails user = service.loadUserByUsername( decodedJWT.getSubject() );

        return new UsernamePasswordAuthenticationToken(
                decodedJWT.getSubject(),
                null, // parce ce qu'on en a pas besoin
                user.getAuthorities() // est-ce que ca vaut la peine de mettre les roles dans le token ??
        );
    }
}
