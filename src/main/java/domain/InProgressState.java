package domain;

class InProgressState extends OrderState{

	public InProgressState(SubOrder subOrder) {
		super(subOrder);
	}
	
	@Override
	public int getId() {
		return 2;
	}
	
	@Override
	public boolean setFinished() {
		subOrder.toOrderState(new FinishedState(subOrder));
		return true;
	}

	@Override
	public String getName() {
		return "In Progress";
	}

}
