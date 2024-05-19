package domain;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dtos.SubOrderInformationDTO;
import dtos.SubOrderItemsDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.SubOrderDao;
import repository.SubOrderDaoJpa;

public class SubOrderManager {

	private SubOrderDao subOrderRepo;
	
	public SubOrderManager() {
		subOrderRepo = new SubOrderDaoJpa();
	}
	
	public double getTotalPrice(String uuid){
		return subOrderRepo.getTotpriceSuborder(uuid) * 1.21;
	}

	public ObservableList<SubOrder> getSubOrders(Long supplierId){
		List<SubOrder> subOrdersList = subOrderRepo.getSubOrdersBySupplier(supplierId);
	    return FXCollections.observableArrayList(subOrdersList);	
	}
	
	public Set<SubOrder> getSubOrdersByClient(Long supplierId,Long clientId){
		return subOrderRepo.getSubOrdersBySupplierAndClient(supplierId, clientId).stream().collect(Collectors.toSet());
	}
	
	public SubOrder getSubOrder(String uuid) {
		return subOrderRepo.getSubOrderByUuid(uuid);
	}

	public List<SubOrderItemsDTO> getItemsByOrderId(int id){
		return subOrderRepo.getItemsBySuborderId(id);
	}
	
	public SubOrderInformationDTO getSubOrderInformation(int id){
		return subOrderRepo.getSubOrderInformation(id);
	}
	
	public void updateSubOrder(SubOrder suborder) {
		SubOrderDaoJpa.startTransaction();
		subOrderRepo.update(suborder);
		SubOrderDaoJpa.commitTransaction();
	}
}
