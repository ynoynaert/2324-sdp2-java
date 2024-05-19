package domain;

class OpenState extends OrderState{

	public OpenState(SubOrder subOrder) {
		super(subOrder);
	}
	
	@Override
	public int getId() {
		return 1;
	}
	
	@Override
	public boolean setInProgress() {
		subOrder.toOrderState(new InProgressState(subOrder));
		return true;
	}
	
	@Override
	public String getName() {
		return "Open";
	}

}
