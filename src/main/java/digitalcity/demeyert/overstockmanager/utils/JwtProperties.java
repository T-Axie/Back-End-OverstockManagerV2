package digitalcity.demeyert.overstockmanager.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("application.jwt")
public class JwtProperties implements InitializingBean {

    //    @Value("${application.jwt.secret}")
    private String secret;
    //    @Value("${application.jwt.expiresAt}")
    private long expiresAt;
    //    @Value("${application.jwt.headerPrefix}")
    private String headerPrefix;
    //    @Value("${application.jwt.headerKey}")
    private String headerKey;



    @Override
    public String toString() {
        return "JwtProperties{" +
                "secret='" + secret + '\'' +
                ", expiresAt='" + expiresAt + '\'' +
                ", headerPrefix='" + headerPrefix + '\'' +
                ", headerKey='" + headerKey + '\'' +
                '}';
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this);
    }
}
