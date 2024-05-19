package domain;

import javafx.collections.ObservableList;

public class BusinessController {

	private BusinessManager manager;
	
	public BusinessController() {
		this.manager = new BusinessManager();
	}
	
	public ObservableList<Business> getAllBusinesses() {
		return manager.getBusinesses();
	}
	
	public Business getBusinessById(long id) {
		return manager.getBusinessById(id);
	}
	
	public void updateBusiness(Business business) {
		manager.updateBusiness(business);
	}
	
	public Business addBusiness(Business business) {
		return manager.addBusiness(business);
	}
}
