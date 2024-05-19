package domain;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.AccountDao;
import repository.AccountDaoJpa;
import repository.BusinessDao;
import repository.BusinessDaoJpa;

public class AccountManager {

	private Account a;
	private AccountDao accountRepo;
	private User u;
	private BusinessDao businessRepo;
	
	public AccountManager() {
		accountRepo = new AccountDaoJpa();
		businessRepo = new BusinessDaoJpa();
	}
	
	public AccountController login(String email, String password) {
		a = accountRepo.getAccountByEmail(email);
		a.checkPassword(password);
		u = User.getInstance();
		u.setAccountInfo(a);
		return new AccountController(this);
	}
	
	public Client getClientById(Long id) {
		return accountRepo.getClientById(id);
	}

	public ObservableList<Client> getClientsBySupplierId(Long id) {
		List<Client> clientsList = accountRepo.getClientsBySupplierId(id);
		return FXCollections.observableArrayList(clientsList);
	}
	
	public Long getClientAmount(long id) {
		return accountRepo.getClientAmount(id);
	}

	public String getIsActive(long id) {		
		return accountRepo.getIsActive(id).stream().map(String::trim).anyMatch("yes"::equalsIgnoreCase) ? "yes" : "no";
	}
	
	public Client updateClient(Client client) {
		AccountDaoJpa.startTransaction();
		accountRepo.update(client);
		AccountDaoJpa.commitTransaction();
		return client;
	}
	
	public Supplier addSupplier(Supplier supplier) {
		AccountDaoJpa.startTransaction();
		accountRepo.insert(supplier);
		AccountDaoJpa.commitTransaction();
		return supplier;
	}
	
	public Client addClient(Client client) {
		AccountDaoJpa.startTransaction();
		accountRepo.insert(client);
		AccountDaoJpa.commitTransaction();
		return client;
	}

	public Supplier updateSupplier(Supplier supplier) {
		AccountDaoJpa.startTransaction();
		accountRepo.update(supplier);
		AccountDaoJpa.commitTransaction();
		return supplier;
	}
	
	public void updateBusiness(Business business) {
        BusinessDaoJpa.startTransaction();
        businessRepo.update(business);
        BusinessDaoJpa.commitTransaction();
    }
}
