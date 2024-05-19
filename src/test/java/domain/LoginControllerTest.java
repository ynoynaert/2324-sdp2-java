package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private AccountManager manager;

    @InjectMocks
    private LoginController controller;

    @Test
    void login() {
        String email = "logged@in.out";
        String password = "Passwordsy";
        AccountController acMock = mock(AccountController.class);
        when(manager.login(email, password)).thenReturn(acMock);

        AccountController result = controller.login(email, password);

        assertEquals(acMock, result);
        assertEquals(acMock, controller.getAc());
        verify(manager).login(email, password);
    }


    @Test
    void get_ac() {
        String email = "logged@in.out";
        String password = "Passwordsy";
        AccountController acMock = mock(AccountController.class);
        when(manager.login(email, password)).thenReturn(acMock);

        controller.login(email, password);
        AccountController result = controller.getAc();

        assertNotNull(result);
    }

    @Test
    void login_fails() {
        String email = "logged@in.out";
        String password = "false";
        when(manager.login(email, password)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> {
            controller.login(email, password);
        });

    }
}