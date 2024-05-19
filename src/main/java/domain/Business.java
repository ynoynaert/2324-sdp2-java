package domain;

import java.net.MalformedURLException;
import java.net.URL;
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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NamedQueries({
        @NamedQuery(name = "Business.findAll", query = "SELECT b FROM Business b WHERE b.id <> 1"),
        @NamedQuery(name = "Business.findClientAmount", query = "SELECT COUNT(DISTINCT o.client.business.id) FROM Order o WHERE o.id IN ("
                + "													SELECT so.order.id FROM SubOrder so WHERE so.supplierId = ("
                + "														SELECT s.id FROM Supplier s WHERE s.business.id = :id))"),
        @NamedQuery(name = "Business.findIfActive", query = "SELECT"
                + " CASE"
                + " WHEN s.enabled = true THEN 'Yes'"
                + " ELSE 'No'"
                + " END,"
                + " CASE"
                + " WHEN c.enabled = true THEN 'Yes'"
                + " ELSE 'No'"
                + " END"
                + " FROM Business b"
                + " JOIN Supplier s on s.business.id = b.id"
                + " JOIN Client c on c.business.id = b.id"
                + " WHERE b.id = :id"),
        @NamedQuery(name = "Business.findById", query= "SELECT b from Business B where b.id = :id"),
        @NamedQuery(name = "Business.findBuyerName", query = "SELECT o.client.business.nameBusiness FROM Order o WHERE o.id = :orderId")
})
@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; //stelt accounnt id voor mag enkel hier

    @Column(name = "name", nullable = false)
    private String nameBusiness;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "vat_number", nullable = false)
    private String vatNumber;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "zipcode", nullable = false)
    private String zipcode;

    @Column(name = "street_nr", nullable = false)
    private String streetNr;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "sector", nullable = false)
    private String sector;

    @OneToOne(mappedBy = "business", cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id")
    @Setter
    private Client client;

    @OneToOne(mappedBy = "business", cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id")
    @Setter
    private Supplier supplier;

	public Business(Long id, String imageUrl, String nameBusiness, String street, String vatNumber, String country, String zipcode, String streetNr, String city, String sector, Supplier supplier, Client client) {
		this.id = id;
		this.imageUrl = imageUrl;
		this.nameBusiness = nameBusiness;
		this.street = street;
		this.vatNumber = vatNumber;
		this.country = country;
		this.zipcode = zipcode;
		this.streetNr = streetNr;
		this.city = city;
		this.sector = sector;
		this.client = client;
		this.supplier = supplier;
	}
	
	public Business(String imageUrl, String nameBusiness, String street, String vatNumber, String country, String zipcode, String streetNr, String city, String sector, Supplier supplier, Client client) {
		this.imageUrl = imageUrl;
		this.nameBusiness = nameBusiness;
		this.street = street;
		this.vatNumber = vatNumber;
		this.country = country;
		this.zipcode = zipcode;
		this.streetNr = streetNr;
		this.city = city;
		this.sector = sector;
		this.client = client;
		this.supplier = supplier;
	}
	
	public List<String> validate() {
		List<String> errors = new ArrayList<>();
        if (this.nameBusiness.isBlank() || this.sector.isBlank() ||
        		this.vatNumber.isBlank() || this.street.isBlank() ||
        		this.streetNr.isBlank() || this.city.isBlank() ||
        		this.zipcode.isBlank() || this.country.isBlank() ||
        		this.imageUrl.isBlank()) {
        	errors.add("Please fill in all fields. ");
        	return errors;
        }
        if (!Pattern.matches("[A-Za-z]{2}\\d{10}", this.vatNumber)) {
        	errors.add("VAT number should start with 2 letters and then 10 numbers. ");
        } 
        if (!Pattern.matches("[0-9]+[A-Za-z0-9]*", this.streetNr)) {
        	errors.add("Street number should start with a number. ");
        }
        if (this.country.length() != 2) {
			errors.add("Country code should be two letters. ");
		}
        if (!Pattern.matches("\\d+", this.zipcode)) {
			errors.add("Zipcode should be a number. ");
		}
        if (containsNumbers(this.sector)) {
			errors.add("Industry cannot contain numbers. ");
		}
        if (containsNumbers(this.street)) {
			errors.add("Street cannot contain numbers. ");
		}
        if (containsNumbers(this.city)) {
			errors.add("City cannot contain numbers. ");
		}
        if (!(this.imageUrl.endsWith(".png") || this.imageUrl.endsWith(".jpg"))) {
			errors.add("ImageUrl should end with .png or .jpg. ");
		}
        try {
	        new URL(this.imageUrl);
	    } catch (MalformedURLException e) {
	        errors.add("ImageUrl should be a valid url. ");
	    }
        return errors;
	}
	
	private boolean containsNumbers(String text) {
		return text.matches(".*\\d+.*");
	}
}


