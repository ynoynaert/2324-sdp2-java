package repository;

import domain.PaymentMethod;
import domain.Supplier;

public class PaymentMethodDaoJpa extends GenericDoaJpa<PaymentMethod> implements PaymentMethodDao{

	public PaymentMethodDaoJpa() {
		super(PaymentMethod.class);
		// TODO Auto-generated constructor stub
	}

		@Override
		public PaymentMethod getPaymentMethodByName(String method) {
			PaymentMethod p =  em.createNamedQuery("PaymentMethod.findByMethod", PaymentMethod.class)
					.setParameter("method", method)
		            .getSingleResult();
			return p;
			
		}

}
