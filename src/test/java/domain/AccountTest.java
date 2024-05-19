package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountTest {

	@Test
	public void testConstructorWithParameters() {
		Account accountWithId = new Account(1L, "test@example.com", "password", "John", "Doe");
		assertEquals(1L, (long) accountWithId.getId());
		assertEquals("test@example.com", accountWithId.getEmail());
		assertEquals("password", accountWithId.getPassword());
		assertEquals("John", accountWithId.getFirstname());
		assertEquals("Doe", accountWithId.getLastname());
	}

	@Test
	public void testConstructorWithoutId() {
		Account accountWithoutId = new Account("test@example.com", "password", "John", "Doe");
		assertEquals("test@example.com", accountWithoutId.getEmail());
		assertEquals("password", accountWithoutId.getPassword());
		assertEquals("John", accountWithoutId.getFirstname());
		assertEquals("Doe", accountWithoutId.getLastname());
	}

	@Test
	public void checkValidPassword() {
		Account account = new Account(1L, "victorian@child.com",
				"$scrypt$n=16384,r=8,p=1$idPZqR6sCtk7R20WhdyaWQ$Bvn4Q6AQpd3zDEGlqi+ScVOZ3igKCt44PrHR1c0lS1ICA1TMBFHBbkvJTdT+TYedNJ6Z3EtYhciLvxH0aZpu+w",
				"goblin", "CDs");
		assertDoesNotThrow(() -> {
			account.checkPassword("Password");
		});
	}

	// TODO: Lore
//	@Test
//	public void checkInvalidPassword() {
//		Account account = new Account(1L, "victorian@child.com", "verysecurepasswordthatisntevenahashlol", "goblin",
//				"CDs");
//		assertThrows(IllegalArgumentException.class, () -> {
//			account.checkPassword("victorianchild");
//		});
//	}
}
