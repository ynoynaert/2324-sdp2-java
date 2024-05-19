package domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderStateTest {
    SubOrder subOrder;
    OrderState orderState;

    @Test
    public void valid_set_order_from_open_to_in_progress() {
        orderState = new OpenState(subOrder);
        orderState.setInProgress();
        assertTrue(subOrder.getCurrentOState() instanceof InProgressState);

    }


    @Test
    public void valid_set_order_from_in_progress_to_finished() {
        orderState = new InProgressState(subOrder);
        orderState.setFinished();;
        assertTrue(subOrder.getCurrentOState() instanceof FinishedState);

    }

    @Test
    public void invalid_set_order_from_open_to_open() {
        orderState = new OpenState(subOrder);
        assertThrows(UnsupportedOperationException.class, () -> {
            orderState.setOpen();
        });

    }


    @Test
    public void invalid_set_order_from_inprg_to_inprg() {
        orderState = new InProgressState(subOrder);
        assertThrows(UnsupportedOperationException.class, () -> {
            orderState.setInProgress();
        });

    }

    @Test
    public void invalid_set_order_from_open_to_finished() {
        orderState = new OpenState(subOrder);
        assertThrows(UnsupportedOperationException.class, () -> {
            orderState.setFinished();
        });
    }

    @Test
    public void invalid_set_order_from_finished_to_open() {
        orderState = new OpenState(subOrder);
        assertThrows(UnsupportedOperationException.class, () -> {
            orderState.setFinished();
        });
    }

    @Test
    public void invalid_set_order_from_finished_to_finished() {
        orderState = new FinishedState(subOrder);
        assertThrows(UnsupportedOperationException.class, () -> {
            orderState.setFinished();
        });
    }

    @Test
    public void invalid_set_order_from_in_progress_to_open() {
        orderState = new InProgressState(subOrder);
        assertThrows(UnsupportedOperationException.class, () -> {
            orderState.setOpen();
        });
    }

    @Test
    public void invalid_set_order_from_finished_to_in_progress() {
        orderState = new FinishedState(subOrder);
        assertThrows(UnsupportedOperationException.class, () -> {
            orderState.setInProgress();
        });
    }

    @BeforeEach
    void setUp() {
        subOrder = new SubOrder();
    }
}