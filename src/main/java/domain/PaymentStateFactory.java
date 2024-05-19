package domain;

import java.rmi.UnexpectedException;

public class PaymentStateFactory {

	public PaymentState createPaymentState(int id, SubOrder subOrder) throws UnexpectedException {
		return switch (id) {
		case 1 -> new UnpaidState(subOrder);
		case 2 -> new PaidState(subOrder);
		default -> throw new UnexpectedException("payment state in database doesn't work");
		};
	}

}
