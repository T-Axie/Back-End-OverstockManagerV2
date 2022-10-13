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

    private final String appToken = "";
    private final String appSecret = "";
    private final String accessToken = "";
    private final String accessTokenSecret = "";

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
