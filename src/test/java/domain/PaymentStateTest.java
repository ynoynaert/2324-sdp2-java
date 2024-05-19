package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentStateTest {
    SubOrder subOrder;
    PaymentState paymentState;

    @Test
    public void valid_set_payment_from_unpaid_to_paid() {
        paymentState = new UnpaidState(subOrder);
        paymentState.pay();
        assertTrue(subOrder.getCurrentPState() instanceof PaidState);
    }



    @Test
    public void invalid_set_payment_from_paid_to_paid() {
        paymentState = new PaidState(subOrder);
        assertThrows(UnsupportedOperationException.class, () -> {
            paymentState.pay();
        });
    }

    @BeforeEach
    void setUp() {
        subOrder = new SubOrder();
    }
}
