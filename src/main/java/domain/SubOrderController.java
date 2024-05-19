package domain;

import java.util.List;
import java.util.Set;

import dtos.SubOrderInformationDTO;
import dtos.SubOrderItemsDTO;
import javafx.collections.ObservableList;
import repository.OrderDaoJpa;
import repository.SubOrderDaoJpa;

public class SubOrderController {
	
	private SubOrderManager manager;
	
	public SubOrderController() {
		this.manager= new SubOrderManager();
	}
	
	public ObservableList<SubOrder> getSubOrders(Long supplierId){
		return manager.getSubOrders(supplierId);
	}
	
	public double getTotalPrice(String uuid){
		return manager.getTotalPrice(uuid);
	}
	
	public Set<SubOrder> getSubOrdersByClient(Long supplierId, Long clientId){
		return manager.getSubOrdersByClient(supplierId, clientId);
	}
	
	public SubOrder getSubOrder(String uuid) {
		return manager.getSubOrder(uuid);
	}
	
	public List<SubOrderItemsDTO> getItemsByOrderId(int id){
		return manager.getItemsByOrderId(id);
	}
	
	public SubOrderInformationDTO getSubOrderInformation(int id){
		return manager.getSubOrderInformation(id);
	}
	
	public boolean setInProgress() {
		throw new UnsupportedOperationException();
	}
	
	public boolean setFinished() {
		throw new UnsupportedOperationException();
	}
	
	public void updateSubOrder(SubOrder suborder) {
		manager.updateSubOrder(suborder);
	}
	
	
	
}
