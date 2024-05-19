package repository;

import java.util.List;

import domain.SubOrder;
import dtos.SubOrderInformationDTO;
import dtos.SubOrderItemsDTO;

public interface SubOrderDao extends GenericDao<SubOrder> {
	
	public List<SubOrder> getSubOrdersBySupplier(Long supplierId);
	public List<SubOrder> getSubOrdersBySupplierAndClient(Long supplierId, Long clientId);
	public SubOrder getSubOrderByUuid(String uuid);
	public List<SubOrderItemsDTO> getItemsBySuborderId(int id);
	public SubOrderInformationDTO getSubOrderInformation(int id);
	public double getTotpriceSuborder(String uuid);
	
}
