package domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "OrderItem.findItemsBySuborderId", query = "SELECT p.name, oi.quantity, p.productAvailability, oi.netAmount, oi.quantity * oi.netAmount AS total FROM Product p JOIN OrderItem oi ON oi.product.id = p.id WHERE p.id IN (SELECT oi.product.id FROM OrderItem oi WHERE oi.subOrder.id = :suborderId) GROUP BY p.name"),
        @NamedQuery(name = "OrderItem.findInformationById", query =
                "SELECT b.nameBusiness, c.phoneNumber, c.email, s.uuid, s.createdAt, o.shippingAddressCity, o.shippingAddressCountry, o.shippingAddressStreet, o.shippingAddressStreetNr, o.shippingAddressZipcode, s.orderStateId, s.paymentReminder " +
                        "FROM SubOrder s " +
                        "JOIN s.order o  " +
                        "JOIN Account a ON o.client.business.id = a.id " +
                        "JOIN Business b ON b.id = a.id " +
                        "JOIN b.client c " +
                        "JOIN s.orderItems oi " +
                        "WHERE s.id = :id GROUP BY b.nameBusiness, c.phoneNumber, c.email, s.uuid, s.createdAt, o.shippingAddressCity, o.shippingAddressCountry, o.shippingAddressStreet, o.shippingAddressStreetNr, o.shippingAddressZipcode, s.orderStateId, s.paymentStateId"
        ),
})
@Table(name = "order_items")
@Getter
public class OrderItem implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "net_amount", nullable = false)
    private double netAmount;

    @ManyToOne
    @JoinColumn(name = "suborder_id")
    private SubOrder subOrder;

    public double getItemPriceVAT() {
    	return netAmount * 0.21;
    }
}
