package domain;

public class NotificationController {

	private NotificationManager manager;

	public NotificationController() {
		this.manager = new NotificationManager();
	}
	
	public void sendPaymentReminder(String suborderId, Supplier supplier) {
		manager.sendPaymentReminder(suborderId, supplier);
	}
}
