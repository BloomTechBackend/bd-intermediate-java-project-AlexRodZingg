package com.amazon.ata.deliveringonourpromise.orderfulfillmentservice;

import com.amazon.ata.deliveringonourpromise.promiseclient.PromiseClient;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.orderfulfillmentservice.OrderFulfillmentService;
import com.amazon.ata.orderfulfillmentservice.OrderPromise;

public class OrderFulfillmentServiceClient implements PromiseClient {

    private OrderFulfillmentService orderFulfillmentService;

    /**
     * Constructor for OrderFulfillmentService Client.
     * @param orderFulfillmentService holds data for OrderFulfillment.
     */
    public OrderFulfillmentServiceClient(OrderFulfillmentService orderFulfillmentService) {
        this.orderFulfillmentService = orderFulfillmentService;
    }

    @Override
    public Promise getDeliveryPromiseByOrderItemId(String customerOrderItemId) {

        OrderPromise orderPromise = orderFulfillmentService.getOrderPromise(customerOrderItemId);
        
        if (orderPromise == null) {
            return null;
        }

        return Promise.builder()
                .withPromiseLatestArrivalDate(orderPromise.getPromiseLatestArrivalDate())
                .withCustomerOrderItemId(orderPromise.getCustomerOrderItemId())
                .withPromiseLatestShipDate(orderPromise.getPromiseLatestShipDate())
                .withPromiseEffectiveDate(orderPromise.getPromiseEffectiveDate())
                .withIsActive(orderPromise.isActive())
                .withPromiseProvidedBy(orderPromise.getPromiseProvidedBy())
                .withAsin(orderPromise.getAsin())
                .build();
    }
}
