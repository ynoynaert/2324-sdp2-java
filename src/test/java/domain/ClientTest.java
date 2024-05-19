package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest {

	private Client client;
	private Business business;
	private Supplier supplier;
	private List<Order> orders;

	@BeforeEach
	public void setUp() {
		orders = new ArrayList<>();
		client = new Client("test@example.com", "password", "John", "Doe", "123456789", true, business, orders);
		supplier = new Supplier("test@example.com", "password", "John", "Doe", "123456789", business, true);
		business = new Business(
				"https://cpmr-islands.org/wp-content/uploads/sites/4/2019/07/Test-Logo-Small-Black-transparent-1.png",
				"Business Name", "Test Street", "VAT", "BE", "9420", "7", "Erpe-Mere", "IT", supplier, client);
	}

	@Test
	public void testConstructorWithParameters() {
		Client clientWithId = new Client(1L, "test@example.com", "password", "John", "Doe", "123456789", true, business,
				orders);
		assertEquals("test@example.com", clientWithId.getEmail());
		assertEquals("password", clientWithId.getPassword());
		assertEquals("John", clientWithId.getFirstname());
		assertEquals("Doe", clientWithId.getLastname());
		assertEquals("123456789", clientWithId.getPhoneNumber());
		assertTrue(clientWithId.isEnabled());
		assertEquals(business, clientWithId.getBusiness());
		assertEquals(orders, clientWithId.getOrders());
	}

	@Test
	public void testConstructorWithoutId() {
		Client clientWithoutId = new Client("test@example.com", "password", "John", "Doe", "123456789", true, business,
				orders);
		assertEquals("test@example.com", clientWithoutId.getEmail());
		assertEquals("password", clientWithoutId.getPassword());
		assertEquals("John", clientWithoutId.getFirstname());
		assertEquals("Doe", clientWithoutId.getLastname());
		assertEquals("123456789", clientWithoutId.getPhoneNumber());
		assertTrue(clientWithoutId.isEnabled());
		assertEquals(business, clientWithoutId.getBusiness());
		assertEquals(orders, clientWithoutId.getOrders());
	}

	@Test
	public void testEnabledSetterGetter() {
		assertTrue(client.isEnabled());
		client.setEnabled(false);
		assertFalse(client.isEnabled());
	}
}
