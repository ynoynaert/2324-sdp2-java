package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.NotificationDao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationManagerTest {

    @Mock
    private NotificationDao notificationDao;
    @Mock
    Supplier supplier;

    @InjectMocks
    private NotificationManager notificationManager;

    @Test
    public void send_payment_reminder() {

        when(supplier.getId()).thenReturn(1L);
        Business business = new Business();
        business.setId(2L);
        when(supplier.getBusiness()).thenReturn(business);

        notificationManager.sendPaymentReminder("12345", supplier);
        verify(notificationDao, times(1)).insert(any(Notification.class));
    }
}