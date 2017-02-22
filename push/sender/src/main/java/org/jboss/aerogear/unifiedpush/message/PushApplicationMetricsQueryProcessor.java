/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.unifiedpush.message;

import org.jboss.aerogear.unifiedpush.api.PushMetricsAggregator;
import org.jboss.aerogear.unifiedpush.dto.MessageMetrics;
import org.jboss.aerogear.unifiedpush.message.event.PushMessageCompletedEvent;
import org.jboss.aerogear.unifiedpush.service.metrics.PushMessageMetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * Bean to create the latest stats/metrics for a given push application
 */
@Stateless
public class PushApplicationMetricsQueryProcessor {

    private final Logger logger = LoggerFactory.getLogger(PushApplicationMetricsQueryProcessor.class);

    @Inject
    private PushMessageMetricsService metricsService;

    public void prepareCacheUpdate(@Observes PushMessageCompletedEvent pushMessageCompletedEvent) {

        logger.warn("processing metrics update");

        final String pushApplicationID = pushMessageCompletedEvent.getPushApplicationId();

        // takes some time...
        MessageMetrics result = metricsService.findMessageMetricsForPushApplicationByParams(pushApplicationID, null, Boolean.FALSE, null, null);

        // now let's add some meat to the aggregation entity
        PushMetricsAggregator pushMetricsAggregator = metricsService.findAllForPushApplication(pushApplicationID);

        if (pushMetricsAggregator == null) {
            pushMetricsAggregator = new PushMetricsAggregator();

            applyValues(pushMetricsAggregator, result);
            // ship it
            metricsService.addPushMetricsAggregator(pushMetricsAggregator);
        } else {
            // update
            applyValues(pushMetricsAggregator, result);

        }

    }

    private void applyValues(PushMetricsAggregator pushMetricsAggregator, MessageMetrics result) {
        pushMetricsAggregator.setAppOpenedCounter(result.getAppOpenedCounter());
        pushMetricsAggregator.setReceivers(result.getReceivers());
        pushMetricsAggregator.setCount(result.getCount());
    }
}
