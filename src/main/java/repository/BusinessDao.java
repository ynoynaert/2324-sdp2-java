package repository;

import java.util.List;

import domain.Business;

public interface BusinessDao extends GenericDao<Business>{

	public List<Business> getBusinesses();
	public Business getBusinessById(long id);
	
}
