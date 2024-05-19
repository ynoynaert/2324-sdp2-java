package domain;

import java.rmi.UnexpectedException;

public class OrderStateFactory {

	public OrderState createOrderState(int id, SubOrder subOrder) throws UnexpectedException {
		return switch (id) {
		case 1 -> new OpenState(subOrder);
		case 2 -> new InProgressState(subOrder);
		case 3 -> new FinishedState(subOrder);
		default -> throw new UnexpectedException("order state in database doesn't work");
		};
		
	}
}
