package domain;



import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Client.findClientById",
                query = "SELECT c FROM Client c WHERE c.id = :id"
        ),
        @NamedQuery(
                name = "Client.findClientsBySupplierId",
                query = "SELECT DISTINCT o.client "+
                        "FROM SubOrder s "
                        +
                        "JOIN s.order o  "
                        +
                        "WHERE s.supplierId = :id "
                        +
                        "GROUP BY o.client.id, o.client.firstname, o.client.lastname, o.client.email, o.client.phoneNumber"
        )
})
@Table(name = "clients")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Client extends Account{

    private static final long serialVersionUID = 1L;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Setter
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_id")
    private Business business;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;

	public Client(Long id, String email, String password, String firstname, String lastname, String phoneNumber, boolean enabled, Business business, List<Order> orders) {
		super(id, email, password, firstname, lastname);
		this.phoneNumber = phoneNumber;
		this.enabled = enabled;
		this.business = business;
		this.orders = orders;
	}
	
	public Client(String email, String password, String firstname, String lastname, String phoneNumber, boolean enabled, Business business, List<Order> orders) {
		super(email, password, firstname, lastname);
		this.phoneNumber = phoneNumber;
		this.enabled = enabled;
		this.business = business;
		this.orders = orders;
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
