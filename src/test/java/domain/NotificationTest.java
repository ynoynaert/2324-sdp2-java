package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationTest {

    @Test
    public void all_around_test() {

        Notification notification = new Notification();


        notification.setAccountId(1L);
        notification.setType("1");
        notification.setText("Texting Textington");
        notification.setData("Data Datington");
        notification.setFrom(2L);
        notification.setReceived(1);
        notification.setSeen(0);
        notification.setNotificationTypeId(3);


        assertEquals(1L, notification.getAccountId());
        assertEquals("1", notification.getType());
        assertEquals("Texting Textington", notification.getText());
        assertEquals("Data Datington", notification.getData());
        assertEquals(2L, notification.getFrom());
        assertEquals(1, notification.getReceived());
        assertEquals(0, notification.getSeen());
        assertEquals(3, notification.getNotificationTypeId());
    }


}