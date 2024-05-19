package domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {


    @InjectMocks
    private User user;

    @Mock
    private Account accountMock;

  @BeforeEach
    public void init() {
      user = User.getInstance();
      user.setAccountInfo(accountMock);
    }


    @Test
    public void get_account() {


        assertEquals(accountMock, user.getAccount());
    }





}