package domain;

import javafx.collections.ObservableList;

public class AccountController {
	
	private AccountManager manager;
	
	public AccountController(AccountManager manager) {
		this.manager=manager;
	}
	
	public User getAccount() {
		return User.getInstance();
	}
	
	public Client getClientById(Long id) {
		return manager.getClientById(id);
	}
	
	public ObservableList<Client> getClientsBySupplierId(Long id){
		return manager.getClientsBySupplierId(id);
	}
	
	public Long getClientAmount(long id) {
		return manager.getClientAmount(id);
	}
	
	public String getIsActive(long id) {
		return manager.getIsActive(id);
	}
	
	public Client updateClient(Client client) {
		return manager.updateClient(client);
	}

	public Supplier addSupplier(Supplier supplier) {
		return manager.addSupplier(supplier);
	}
	
	public Client addClient(Client client) {
		return manager.addClient(client);
	}

	public Supplier updateSupplier(Supplier supplier) {
		return manager.updateSupplier(supplier);
	}
	
	public void logout() {
		User.getInstance().logout();
    }
	
	public void updateBusiness(Business business) {
        manager.updateBusiness(business);
    }
}
