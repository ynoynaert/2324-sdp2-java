package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@NamedQueries({
    @NamedQuery(
            name = "Supplier.findSupplierByEmail",
            query = "SELECT s FROM Supplier s WHERE s.email = :accountEmail AND s.enabled = 1"
        )
})
@Table(name = "suppliers")
@Getter
public class Supplier extends Account {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    
    @ManyToMany( cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "payment_methods_suppliers", 
            joinColumns = @JoinColumn(name = "supplier_id"), 
            inverseJoinColumns = @JoinColumn(name = "payment_method_id") 
        )
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_id")
    private Business business;

    @Setter
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

	public Supplier(Long id, String email, String password, String firstname, String lastname,
			String phoneNumber, Business business, boolean enabled) {
		super(id, email, password, firstname, lastname);
		this.phoneNumber = phoneNumber;
		this.business = business;
		this.enabled = enabled;
	}
    
	public Supplier(String email, String password, String firstname, String lastname,
			String phoneNumber, Business business, boolean enabled) {
		super(email, password, firstname, lastname);
		this.phoneNumber = phoneNumber;
		this.business = business;
		this.enabled = enabled;
	}
    
    public void addPaymentMethod(PaymentMethod paymentMethod) {
    	paymentMethods.add(paymentMethod);
    }
    
    public List<String> validate() {
		List<String> errors = new ArrayList<>();
        if (this.phoneNumber.isBlank() || super.getEmail().isBlank()){
        	errors.add("Please fill in all fields.");
        	return errors;
        }
        if (!Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", super.getEmail())) {
        	errors.add("Please fill in a valid email address.");
        }
        return errors;
	}
}
