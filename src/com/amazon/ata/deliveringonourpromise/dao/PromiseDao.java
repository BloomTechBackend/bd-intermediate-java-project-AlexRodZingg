package com.amazon.ata.deliveringonourpromise.dao;

import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.deliveringonourpromise.promiseclient.PromiseClient;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.ordermanipulationauthority.OrderResult;
import com.amazon.ata.ordermanipulationauthority.OrderResultItem;
import com.amazon.ata.ordermanipulationauthority.OrderShipment;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for Promises.
 */
public class PromiseDao implements ReadOnlyDao<String, List<Promise>> {
    private List<PromiseClient> promiseClients;
    private OrderManipulationAuthorityClient omaClient;

    /**
     * PromiseDao constructor, accepting service clients for DPS and OMA.
     * @param promiseClients List of {@code PromiseClient} for DAO to access Promise Clients
     * @param omaClient OrderManipulationAuthorityClient for DAO to access OMA
     */
    public PromiseDao(List<PromiseClient> promiseClients, OrderManipulationAuthorityClient omaClient) {
        this.promiseClients = promiseClients;
        this.omaClient = omaClient;
    }

    /**
     * Returns a list of all Promises associated with the given order item ID.
     * @param customerOrderItemId the order item ID to fetch promise for
     * @return a List of promises for the given order item ID
     */
    @Override
    public List<Promise> get(String customerOrderItemId) {
        // Fetch the delivery date, so we can add to any promises that we find
        ZonedDateTime itemDeliveryDate = getDeliveryDateForOrderItem(customerOrderItemId);

        List<Promise> promises = new ArrayList<>();

        // fetch Promise from Promise Service. If exists, add to list of Promises to return.
        // Set delivery date

        for (PromiseClient pc : promiseClients) {
            Promise promise = pc.getDeliveryPromiseByOrderItemId(customerOrderItemId);

            if (promise != null) {
                promise.setDeliveryDate(itemDeliveryDate);
                promises.add(promise);
            }
        }

        return promises;
    }

    /*
     * Fetches the delivery date of the shipment containing the order item specified by the given order item ID,
     * if there is one.
     *
     * If the order item ID doesn't correspond to a valid order item, or if the shipment hasn't been delivered
     * yet, return null.
     */
    private ZonedDateTime getDeliveryDateForOrderItem(String customerOrderItemId) {
        OrderResultItem orderResultItem = omaClient.getCustomerOrderItemByOrderItemId(customerOrderItemId);

        if (null == orderResultItem) {
            return null;
        }

        OrderResult orderResult = omaClient.getCustomerOrderByOrderId(orderResultItem.getOrderId());

        for (OrderShipment shipment : orderResult.getOrderShipmentList()) {
            for (OrderShipment.ShipmentItem shipmentItem : shipment.getCustomerShipmentItems()) {
                if (shipmentItem.getCustomerOrderItemId().equals(customerOrderItemId)) {
                    return shipment.getDeliveryDate();
                }
            }
        }

        // didn't find a delivery date!
        return null;
    }
}
