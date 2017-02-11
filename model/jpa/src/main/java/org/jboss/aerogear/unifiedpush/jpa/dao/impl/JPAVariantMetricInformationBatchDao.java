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

package org.jboss.aerogear.unifiedpush.jpa.dao.impl;

import org.jboss.aerogear.unifiedpush.api.VariantMetricInformationBatch;
import org.jboss.aerogear.unifiedpush.dao.VariantMetricInformationBatchDao;

public class JPAVariantMetricInformationBatchDao extends JPABaseDao<VariantMetricInformationBatch, String> implements VariantMetricInformationBatchDao {

    @Override
    public Class<VariantMetricInformationBatch> getType() {
        return VariantMetricInformationBatch.class;
    }


    @Override
    public long countAllReceiversPerVariantBatchForPushMessage(String variantID, String pushMessageinformationId) {
        return createQuery(
                "select sum(receivers) VariantMetricInformationBatch batch where batch.variantID = :variantId and batch.pushMessageInformation.id = :pushmessageinformationId", Long.class)
                .setParameter("variantId", variantID)
                .setParameter("pushmessageinformationId",pushMessageinformationId)
                .getSingleResult();
    }

    @Override
    public VariantMetricInformationBatch findErrorBatchForPushMessage(String variantID, String pushMessageinformationId) {
        return createQuery(
                "select distinct batch from VariantMetricInformationBatch batch where batch.variantID = :variantId and batch.pushMessageInformation.id = :pushmessageinformationId")
                .setParameter("variantId", variantID)
                .setParameter("pushmessageinformationId",pushMessageinformationId)
        .getSingleResult();
    }

    @Override
    public long countAllariantBatchesForPushMessage(String variantID, String pushMessageinformationId) {
        return createQuery(
                "select count(receivers) VariantMetricInformationBatch batch where batch.variantID = :variantId and batch.pushMessageInformation.id = :pushmessageinformationId", Long.class)
                .setParameter("variantId", variantID)
                .setParameter("pushmessageinformationId",pushMessageinformationId)
        .getSingleResult();
    }
}
