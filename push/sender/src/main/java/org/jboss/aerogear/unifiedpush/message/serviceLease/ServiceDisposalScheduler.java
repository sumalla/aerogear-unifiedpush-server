/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aerogear.unifiedpush.message.serviceLease;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Allows to scheduled instantiated services for disposal.
 *
 * Gracefully disposes services on a container shutdown.
 */
@Singleton
@Startup
public class ServiceDisposalScheduler {

    private ScheduledExecutorService scheduler;
    private long terminationTimeout = 10000L;

    /**
     * Creates a new scheduler on container startup
     */
    @PostConstruct
    public void initialize() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * On container shutdown, immediately terminates all instantiated services that were scheduled for disposal.
     */
    @PreDestroy
    public void terminate() {
        try {
            scheduler.shutdown();
            scheduler.awaitTermination(terminationTimeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new IllegalStateException("Interrupted during attempt to shutdown gracefully", e);
        }
    }

    /**
     * Schedules a service instance for disposal if not used.
     * @param reference a reference to service that will be disposed later
     * @param delay a delay which need to pass before the reference can be disposed
     */
    public void scheduleForDisposal(DisposableReference<?> reference, long delay) {
        synchronized (scheduler) {
            terminationTimeout = Math.max(delay + 2500L, terminationTimeout);
            scheduler.schedule(new DisposeTask(reference), delay, TimeUnit.MILLISECONDS);
        }
    }

    private static class DisposeTask implements Runnable {

        private DisposableReference<?> reference;

        public DisposeTask(DisposableReference<?> reference) {
            this.reference = reference;
        }

        @Override
        public void run() {
            reference.dispose();
        }
    }
}
