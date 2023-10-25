package com.amazon.ata.deliveringonourpromise.dao;

import com.amazon.ata.deliveringonourpromise.App;
import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.deliveringonourpromise.types.Order;
import com.amazon.ata.ordermanipulationauthority.OrderManipulationAuthority;
import com.amazon.ata.ordermanipulationauthority.OrderResult;
import com.amazon.ata.ordermanipulationauthority.OrderResultItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderDaoTest {
    private OrderManipulationAuthorityClient omaClient = App.getOrderManipulationAuthorityClient();
    private OrderDao orderDao = new OrderDao(omaClient);
    private String orderId;
    private String orderItemId;
    private OrderResult orderResult;
    private OrderResultItem orderResultItem;
    private String invalidOrderId;
    private String invalidOrderItemId;

    @BeforeEach
    private void createClient() {
        orderId = "111-7497023-2960775";
        orderItemId = "20655079937481";
        invalidOrderId = "123";
        invalidOrderItemId = "01";

        orderResultItem = OrderResultItem.builder()
                .withCustomerOrderItemId(orderItemId)
                .withOrderId(orderId).build();
        orderResult = OrderResult.builder()
                .withOrderId(orderId)
                .withCustomerOrderItemList(Arrays.asList(orderResultItem))
                .build();
    }

    @Test
    public void get_forKnownOrderId_returnsOrder() {
        // GIVEN
        //orderId is valid

        // WHEN
        Order order = orderDao.get(orderId);

        // THEN
        assertNotNull(order, "Order was expected to not be null.");
    }

    @Test
    public void get_invalidOrderId_returnsNullOrder() {
        // GIVEN
        // invalidOrderId is invalid.

        // WHEN
        Order order = orderDao.get(invalidOrderId);

        // THEN
        assertEquals(null, order, "Expected Order to be null.");
    }

    @Test
    public void get_emptyOrderId_returnsNullOrder() {
        // GIVEN
        String noOrderId = "";

        // WHEN
        Order order = orderDao.get(noOrderId);

        // THEN
        assertEquals(null, order, "Expected Order to be null.");
    }



}
