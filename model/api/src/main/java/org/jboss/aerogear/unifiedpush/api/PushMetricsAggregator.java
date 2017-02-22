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
package org.jboss.aerogear.unifiedpush.api;

public class PushMetricsAggregator extends BaseModel {

    private String pushApplicationID;
    private long count = 0L;
    private long receivers = 0L;
    private long appOpenedCounter = 0L;

    public void setPushApplicationID(String pushApplicationID) {
        this.pushApplicationID = pushApplicationID;
    }

    public String getPushApplicationID() {
        return pushApplicationID;
    }


    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getReceivers() {
        return receivers;
    }

    public void setReceivers(long receivers) {
        this.receivers = receivers;
    }

    public long getAppOpenedCounter() {
        return appOpenedCounter;
    }

    public void setAppOpenedCounter(long appOpenedCounter) {
        this.appOpenedCounter = appOpenedCounter;
    }

}
