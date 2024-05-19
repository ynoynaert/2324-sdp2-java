package domain;

import repository.NotificationDao;
import repository.NotificationDaoJpa;

public class NotificationManager {

    private NotificationDao notificationRepo;

    public NotificationManager() {
    	notificationRepo = new NotificationDaoJpa();
    }

    public void sendPaymentReminder(String uuid, Supplier supplier) {
        Notification n = new Notification();
        n.setFrom(supplier.getId());
        n.setType("client");
        n.setText("You've gotten a payment reminder");
        n.setData(String.format("{\"orderId\":\"%s\"}", uuid));
        n.setReceived(0);
        n.setSeen(0);
        n.setAccountId(supplier.getBusiness().getId());
        n.setNotificationTypeId(7);

        notificationRepo.insert(n);
    }
}
