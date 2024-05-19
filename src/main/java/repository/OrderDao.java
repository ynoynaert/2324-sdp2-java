package repository;

import java.util.List;

import domain.Order;
import domain.SubOrder;
import dtos.SubOrderInformationDTO;
import dtos.SubOrderItemsDTO;

public interface OrderDao extends GenericDao<Order>{

	public String getBuyerName(long id);
	
}
