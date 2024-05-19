package repository;

import java.util.List;
import java.util.Set;

import domain.Account;
import domain.Business;
import domain.Client;
import jakarta.persistence.EntityNotFoundException;

public interface AccountDao extends GenericDao<Account> {

	public Account getAccountByEmail(String email) throws EntityNotFoundException;
	public Client getClientById(Long id);
	public List<Client> getClientsBySupplierId(Long id);
	public Long getClientAmount(long id);
	public List<String> getIsActive(long id);

	
}
