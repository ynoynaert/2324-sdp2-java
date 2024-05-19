package domain;

public class OrderController {

	private OrderManager manager;
	
	public OrderController() {
		this.manager= new OrderManager();
	}
	
	public String getBuyerName(int id) {
		return manager.getBuyerName(id);
	}
	
	public void updateOrderPaymentStatus(Order order) {
		manager.updateOrderPaymentStatus(order);
	}
	
	public double getItemPriceVAT() {
		return manager.getItemPriceVAT();
	}
	
}

