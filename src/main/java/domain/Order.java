package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="orders")
@NoArgsConstructor
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Getter
	@Column(name = "id", nullable = false)
	private int id;
	
	@Getter
    @Column(name = "uuid", nullable = false)
	private String uuid;
	
	@Getter
    @Column(name = "billing_address_street", nullable = false)
	private String billingAddressStreet;
	
	@Getter
    @Column(name = "billing_address_zipcode", nullable = false)
	private String billingAddressZipcode;
	
	@Getter
    @Column(name = "billing_address_country", nullable = false)
	private String billingAddressCountry;
	
	@Getter
    @Column(name = "billing_address_street_nr", nullable = false)
	private String billingAddressStreetNr;
	
	@Getter
    @Column(name = "billing_address_city", nullable = false)
	private String billingAddressCity;

	@Getter
    @Column(name = "shipping_address_street", nullable = false)
	private String shippingAddressStreet;
	
	@Getter
    @Column(name = "shipping_address_zipcode", nullable = false)
	private String shippingAddressZipcode;
	
	@Getter
    @Column(name = "shipping_address_country", nullable = false)
	private String shippingAddressCountry;
	
	@Getter
    @Column(name = "shipping_address_street_nr", nullable = false)
	private String shippingAddressStreetNr;
	
	@Getter
    @Column(name = "shipping_address_city", nullable = false)
	private String shippingAddressCity;
	
	@Getter
    @Column(name = "remark", nullable = false)
	private String remark;
	
	@Getter
    @Column(name = "payment_period", nullable = false)
	private String paymentPeriod;
	
	@Getter
	@Column(name = "created_at", nullable = false)
	private LocalDate date;
	
	@Getter
	@Column(name = "payment_status_id", nullable = false)
	private int paymentStatusId;
	
	@Getter
	@Column(name = "order_status_id", nullable = false)
	private int orderStatusId;
	
	@Getter
	@Transient
	private PaymentState paymentState;
	
	@Getter
	@Transient
	private OrderState orderState;
	
	@Getter
	@OneToMany
	@JoinColumn(name = "id")
	private Set<SubOrder> subOrders = new HashSet<SubOrder>();
	
	@Getter
	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName ="account_id")
	private Client client;

	public void setPaymentState() {
		int maxId = subOrders.stream().map(s -> s.getCurrentPState().getId()).reduce(Integer.MIN_VALUE, Integer::max);
		switch(maxId) {
			case 2 -> {
				this.paymentState = new UnpaidState(null);
				this.paymentStatusId = 2;
			}
			case 1 -> { 
				this.paymentState = new PaidState(null);
				this.paymentStatusId = 2;
			}
			default -> throw new IllegalArgumentException("error by OrderState");
		}
	}
	
	public void setOrderState() {
		int maxId = subOrders.stream().map(s -> s.getCurrentOState().getId()).reduce(Integer.MIN_VALUE, Integer::max);
		switch(maxId) {
			case 3 -> {
				this.orderState = new OpenState(null);
				this.orderStatusId = 3;
			}
			case 2 -> {
				this.orderState = new InProgressState(null);
				this.orderStatusId = 2;
			}
			case 1 -> {
				this.orderState = new FinishedState(null);
				this.orderStatusId = 1;
			}
			default ->throw new IllegalArgumentException("error by OrderState");
		}
	}

	public Order(int id, String uuid, String billingAddressStreet, String billingAddressZipcode,
			String billingAddressCountry, String billingAddressStreetNr, String billingAddressCity,
			String shippingAddressStreet, String shippingAddressZipcode, String shippingAddressCountry,
			String shippingAddressStreetNr, String shippingAddressCity, String remark, String paymentPeriod,
			LocalDate date, Set<SubOrder> subOrders, Client client) {
		this.id = id;
		this.uuid = uuid;
		this.billingAddressStreet = billingAddressStreet;
		this.billingAddressZipcode = billingAddressZipcode;
		this.billingAddressCountry = billingAddressCountry;
		this.billingAddressStreetNr = billingAddressStreetNr;
		this.billingAddressCity = billingAddressCity;
		this.shippingAddressStreet = shippingAddressStreet;
		this.shippingAddressZipcode = shippingAddressZipcode;
		this.shippingAddressCountry = shippingAddressCountry;
		this.shippingAddressStreetNr = shippingAddressStreetNr;
		this.shippingAddressCity = shippingAddressCity;
		this.remark = remark;
		this.paymentPeriod = paymentPeriod;
		this.date = date;
		this.subOrders = subOrders;
		this.client = client;
		setPaymentState();
		setOrderState();
	}
	
}
