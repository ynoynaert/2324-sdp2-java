package repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import domain.SubOrder;
import dtos.SubOrderInformationDTO;
import dtos.SubOrderItemsDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;

public class SubOrderDaoJpa extends GenericDoaJpa<SubOrder> implements SubOrderDao {

	public SubOrderDaoJpa() {
		super(SubOrder.class);
	}

	@Override
	public List<SubOrder> getSubOrdersBySupplier(Long supplierId) {
		try {
			return em.createNamedQuery("SubOrder.findBySupplier",SubOrder.class)
					.setParameter("supplierId", supplierId)
					.getResultList();
		 } catch (NoResultException ex) {
		        throw new EntityNotFoundException("You have no suborders");
		    }
	}
	
	@Override
	public List<SubOrder> getSubOrdersBySupplierAndClient(Long supplierId, Long clientId) {
		try {
			return em.createNamedQuery("SubOrder.findBySupplierAndClient",SubOrder.class)
					.setParameter("supplierId", supplierId)
					.setParameter("clientId", clientId)
					.getResultList();
		 } catch (NoResultException ex) {
		        throw new EntityNotFoundException("You have no suborders");
		    }
	}
	
	@Override
	public double getTotpriceSuborder(String uuid) {
		try {
			List<Double> price = em.createNamedQuery("SubOrder.findPriceByUuid",Double.class)
					.setParameter("uuid", uuid)
					.getResultList();
			return price.stream().reduce(0.0, (subtotal, element) -> subtotal + element);
		 } catch (NoResultException ex) {
		        throw new EntityNotFoundException("No suborder found");
		    }

	}

	@Override
	public SubOrder getSubOrderByUuid(String uuid) {
	    try {
	        return em.createNamedQuery("SubOrder.findByUuid", SubOrder.class)
	                .setParameter("uuid", uuid)
	                .getSingleResult();
	    } catch (NoResultException ex) {
	        throw new EntityNotFoundException("SubOrder not found");
	    }
	}
	
	@Override
	public List<SubOrderItemsDTO> getItemsBySuborderId(int id){
		try {
			List<Object[]> results = em.createNamedQuery("OrderItem.findItemsBySuborderId", Object[].class)
	                .setParameter("suborderId", id)
	                .getResultList();
	        List<SubOrderItemsDTO> subOrderItemsList = new ArrayList<>();

	        for (Object[] result : results) {
	            SubOrderItemsDTO subOrderItemsDTO = new SubOrderItemsDTO((String) result[0],(int) result[1],(boolean) result[2],(double) result[3],(double) result[4]);
	            subOrderItemsList.add(subOrderItemsDTO);
	        }
	        return subOrderItemsList;
		}catch(NoResultException ex) {
			throw new EntityNotFoundException("You have no order items");
		}
	}

	@Override
	public SubOrderInformationDTO getSubOrderInformation(int id) {
		try {
			Object[] results = em.createNamedQuery("OrderItem.findInformationById", Object[].class)
	                .setParameter("id", id)
	                .getSingleResult();

			 SubOrderInformationDTO subOrderItemsList = new SubOrderInformationDTO(
					 	(String) results[0],
					 	(String) results[1],
			            (String) results[2],
			            (String) results[3],
			            (LocalDate) results[4], 
			            (String) results[5], 
			            (String) results[6],   
			            (String) results[7],         
			            (String) results[8],            
			            (String) results[9],            
			            (int) results[10],               
			            (LocalDate) results[11]
			            //(double) results[12]           
			    );

	        return subOrderItemsList;
		}catch(NoResultException ex) {
			throw new EntityNotFoundException("You have no order items");
		}
	}
	
}
