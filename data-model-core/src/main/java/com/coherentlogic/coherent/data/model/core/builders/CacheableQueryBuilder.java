package com.coherentlogic.coherent.data.model.core.builders;

import com.coherentlogic.coherent.data.model.core.cache.CacheServiceProviderSpecification;

/**
 * Apply the JAMON Performance Interceptor as follows:
 *
 * <pre>
 * <bean id="springMonitoringAspectInterceptor"
 *  class="org.springframework.aop.interceptor.JamonPerformanceMonitorInterceptor">
 *     <property name="trackAllInvocations" value="true"/>
 *     <property name="useDynamicLogger" value="true"/>
 * </bean>
 *
 * <aop:config proxy-target-class="true">
 *     <aop:advisor advice-ref="springMonitoringAspectInterceptor"
 *      pointcut=
 *      "execution(public * com.coherentlogic.coherent.data.model.core.builders.HttpMethodsSpecification.doGet(..)))"/>
 * </aop:config>
 * </pre>
 *
 * The important item to note here is that the pointcut is applied to the </b>interface<b> and not the implementation --
 * if you target the implementation nothing will appear in the log.
 *
 * Also note that whatever bean you're applying the interceptor to <b>must</b> be obtained through the Spring container,
 * otherwise the interceptor will not be applied to it.
 *
 * @todo Auto-apply the performance interceptor.
 *
 * @author <a href="mailto:support@coherentlogic.com">Support</a>
 */
public abstract class CacheableQueryBuilder<K, V> extends AbstractQueryBuilder<K, V> {

    private final CacheServiceProviderSpecification<K, V> cache;

    public CacheableQueryBuilder(
        CacheServiceProviderSpecification<K, V> cache
    ) {
        super();
        this.cache = cache;
    }

    public CacheServiceProviderSpecification<K, V> getCache() {
        return cache;
    }
}
