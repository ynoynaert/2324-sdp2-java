package domain;

import java.util.List;

public class PaymentMethodController {

	private PaymentMethodManager manager;
	
	public PaymentMethodController() {
		this.manager = new PaymentMethodManager();
	}
	
	public PaymentMethod getPaymentMethodByName(String method) {
		return manager.getPaymentMethodByName(method);
	}
	
	public List<PaymentMethod> getAllPaymentMethods (){
		return manager.getAllPaymentMethods();
	}
}
