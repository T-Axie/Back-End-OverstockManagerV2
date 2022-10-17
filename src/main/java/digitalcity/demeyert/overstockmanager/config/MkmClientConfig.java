package digitalcity.demeyert.overstockmanager.config;

import lombok.extern.slf4j.Slf4j;
import org.api.mkm.services.OrderService;
import org.api.mkm.services.StockService;
import org.api.mkm.tools.MkmAPIConfig;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MkmClientConfig implements InitializingBean {

    private final String appToken = "5koy0GXgplAPr4lq";
    private final String appSecret = "y2RswBdjl0XKBcMmcWlTiF1RAzn7sCSn";
    private final String accessToken = "5OSebVjddW28CmLh739k5cXXTZY1J44L";
    private final String accessTokenSecret = "JSZYNEKqO6L67ttNpb2SQvq4YA1dQZHP";

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("MkmAPIConfig initialized");
        MkmAPIConfig.getInstance().init(accessTokenSecret,accessToken,appSecret,appToken);
    }

    @Bean
    public StockService stockService(){
        return new StockService();
    }
    @Bean
    public OrderService orderService(){
        return new OrderService();
    }

}
