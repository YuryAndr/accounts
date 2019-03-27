package ru.andryakov.accounts.server.config;

import static java.util.Collections.singletonList;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import java.lang.management.ManagementFactory;
import java.util.List;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;
import ru.andryakov.accounts.common.service.AccountService;
import ru.andryakov.accounts.server.monitoring.MetricsMonitoringInterceptor;

@Configuration
public class RmiConfig {

    @Value("${accounts.server.host}")
    private String host;

    @Value("${accounts.server.port}")
    private int port;

    @Bean
    public RmiServiceExporter exporter(AccountService accountService) {
        System.setProperty("java.rmi.server.hostname", host);
        Class<AccountService> serviceInterface = AccountService.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(accountService);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(port);
        exporter.setInterceptors(configureInterceptors().toArray());
        return exporter;
    }

    private List<MethodInterceptor> configureInterceptors() {
        MetricsMonitoringInterceptor metricsMonitoringInterceptor = new MetricsMonitoringInterceptor(
                configureMetricRegistry());
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName mxbeanName = null;
        try {
            mxbeanName = new ObjectName(AccountService.class.getName() + ":name=reset");
            mBeanServer.registerMBean(metricsMonitoringInterceptor, mxbeanName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Can not run application: ", e);
        }
        return singletonList(metricsMonitoringInterceptor);
    }

    private MetricRegistry configureMetricRegistry() {
        MetricRegistry metricRegistry = new MetricRegistry();
        JmxReporter
                .forRegistry(metricRegistry)
                .inDomain(AccountService.class.getName())
                .build()
                .start();
        return metricRegistry;
    }
}
