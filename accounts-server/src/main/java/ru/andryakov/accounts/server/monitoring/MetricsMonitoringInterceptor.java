package ru.andryakov.accounts.server.monitoring;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MetricsMonitoringInterceptor implements MethodInterceptor, MetricsMonitoringInterceptorMBean {

    private final MetricRegistry metrics;

    public MetricsMonitoringInterceptor(MetricRegistry metrics) {
        this.metrics = metrics;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();
        metrics.meter(methodName).mark();
        return invocation.proceed();
    }

    @Override
    public void reset() {
        metrics.removeMatching(MetricFilter.ALL);
    }
}
