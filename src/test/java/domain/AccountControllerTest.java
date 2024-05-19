package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.AccountDao;

class AccountControllerTest {
	@Mock
	private AccountDao accountDaoMock;
	@Mock
	private AccountManager accountManagerMock;
	@InjectMocks
	private AccountController accountController;

	@BeforeEach
	public void setUp() {
		accountDaoMock = mock(AccountDao.class);
		accountManagerMock = mock(AccountManager.class);
		accountController = new AccountController(accountManagerMock);
	}

	@Test
	public void testGetClientById() {
		// Prepare test data
		Long clientId = 1L;
		Client expectedClient = new Client();

		// Mocking behavior of AccountManager
		when(accountManagerMock.getClientById(clientId)).thenReturn(expectedClient);

		// Testing getClientById method
		assertEquals(expectedClient, accountController.getClientById(clientId));
	}

	@Test
	public void testGetClientsBySupplierId() {
		// Prepare test data
		Long supplierId = 1L;
		ObservableList<Client> expectedClients = FXCollections.observableArrayList();

		// Mocking behavior of AccountManager
		when(accountManagerMock.getClientsBySupplierId(supplierId)).thenReturn(expectedClients);

		// Testing getClientsBySupplierId method
		assertEquals(expectedClients, accountController.getClientsBySupplierId(supplierId));
	}

	@Test
	public void testGetClientAmount() {
		// Prepare test data
		Long clientId = 1L;
		Long expectedAmount = 100L;

		// Mocking behavior of AccountManager
		when(accountManagerMock.getClientAmount(clientId)).thenReturn(expectedAmount);

		// Testing getClientAmount method
		assertEquals(expectedAmount, accountController.getClientAmount(clientId));
	}

	@Test
	public void testGetIsActive() {
		// Prepare test data
		Long clientId = 1L;
		String expectedStatus = "Active";

		// Mocking behavior of AccountManager
		when(accountManagerMock.getIsActive(clientId)).thenReturn(expectedStatus);

		// Testing getIsActive method
		assertEquals(expectedStatus, accountController.getIsActive(clientId));
	}

}
