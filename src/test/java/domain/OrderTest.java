package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderTest {

	private Order order;
	private Client client;
	private Business business;
	private Supplier supplier;

	@BeforeEach
	public void setUp() {
		List<OrderItem> items = new ArrayList<>();
		OrderItem item = new OrderItem();
		items.add(item);

		Set<SubOrder> subOrders = new HashSet<>();
		SubOrder subOrder = new SubOrder(1, "123456", 1, LocalDate.now(), 1, 1, LocalDate.now(), order, items);
		subOrder.toPaymentState(new PaidState(subOrder));
		subOrder.toOrderState(new OpenState(subOrder));
		subOrders.add(subOrder);


		List<Order> orders = new ArrayList<>();
		client = new Client("test@example.com", "password", "John", "Doe", "123456789", true, business, orders);

		order = new Order(1, "123456", "Billing Street", "12345", "Billing Country", "1A", "Billing City",
				"Shipping Street", "54321", "Shipping Country", "2B", "Shipping City", "Remark", "30 days",
				LocalDate.now(), subOrders, client);
		order.setPaymentState();
		order.setOrderState();
		orders.add(order);

		supplier = new Supplier("test@example.com", "password", "John", "Doe", "123456789", business, true);
		business = new Business(
				"https://cpmr-islands.org/wp-content/uploads/sites/4/2019/07/Test-Logo-Small-Black-transparent-1.png",
				"Business Name", "Test Street", "VAT", "BE", "9420", "7", "Erpe-Mere", "IT", supplier, client);
	}

	@Test
	public void testConstructorWithParameters() {
		assertEquals("123456", order.getUuid());
		assertEquals("Billing Street", order.getBillingAddressStreet());
		assertEquals("12345", order.getBillingAddressZipcode());
		assertEquals("Billing Country", order.getBillingAddressCountry());
		assertEquals("1A", order.getBillingAddressStreetNr());
		assertEquals("Billing City", order.getBillingAddressCity());
		assertEquals("Shipping Street", order.getShippingAddressStreet());
		assertEquals("54321", order.getShippingAddressZipcode());
		assertEquals("Shipping Country", order.getShippingAddressCountry());
		assertEquals("2B", order.getShippingAddressStreetNr());
		assertEquals("Shipping City", order.getShippingAddressCity());
		assertEquals("Remark", order.getRemark());
		assertEquals("30 days", order.getPaymentPeriod());
		assertNotNull(order.getDate());
		assertEquals(2, order.getPaymentStatusId());
		assertEquals(1, order.getOrderStatusId());
		assertNotNull(order.getPaymentState());
		assertNotNull(order.getOrderState());
		assertNotNull(order.getSubOrders());
		assertEquals(client, order.getClient());
	}
}
