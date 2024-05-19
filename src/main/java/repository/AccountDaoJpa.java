package repository;


import java.util.ArrayList;
import java.util.List;

import domain.Account;
import domain.Admin;
import domain.Business;
import domain.Client;
import domain.Supplier;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;

public class AccountDaoJpa extends GenericDoaJpa<Account> implements AccountDao{

	public AccountDaoJpa() {
		super(Account.class);
	}

	@Override
	public Account getAccountByEmail(String email) throws EntityNotFoundException {
		
		try {
		    Supplier s = em.createNamedQuery("Supplier.findSupplierByEmail", Supplier.class)
		            .setParameter("accountEmail", email)
		            .getSingleResult();
		    return s;
		} catch (NoResultException ex) {
		    try {
		        Admin a = em.createNamedQuery("Admin.findAdminByEmail", Admin.class)
		                .setParameter("accountEmail", email)
		                .getSingleResult();
		        return a;
		    } catch (NoResultException ex2) {
		        throw new EntityNotFoundException("Invalid email or password");
		    }
		}
	}

	@Override
    public Client getClientById(Long id) {
        try {
            Client c = em.createNamedQuery("Client.findClientById", Client.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return c;
        }  catch (NoResultException ex) {
            throw new EntityNotFoundException("client not found");
        }
    }

    @Override
    public List<Client> getClientsBySupplierId(Long id) {
        try {
            List<Client> c = em.createNamedQuery("Client.findClientsBySupplierId", Client.class)
                    .setParameter("id", id)
                    .getResultList();
            return c;
        }  catch (NoResultException ex) {
            throw new EntityNotFoundException("client not found");
        }
    }
	
	@Override
	public Long getClientAmount(long id) {
		try {
			return em.createNamedQuery("Business.findClientAmount", Long.class)
					.setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException ex) {
	        throw new EntityNotFoundException("there are no clients for this business");
	  }
	}

	@Override
	public List<String> getIsActive(long id) {
		try {
			List<Object[]> list = em.createNamedQuery("Business.findIfActive", Object[].class)
					.setParameter("id", id)
					.getResultList();
			
			List<String> isActiveList = new ArrayList<>();
	        for (Object[] result : list) {
	            String supplierIsActive = (String) result[0];
	            String clientIsActive = (String) result[1];
	            isActiveList.add(supplierIsActive);
	            isActiveList.add(clientIsActive);
	        }
	        
	        return isActiveList;
		} catch (NoResultException ex) {
	        throw new EntityNotFoundException("there are no clients for this business");
		}
	}

}