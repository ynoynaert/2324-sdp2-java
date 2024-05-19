package domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;


@ExtendWith(MockitoExtension.class)
public class SubOrderTest {


    //Testen van state... maar eigenlijk al een hele PaymentStateTest geschreven dus lijkt mij dit nu eigenlijk een beetje dom...
    // Mogelijks gewoon weglaten dan? Niet verder zitten testen op orderstate omdat je dan eigenlijk  gewoon weer states zit te testen
    @Mock
    private PaymentStateFactory paymentFactory;


    @Mock
    private OrderStateFactory orderStateFactory;

    @InjectMocks
    private SubOrder subOrder;

    @BeforeEach
    void setUp() {
        subOrder = new SubOrder(paymentFactory, orderStateFactory);
    }

    @Test
    void if_paid_then_return_paid() {

        subOrder.toPaymentState(new PaidState(subOrder));
        assertInstanceOf(PaidState.class, subOrder.getCurrentPState());
    }

    @Test
    void if_unpaid_then_return_unpaid() {


        subOrder.toPaymentState(new UnpaidState(subOrder));
        assertInstanceOf(UnpaidState.class, subOrder.getCurrentPState());

    }


}