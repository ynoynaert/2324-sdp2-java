package domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@NamedQueries({
    @NamedQuery(
    		name = "Admin.findAdminByEmail",
            query = "SELECT a FROM Admin a WHERE a.email = :accountEmail"
        )
})
@Table(name="accounts")
@Getter
public class Admin extends Account{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "is_admin", nullable = false)
	private String isAdmin;
	
	@Column(name = "name", nullable = false)
	private String name;	
}
