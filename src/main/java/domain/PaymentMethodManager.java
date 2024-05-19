package domain;

import java.util.List;

import repository.PaymentMethodDao;
import repository.PaymentMethodDaoJpa;

public class PaymentMethodManager {

	private PaymentMethodDao paymentMethodRepo;

	public PaymentMethodManager() {
		paymentMethodRepo = new PaymentMethodDaoJpa();
	}

	public PaymentMethod getPaymentMethodByName(String method) {
		return paymentMethodRepo.getPaymentMethodByName(method);
	}
	
	public List<PaymentMethod> getAllPaymentMethods (){
		return paymentMethodRepo.findAll();
	}

}
