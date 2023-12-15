package com.amazon.ata.deliveringonourpromise.promiseclient;

import com.amazon.ata.deliveringonourpromise.types.Promise;

public interface PromiseClient {
    /**
     * Enter a customerOrderItemId to receive a Promise.
     * @param customerOrderItemId is the orderItemId that you wish to receive delivery promise for.
     * @return Promise.
     */
    Promise getDeliveryPromiseByOrderItemId(String customerOrderItemId);
}
