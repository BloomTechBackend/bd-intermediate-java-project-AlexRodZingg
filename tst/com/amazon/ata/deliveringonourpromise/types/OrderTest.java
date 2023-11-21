package com.amazon.ata.deliveringonourpromise.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private List<OrderItem> customerOrderItemList = new ArrayList<>();

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

    @Test
    public void orderClassShouldNotHaveSetterForOrderId() {
        Method[] methods = Order.class.getMethods();
        for (Method method : methods) {
            if (method.getName().toLowerCase().startsWith("set") && method.getParameterTypes().length == 1) {
                if (method.getName().toLowerCase().contains("orderid")) {
                    fail("Setter method found for orderId.");
                }
            }
        }
    }

    @Test
    public void orderClassShouldNotHaveSetterForCustomerId() {
        Method[] methods = Order.class.getMethods();
        for (Method method : methods) {
            if (method.getName().toLowerCase().startsWith("set") && method.getParameterTypes().length == 1) {
                if (method.getName().toLowerCase().contains("customerid")) {
                    fail("Setter method found for customerId.");
                }
            }
        }
    }

    @Test
    public void orderAttributesArePrivate() {
        Field[] fields = Order.class.getFields();
        for (Field field : fields) {
            assertTrue(Modifier.isPrivate(field.getModifiers()),
                    "Field " + field.getName() + " should be private.");
        }
    }
}
