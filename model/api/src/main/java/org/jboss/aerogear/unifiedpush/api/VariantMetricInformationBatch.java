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

import javax.validation.constraints.NotNull;

/**
 * Contains metadata about a variant, of the push message request, such as successful delivery to the push network
 * or involved client devices.
 */
public class VariantMetricInformationBatch extends BaseModel {

    private static final long serialVersionUID = 7704636533294404718L;

    @NotNull
    private String variantID;
    @NotNull
    private String pushMessageInformationID;
    private Long receivers = 0L;
    private Boolean deliveryStatus = Boolean.FALSE;
    private String reason;

    public VariantMetricInformationBatch() {
    }

    public VariantMetricInformationBatch(VariantMetricInformation vmi) {
        variantID = vmi.getVariantID();
        receivers = vmi.getReceivers();
        deliveryStatus = vmi.getDeliveryStatus();
        reason = vmi.getReason();
        pushMessageInformationID = vmi.getPushMessageInformation().getId();
    }

    /**
     * The ID of the involved variant
     *
     * @return variant ID
     */
    public String getVariantID() {
        return variantID;
    }

    public void setVariantID(String variantID) {
        this.variantID = variantID;
    }

    /**
     * Number of receivers for this variant that were found for the submitted push request
     *
     * @return number of receivers
     */
    public Long getReceivers() {
        return receivers;
    }

    public void setReceivers(Long receivers) {
        this.receivers = receivers;
    }

    /**
     * Indicator if the request to the actual push network, for the related variant, was successful or not.
     *
     * @return status of the delivery
     */
    public Boolean getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Boolean deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    /**
     * In case of an error (deliveryStatus:false), there is most likely a reason which may give some more insights.
     *
     * @return error details
     */
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPushMessageInformationID() {
        return pushMessageInformationID;
    }

    public void setPushMessageInformationID(String pushMessageInformationID) {
        this.pushMessageInformationID = pushMessageInformationID;
    }
}
