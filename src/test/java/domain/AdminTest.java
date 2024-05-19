package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.AccountDaoJpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminTest {

    @Mock
    private Admin admin;
    @Mock
    private AccountDaoJpa accountDaoJpa;


    //ORM  doet hier raar - mappen van 1 naar "1" waarschijnlijk.
    @Test
    public void get_is_admin() {
    var isAdmin  = "1";
    when(admin.getIsAdmin()).thenReturn(isAdmin);
        assertEquals(isAdmin, admin.getIsAdmin());
    }

    @Test
    public void get_name() {
        var adminName = "Vicky The Admin";
        when(admin.getName()).thenReturn(adminName);
        assertEquals(adminName, admin.getName());
    }

    @Test
    public void find_admin_by_email() {
        var email = "admin@delaware.com";
        when(accountDaoJpa.getAccountByEmail(email)).thenReturn(admin);

        Admin foundAdmin = (Admin) accountDaoJpa.getAccountByEmail(email);
        assertEquals(admin, foundAdmin);
    }
}