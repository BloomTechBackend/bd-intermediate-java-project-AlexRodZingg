package com.amazon.ata.deliveringonourpromise.dao;

import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.deliveringonourpromise.types.Order;
import com.amazon.ata.ordermanipulationauthority.OrderResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class OrderDaoTest {
//    private OrderManipulationAuthorityClient mockClient;
    private OrderManipulationAuthorityClient omaClient;
    private ReadOnlyDao<String, Order> orderDao;

    @Test
    public void get_forKnownOrderId_returnsOrder() {
        // GIVEN
        String knownOrderId = "111-7499023-7630574";
        // WHEN
        Order order = orderDao.get(knownOrderId);

        // THEN
        assertNotNull(order);

    }



}
