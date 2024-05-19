package domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "products")
@Getter
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "category_id", nullable = false)
	private int categoryId;
	
	@Column(name = "product_availability", nullable = false)
	private boolean productAvailability;
	
	@Column(name = "image_url", nullable = false)
	private String imageUrl;
	
	@Column(name = "supplier_id", nullable = false)
	private int supplierId;
	
}
