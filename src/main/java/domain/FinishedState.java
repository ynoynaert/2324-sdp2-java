package domain;

class FinishedState extends OrderState{

	public FinishedState(SubOrder subOrder) {
		super(subOrder);
	}
	
	@Override
	public int getId() {
		return 3;
	}
	
	@Override
	public String getName() {
		return "Finished";
	}
	
}
