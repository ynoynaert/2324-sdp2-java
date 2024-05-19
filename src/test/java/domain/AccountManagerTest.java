package domain;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountManagerTest {

    final String EMAIL = "example@domain.com";
    final String PASSWORD = "password";
    final int CLIENT_ID = 123;
    final int SUPPLIER_ID = 456;
    @Mock
    private AccountManager accountManager;


    @Test
    public void valid_login_Admin() {
        when(accountManager.login(EMAIL, PASSWORD)).thenReturn(new AccountController(accountManager));
        var ac = accountManager.login(EMAIL, PASSWORD);
        assertInstanceOf(AccountController.class, ac);
    }

    @Test
    public void valid_login_supplier() {
        when(accountManager.login(EMAIL, PASSWORD)).thenReturn(new AccountController(accountManager));
        var sc = accountManager.login(EMAIL, PASSWORD);
        assertInstanceOf(AccountController.class, sc);
    }

    @Test
    public void get_client_by_id() {
        AccountManager accountManager = mock(AccountManager.class);
        Client expectedClient = new Client((long)CLIENT_ID, EMAIL, PASSWORD, "firstname", "Lastname", "+32483200900", true, null, null);
        when(accountManager.getClientById((long)CLIENT_ID)).thenReturn(expectedClient);

        Client actualClient = accountManager.getClientById((long)CLIENT_ID);
        verify(accountManager).getClientById((long)CLIENT_ID);

        assertNotNull(actualClient);
        assertEquals(CLIENT_ID, (long)actualClient.getId());
        assertEquals(EMAIL, actualClient.getEmail());
        assertEquals(PASSWORD, actualClient.getPassword());
    }

    @Test
    public void get_clientsFrom_supplier_id() {

        AccountManager accountManager = mock(AccountManager.class);
        List<Client> expectedClients = Arrays.asList(
                new Client(1L, "example1@domain.com", "password1", "First1", "Last1", "+123456789", true, null, null),
                new Client(2L, "example2@domain.com", "password2", "First2", "Last2", "+987654321", true, null, null)
        );
        when(accountManager.getClientsBySupplierId((long) SUPPLIER_ID)).thenReturn(FXCollections.observableArrayList(expectedClients));

        ObservableList<Client> actualClients = accountManager.getClientsBySupplierId((long)SUPPLIER_ID);
        verify(accountManager).getClientsBySupplierId((long)SUPPLIER_ID);

        assertNotNull(actualClients);
        assertFalse(actualClients.isEmpty());


        assertEquals(expectedClients.size(), actualClients.size());
        assertTrue(expectedClients.containsAll(actualClients));
        assertTrue(actualClients.containsAll(expectedClients));
    }

}