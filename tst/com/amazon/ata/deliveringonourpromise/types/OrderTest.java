package com.amazon.ata.deliveringonourpromise.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private List<OrderItem> customerOrderItemList = new ArrayList<>();


    // Remember customerOrderItemList is a List, which is mutable.
    // Then, the only way it would be vulnerable is through a getter.
    // So, create a customerOrderItemList, fill it, then create an Order using the list in the constructor.
    // Then test it by simply, creating a new list set equal to order.getCustomerOrderItemList()
    // Defensive copying test: Mutable attribute + getter
    // Don't overthink about what inputs are needed, just use the bare minimum needed to test without crashing
    @Test
    public void testDefensiveCopyingOnGetter() {
        customerOrderItemList.add(OrderItem.builder().withCustomerOrderItemId("1").build());
        customerOrderItemList.add(OrderItem.builder().withCustomerOrderItemId("2").build());

        Order order = Order.builder().withCustomerOrderItemList(customerOrderItemList).build();

        List<OrderItem> retrievedList = order.getCustomerOrderItemList();
        retrievedList.remove(0);

        assertEquals(customerOrderItemList.size(), order.getCustomerOrderItemList().size(),
                "Original list should not be affected by modifications to retrievedList.");
    }

    @Test
    public void testBuilderVulnerability() {
        customerOrderItemList.add(OrderItem.builder().withCustomerOrderItemId("1").build());
        customerOrderItemList.add(OrderItem.builder().withCustomerOrderItemId("2").build());

        List <OrderItem> checkList = new ArrayList<>(customerOrderItemList);

        Order order = Order.builder().withCustomerOrderItemList(customerOrderItemList).build();

        customerOrderItemList.remove(0);

        assertEquals(checkList.size(), order.getCustomerOrderItemList().size(),
                "Changing customerOrderItemList should not have affected order.");
    }
}
