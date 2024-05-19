package repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import domain.Order;
import domain.OrderState;
import domain.SubOrder;
import dtos.SubOrderInformationDTO;
import dtos.SubOrderItemsDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;

public class OrderDaoJpa extends GenericDoaJpa<Order> implements OrderDao{

	public OrderDaoJpa() {
		super(Order.class);
	}

	@Override
	public String getBuyerName(long orderId) {
		try {
			return em.createNamedQuery("Business.findBuyerName",String.class)
					.setParameter("orderId", orderId)
					.getSingleResult();
		} catch (NoResultException ex) {
	        throw new EntityNotFoundException("Name of buyer not found");
	    }
	}    	

}
