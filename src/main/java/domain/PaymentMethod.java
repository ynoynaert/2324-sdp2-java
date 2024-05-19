package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NamedQueries({
		@NamedQuery(name = "PaymentMethod.findByMethod", query = "SELECT p FROM PaymentMethod p WHERE p.method = :method"), 
		//@NamedQuery(name = "PaymentMethod.findAll", query = "SELECT p FROM PaymentMethod p "),

})
@Entity
@Table(name = "payment_methods")
@Getter
@NoArgsConstructor
public class PaymentMethod implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "method", nullable = false)
	private String method;

	@ManyToMany(mappedBy = "paymentMethods", cascade = CascadeType.PERSIST)
	private List<Supplier> suppliers = new ArrayList<>();

	public PaymentMethod(String method) {
		this.method = method;
	}

	public void addSupplier(Supplier s) {
		suppliers.add(s);
	}

}
