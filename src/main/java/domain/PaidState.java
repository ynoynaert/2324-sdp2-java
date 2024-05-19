package domain;

class PaidState extends PaymentState{

	public PaidState(SubOrder subOrder) {
		super(subOrder);
	}

	@Override
	public int getId() {
		return 1;
	}

	@Override
	public String getName() {
		return "Paid";
	}
	
}
