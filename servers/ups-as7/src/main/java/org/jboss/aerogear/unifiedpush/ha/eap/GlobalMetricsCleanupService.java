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
package org.jboss.aerogear.unifiedpush.ha.eap;

import org.jboss.aerogear.unifiedpush.service.metrics.DeleteOldPushMessageInformationScheduler;
import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalMetricsCleanupService implements Service<String>  {

    private static final Logger LOGGER = Logger.getLogger(GlobalMetricsCleanupService.class.getName());
    public static final ServiceName SINGLETON_SERVICE_NAME = ServiceName.JBOSS.append("unifiedpush", "ha", "singleton", "timer");
    private static final String JNDI_NAME = "global/ag-push/DeleteOldPushMessageInformationScheduler";

    /**
     * A flag whether the service is started.
     */
    private final AtomicBoolean started = new AtomicBoolean(false);

    @Override
    public void start(StartContext startContext) throws StartException {
        if (!started.compareAndSet(false, true)) {
            throw new StartException("The service is still started!");
        }
        LOGGER.info("Start HASingleton timer service '" + this.getClass().getName() + "'");

        final String node = System.getProperty("jboss.node.name");
        try {
            InitialContext ic = new InitialContext();
            ((DeleteOldPushMessageInformationScheduler) ic.lookup(JNDI_NAME)).toString();
        } catch (NamingException e) {
            throw new StartException("Could not initialize timer", e);
        }

    }

    @Override
    public void stop(StopContext stopContext) {
        if (!started.compareAndSet(true, false)) {
            LOGGER.warning("The service '" + this.getClass().getName() + "' is not active!");
        } else {
            LOGGER.info("Stop HASingleton timer service '" + this.getClass().getName() + "'");
            try {
                InitialContext ic = new InitialContext();
                ((DeleteOldPushMessageInformationScheduler) ic.lookup(JNDI_NAME)).toString();
            } catch (NamingException e) {
                LOGGER.log(Level.SEVERE, "Could not stop timer", e);
            }
        }

    }

    @Override
    public String getValue() throws IllegalStateException, IllegalArgumentException {
        LOGGER.info(String.format("%s is %s at %s", GlobalMetricsCleanupService.class.getSimpleName(), (started.get() ? "started" : "not started"), System.getProperty("jboss.node.name")));
        return "";
    }
}
