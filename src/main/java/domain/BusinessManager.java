package domain;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.AccountDaoJpa;
import repository.BusinessDao;
import repository.BusinessDaoJpa;

public class BusinessManager {

	private BusinessDao businessRepo;
	
	public BusinessManager() {
		businessRepo = new BusinessDaoJpa();
	}
	
	public ObservableList<Business> getBusinesses() {
		List<Business> businessList = businessRepo.getBusinesses();
		return FXCollections.observableArrayList(businessList);
	}
	
	public Business getBusinessById(long id) {
		return businessRepo.getBusinessById(id);
	}
	
	public void updateBusiness(Business business) {
		BusinessDaoJpa.startTransaction();
		businessRepo.update(business);
		BusinessDaoJpa.commitTransaction();
	}
	
	public Business addBusiness(Business business) {
		BusinessDaoJpa.startTransaction();
		businessRepo.insert(business);
		BusinessDaoJpa.commitTransaction();
		return business;
	}
	
}
