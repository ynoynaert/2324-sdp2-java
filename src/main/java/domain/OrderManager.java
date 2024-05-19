package domain;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dtos.SubOrderInformationDTO;
import dtos.SubOrderItemsDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repository.OrderDao;
import repository.OrderDaoJpa;
import repository.SubOrderDao;
import repository.SubOrderDaoJpa;

public class OrderManager {

	private OrderDao orderRepo; 
	private OrderItem o;
	
	public OrderManager() {
		orderRepo = new OrderDaoJpa();
	}

	public String getBuyerName(int id) {
		return orderRepo.getBuyerName(id);
	}

	public void updateOrderPaymentStatus(Order order) {
		OrderDaoJpa.startTransaction();
		orderRepo.update(order);
		OrderDaoJpa.commitTransaction();
	}
	
	public double getItemPriceVAT() {
		return o.getItemPriceVAT();
	}
}
