package digitalcity.demeyert.overstockmanager.filters;

import digitalcity.demeyert.overstockmanager.utils.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Sortir le token de la requete (header Authorization)
        String token = jwtProvider.extractToken(request);

        // Verifier la validité du token (algo, secret, claims particuliers)
        if( token != null && jwtProvider.validate(token) ) {

            // Créer l'Authentication
            Authentication auth = jwtProvider.generateAuth(token);

            // Mettre l'Authentication dans le contexte de securité
            SecurityContextHolder.getContext().setAuthentication( auth );

        }


        filterChain.doFilter(request, response);

    }
}
