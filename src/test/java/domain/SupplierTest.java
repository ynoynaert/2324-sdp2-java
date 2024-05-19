package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplierTest {

	private Supplier supplier;
	private Business business;
	private Client client;
    private LoginController lc;

	@BeforeEach
	public void setUp() {
		supplier = new Supplier("test@example.com", "password", "John", "Doe", "123456789", business, true);
		client = new Client("test@example.com", "password", "John", "Doe", "123456789", true, business, null);
		business = new Business(
				"https://cpmr-islands.org/wp-content/uploads/sites/4/2019/07/Test-Logo-Small-Black-transparent-1.png",
				"Business Name", "Test Street", "VAT", "BE", "9420", "7", "Erpe-Mere", "IT", supplier, client);
	}

	@Test
	public void testAddPaymentMethod() {
		PaymentMethod paymentMethod = new PaymentMethod("Credit Card");
		supplier.addPaymentMethod(paymentMethod);
		assertEquals(1, supplier.getPaymentMethods().size());
		assertTrue(supplier.getPaymentMethods().contains(paymentMethod));
	}

	@Test
	public void testConstructorWithParameters() {
		Supplier supplierWithId = new Supplier(1L, "test@example.com", "password", "John", "Doe", "123456789", business,
				true);
		assertEquals("test@example.com", supplierWithId.getEmail());
		assertEquals("password", supplierWithId.getPassword());
		assertEquals("John", supplierWithId.getFirstname());
		assertEquals("Doe", supplierWithId.getLastname());
		assertEquals("123456789", supplierWithId.getPhoneNumber());
		assertEquals(business, supplierWithId.getBusiness());
		assertTrue(supplierWithId.isEnabled());
	}

	@Test
	public void testConstructorWithoutId() {
		Supplier supplierWithoutId = new Supplier("test@example.com", "password", "John", "Doe", "123456789", business,
				true);
		assertEquals("test@example.com", supplierWithoutId.getEmail());
		assertEquals("password", supplierWithoutId.getPassword());
		assertEquals("John", supplierWithoutId.getFirstname());
		assertEquals("Doe", supplierWithoutId.getLastname());
		assertEquals("123456789", supplierWithoutId.getPhoneNumber());
		assertEquals(business, supplierWithoutId.getBusiness());
		assertTrue(supplierWithoutId.isEnabled());
	}

	@Test
	public void testEnabledSetterGetter() {
		assertTrue(supplier.isEnabled());
		supplier.setEnabled(false);
		assertFalse(supplier.isEnabled());
	}
}