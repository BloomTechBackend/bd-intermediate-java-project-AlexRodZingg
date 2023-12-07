package com.amazon.ata.deliveringonourpromise.promiseclient;

import com.amazon.ata.deliveringonourpromise.types.Promise;

public interface PromiseClient {
    Promise getDeliveryPromiseByOrderItemId(String customerOrderItemId);
}
