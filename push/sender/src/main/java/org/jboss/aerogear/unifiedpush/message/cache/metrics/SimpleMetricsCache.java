package org.jboss.aerogear.unifiedpush.message.cache.metrics;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Named
@ApplicationScoped
public class SimpleMetricsCache {

    private final ConcurrentMap<String, Long> store = new ConcurrentHashMap();

    public ConcurrentMap<String, Long> getStore() {
        return store;
    }
}
