package dtos;

import java.sql.Date;

public class SubOrderItemsDTO {

	public String productName;
	public int quantity;
	public boolean productAvailability;
	public double netAmount;
	public double total;
    
	public SubOrderItemsDTO(String productName, int quantity, boolean productAvailability, double netAmount, double total) {
		super();
		this.productName = productName;
		this.quantity = quantity;
		this.productAvailability = productAvailability;
		this.netAmount = netAmount;
		this.total = total;
	}  

}