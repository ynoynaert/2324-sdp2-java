package repository;

import java.util.List;

import domain.Business;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;

public class BusinessDaoJpa extends GenericDoaJpa<Business> implements BusinessDao{

	public BusinessDaoJpa() {
		super(Business.class);
	}
	
	@Override
	public List<Business> getBusinesses() {
		try {
			return em.createNamedQuery("Business.findAll", Business.class)
					.getResultList();
		} catch (NoResultException ex) {
	        throw new EntityNotFoundException("there are no businesses");
	    }
	}
	
	@Override
	public Business getBusinessById(long id) {
		try {
			return em.createNamedQuery("Business.findById", Business.class)
					.setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException ex) {
			throw new EntityNotFoundException("Unable to find business with this ID.");
		}
	}
	
}
