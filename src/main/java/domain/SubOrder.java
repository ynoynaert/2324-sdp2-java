package domain;

import java.io.Serializable;
import java.rmi.UnexpectedException;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;

@Entity
@NamedQueries({
        @NamedQuery(name = "SubOrder.findBySupplier", query = "SELECT s FROM SubOrder s WHERE s.supplierId  = :supplierId"),
        @NamedQuery(name = "SubOrder.findByUuid", query = "SELECT s FROM SubOrder s WHERE s.uuid like :uuid"),
        @NamedQuery(name = "SubOrder.findBySupplierAndClient",
                query = "SELECT s FROM SubOrder s "+
                        "WHERE s.supplierId  = :supplierId "+
                        "AND s.order.client.id = :clientId"

        ),
        @NamedQuery(name="SubOrder.findPriceByUuid", query = "SELECT SUM(oi.quantity *oi.netAmount) "
        										+ " FROM OrderItem oi "
        										+ "WHERE oi.subOrder.uuid like :uuid "
        										+ "GROUP BY oi.quantity * oi.netAmount")
})
@Table(name = "suborders")
@Getter
public class SubOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "supplier_id", nullable = false)
    private int supplierId;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "payment_status_id", nullable = false)
    private int paymentStateId;

    @Column(name = "order_status_id", nullable = false)
    private int orderStateId;
    
    @Column(name = "payment_reminder", nullable = false)
    private LocalDate paymentReminder;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    @OneToMany
    @JoinColumn(name = "id")
    private List<OrderItem> orderItems;

    @Transient
    private PaymentState currentPState;

    @Transient
    private OrderState currentOState;

    @Transient
    private PaymentStateFactory paymentFactory;

    @Transient
    private OrderStateFactory orderFactory;

    public SubOrder() {
        this(new PaymentStateFactory(),new OrderStateFactory());
    }

    public SubOrder(PaymentStateFactory paymentFactory,OrderStateFactory orderFactory) {
        this.paymentFactory=paymentFactory;
        this.orderFactory=orderFactory;
    }

    public SubOrder(int id, String uuid, int supplierId, LocalDate createdAt, int paymentStateId, int orderStateId,
			LocalDate paymentReminder, Order order, List<OrderItem> orderItems) {
		this.id = id;
		this.uuid = uuid;
		this.supplierId = supplierId;
		this.createdAt = createdAt;
		this.paymentStateId = paymentStateId;
		this.orderStateId = orderStateId;
		this.paymentReminder = paymentReminder;
		this.order = order;
		this.orderItems = orderItems;
	}
    
    @PostLoad
    private void initialize() throws UnexpectedException {
        toOrderState(orderFactory.createOrderState(this.orderStateId, this));
        toPaymentState(paymentFactory.createPaymentState(this.paymentStateId, this));
    }

    protected void toPaymentState(PaymentState state) {
        this.currentPState = state;
    }

    protected void toOrderState(OrderState state) {
        this.currentOState = state;
    }
}
