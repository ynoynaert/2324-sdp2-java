package dtos;

import java.time.LocalDate;

public class SubOrderInformationDTO {

	public String businessName;
    public String phoneNumber;
    public String email;
    public String suborderUuid;
    public LocalDate suborderCreatedAt;
    public String shippingAddressCity;
    public String shippingAddressCountry;
    public String shippingAddressStreet;
    public String shippingAddressStreetNr;
    public String shippingAddressZipcode;
    public int orderStateId;
    public LocalDate paymentReminder;
    
	public SubOrderInformationDTO(String businessName, String phoneNumber, String email, String suborderUuid,
			LocalDate suborderCreatedAt, String shippingAddressCity, String shippingAddressCountry,
			String shippingAddressStreet, String shippingAddressStreetNr, String shippingAddressZipcode,
			int orderStateId, LocalDate paymentReminder) {
		super();
		this.businessName = businessName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.suborderUuid = suborderUuid;
		this.suborderCreatedAt = suborderCreatedAt;
		this.shippingAddressCity = shippingAddressCity;
		this.shippingAddressCountry = shippingAddressCountry;
		this.shippingAddressStreet = shippingAddressStreet;
		this.shippingAddressStreetNr = shippingAddressStreetNr;
		this.shippingAddressZipcode = shippingAddressZipcode;
		this.orderStateId = orderStateId;
		this.paymentReminder = paymentReminder;
	}
    
    
	
}
