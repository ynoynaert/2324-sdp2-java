package repository;

import domain.PaymentMethod;

public interface PaymentMethodDao extends GenericDao<PaymentMethod> {

	public PaymentMethod getPaymentMethodByName(String method);
	
}
