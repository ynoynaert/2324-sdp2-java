package domain;
class UnpaidState extends PaymentState{

	public UnpaidState(SubOrder subOrder) {
		super(subOrder);
	}
	
	@Override
	public boolean pay() {
		subOrder.toPaymentState(new PaidState(subOrder));
		return true;
	}

	@Override
	public int getId() {
		return 2;
	}

	@Override
	public String getName() {
		return "Unpaid";
	}
	
}
