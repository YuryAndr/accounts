package ru.andryakov.accounts.emulator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import ru.andryakov.accounts.common.service.AccountService;

@Configuration
public class RmiClientConfig {

    @Value("${accounts.client.emulator.uri}")
    private String uri;

    @Bean
    public RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl(uri);
        rmiProxyFactory.setServiceInterface(AccountService.class);
        return rmiProxyFactory;
    }
}
