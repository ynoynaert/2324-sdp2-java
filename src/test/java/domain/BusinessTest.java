package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BusinessTest {

	private Business business;
	private Supplier supplier;
	private Client client;


	private String bUrl = "https://abc.com/image.jpg";
	private String bName = "Business name";
	private String street = "street";
	private String vatNumber = "BE1234567890";
	private String countryCode = "BE";
	private String zipcode = "12345";
	private String streetNr = "1A";
	private String city = "Aalst";


	@BeforeEach
	public void setUp() {
		supplier = new Supplier("test@example.com", "password", "John", "Doe", "123456789", business, true);
		client = new Client("test@example.com", "password", "John", "Doe", "123456789", true, business, null);
		business = new Business(bUrl, bName, street, vatNumber, countryCode, zipcode, streetNr, city,
				"Sector", supplier, client);
	}

	@Test
	public void testConstructorWithParameters() {
		Business businessWithId = new Business(1L, "http://example.com/image.png", "Business Name", "Street", "BE1234567890", "BE", "12345",
				"1A", "City", "Sector", supplier, client);
		assertEquals(1L, (long) businessWithId.getId());
		assertEquals("http://example.com/image.png", businessWithId.getImageUrl());
		assertEquals("Business Name", businessWithId.getNameBusiness());
		assertEquals("Street", businessWithId.getStreet());
		assertEquals("BE1234567890", businessWithId.getVatNumber());
		assertEquals("BE", businessWithId.getCountry());
		assertEquals("12345", businessWithId.getZipcode());
		assertEquals("1A", businessWithId.getStreetNr());
		assertEquals("City", businessWithId.getCity());
		assertEquals("Sector", businessWithId.getSector());
		assertEquals(supplier, businessWithId.getSupplier());
		assertEquals(client, businessWithId.getClient());
	}

	@Test
	public void testConstructorWithoutId() {
		assertEquals(bUrl, business.getImageUrl());
		assertEquals(bName, business.getNameBusiness());
		assertEquals(street, business.getStreet());
		assertEquals(vatNumber, business.getVatNumber());
		assertEquals(countryCode, business.getCountry());
		assertEquals(zipcode, business.getZipcode());
		assertEquals(streetNr, business.getStreetNr());
		assertEquals(city, business.getCity());
		assertEquals("Sector", business.getSector());
		assertEquals(supplier, business.getSupplier());
		assertEquals(client, business.getClient());
	}

	@Test
	public void testSetterAndGetters() {
		Supplier newSupplier = new Supplier();
		Client newClient = new Client();
		business.setImageUrl("http://example.com/new_image.png");
		business.setNameBusiness("New Business Name");
		business.setStreet("New Street");
		business.setVatNumber("NEWVAT123456");
		business.setCountry("NC");
		business.setZipcode("54321");
		business.setStreetNr("2B");
		business.setCity("New City");
		business.setSector("New Sector");
		business.setSupplier(newSupplier);
		business.setClient(newClient);

		assertEquals("http://example.com/new_image.png", business.getImageUrl());
		assertEquals("New Business Name", business.getNameBusiness());
		assertEquals("New Street", business.getStreet());
		assertEquals("NEWVAT123456", business.getVatNumber());
		assertEquals("NC", business.getCountry());
		assertEquals("54321", business.getZipcode());
		assertEquals("2B", business.getStreetNr());
		assertEquals("New City", business.getCity());
		assertEquals("New Sector", business.getSector());
		assertEquals(newSupplier, business.getSupplier());
		assertEquals(newClient, business.getClient());
	}

	@Test
	public void valid_fields_ret() {
		business = new Business("http://example.com/image.png", "BusinessName", "Street", "BE1234567890", "BE", "1000", "10A", "City", "Sector", supplier, client);
		List<String> errors = business.validate();
		assertEquals(0, errors.size());
	}

	@Test
	public void invalid_all_fields() {
		business = new Business("", "", "", "", "", "", "", "", "", supplier, client);
		List<String> errors = business.validate();
		assertEquals(1, errors.size());
		assertEquals("Please fill in all fields. ", errors.get(0));
	}

	@Test
	public void invalid_vat_number() {
		business = new Business(bUrl, bName, street, "1234567890", countryCode, zipcode, streetNr, city, "Sector", supplier, client);
		List<String> errors = business.validate();
		assertEquals(1, errors.size());
		assertEquals("VAT number should start with 2 letters and then 10 numbers. ", errors.get(0));
	}

	@Test
	public void invalid_street_nr() {
		business = new Business(bUrl, bName, street, vatNumber, countryCode, zipcode, "A10", city, "Sector", supplier, client);
		List<String> errors = business.validate();
		assertEquals(1, errors.size());
		assertEquals("Street number should start with a number. ", errors.get(0));
	}

	@Test
	public void invalid_country_code() {
		business = new Business(bUrl, bName, street, vatNumber, "BEL", zipcode, streetNr, city, "Sector", supplier, client);
		List<String> errors = business.validate();
		assertEquals(1, errors.size());
		assertEquals("Country code should be two letters. ", errors.get(0));
	}

	@Test
	public void invalid_zip_code() {
		business = new Business(bUrl, bName, street, vatNumber, countryCode, "10A0", streetNr, city, "Sector", supplier, client);
		List<String> errors = business.validate();
		assertEquals(1, errors.size());
		assertEquals("Zipcode should be a number. ", errors.get(0));
	}

	@Test
	public void invalid_sector_contains_numbers() {
		business = new Business(bUrl, bName, street, vatNumber, countryCode, zipcode, streetNr, city, "Sector1", supplier, client);
		List<String> errors = business.validate();
		assertEquals(1, errors.size());
		assertEquals("Industry cannot contain numbers. ", errors.get(0));
	}

	@Test
	public void invalid_street_contains_numbers() {
		business = new Business(bUrl, bName, "Street1", vatNumber, countryCode, zipcode, streetNr, city, "Sector", supplier, client);
		List<String> errors = business.validate();
		assertEquals(1, errors.size());
		assertEquals("Street cannot contain numbers. ", errors.get(0));
	}

	@Test
	public void invalid_city_contains_numbers() {
		business = new Business(bUrl, bName, street, vatNumber, countryCode, zipcode, streetNr, "City1", "Sector", supplier, client);
		List<String> errors = business.validate();
		assertEquals(1, errors.size());
		assertEquals("City cannot contain numbers. ", errors.get(0));
	}

	@Test
	public void invalid_image_wrong_extension() {
		business = new Business("https://abc.com/image.bmp", bName, street, vatNumber, countryCode, zipcode, streetNr, city, "Sector", supplier, client);
		List<String> errors = business.validate();
		assertEquals(1, errors.size());
		assertEquals("ImageUrl should end with .png or .jpg. ", errors.get(0));
	}

	@Test
	public void invalid_url() {
		business = new Business("htp://abc.com/image.jpg", bName, street, vatNumber, countryCode, zipcode, streetNr, city, "Sector", supplier, client);
		List<String> errors = business.validate();
		assertEquals(1, errors.size());
		assertEquals("ImageUrl should be a valid url. ", errors.get(0));
	}

	@Test
	public void test_combined_invalid_url_and_invalid_street_and_invalid_city() {
		business = new Business("ht://abc.commage.png", bName, "Street1", vatNumber, countryCode, zipcode, streetNr, "City1", "Sector", supplier, client);
		List<String> errors = business.validate();
		assertEquals(3, errors.size());
		assertTrue(errors.contains("Street cannot contain numbers. "));
		assertTrue(errors.contains("City cannot contain numbers. "));
		assertTrue(errors.contains("ImageUrl should be a valid url. "));
	}

}
