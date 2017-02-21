package org.jboss.aerogear.unifiedpush.message.cache.metrics;

import org.jboss.aerogear.unifiedpush.api.PushMessageInformation;
import org.jboss.aerogear.unifiedpush.dao.PageResult;
import org.jboss.aerogear.unifiedpush.dto.MessageMetrics;
import org.jboss.aerogear.unifiedpush.message.event.PushMessageCompletedEvent;
import org.jboss.aerogear.unifiedpush.service.metrics.PushMessageMetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class MetricsCacheProcessor {

    private final Logger logger = LoggerFactory.getLogger(MetricsCacheProcessor.class);

    @Inject
    private SimpleMetricsCache cache;

    @Inject
    private PushMessageMetricsService metricsService;

    public void prepareCacheUpdate(@Observes PushMessageCompletedEvent pushMessageCompletedEvent) {

        logger.warn("processing metrics update");

        final String pushApplicationID = pushMessageCompletedEvent.getPushApplicationId();

        PageResult<PushMessageInformation, MessageMetrics> pageResult =
                metricsService.findAllForPushApplication(pushApplicationID, null, Boolean.FALSE, null, null);


        cache.getStore().put(pushApplicationID+":total", pageResult.getAggregate().getCount());
        cache.getStore().put(pushApplicationID+":receivers", pageResult.getAggregate().getReceivers());
        cache.getStore().put(pushApplicationID+":appOpenedCounter", pageResult.getAggregate().getAppOpenedCounter());


    }
}
