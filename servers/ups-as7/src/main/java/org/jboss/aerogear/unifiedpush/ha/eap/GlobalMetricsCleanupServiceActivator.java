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

import org.jboss.as.clustering.singleton.SingletonService;
import org.jboss.as.clustering.singleton.election.NamePreference;
import org.jboss.as.clustering.singleton.election.PreferredSingletonElectionPolicy;
import org.jboss.as.clustering.singleton.election.SimpleSingletonElectionPolicy;
import org.jboss.msc.service.DelegatingServiceContainer;
import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceActivatorContext;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceRegistryException;

import java.util.logging.Logger;

/**
 *
 */
public class GlobalMetricsCleanupServiceActivator implements ServiceActivator {
    private static final Logger LOGGER = Logger.getLogger(GlobalMetricsCleanupServiceActivator.class.getName());


    @Override
    public void activate(ServiceActivatorContext context) throws ServiceRegistryException {
        LOGGER.info("HATimerService will be installed!");

        GlobalMetricsCleanupService service = new GlobalMetricsCleanupService();
        SingletonService<String> singleton = new SingletonService<String>(service, GlobalMetricsCleanupService.SINGLETON_SERVICE_NAME);

        /*
         * The NamePreference is a combination of the node name (-Djboss.node.name) and the name of
         * the configured cache "singleton". If there is more than 1 node, it is possible to add more than
         * one name and the election will use the first available node in that list.
         *   -  To pass a chain of election policies to the singleton and tell JGroups to run the
         * singleton on a node with a particular name, uncomment the first line  and
         * comment the second line below.
         *   - To pass a list of more than one node, comment the first line and uncomment the
         * second line below.
         */
        singleton.setElectionPolicy(new PreferredSingletonElectionPolicy(new SimpleSingletonElectionPolicy(), new NamePreference("node1/singleton")));
        //singleton.setElectionPolicy(new PreferredSingletonElectionPolicy(new SimpleSingletonElectionPolicy(), new NamePreference("node1/singleton"), new NamePreference("node2/singleton")));

        singleton.build(new DelegatingServiceContainer(context.getServiceTarget(), context.getServiceRegistry()))
                .setInitialMode(ServiceController.Mode.ACTIVE)
                .install()
        ;

    }
}
