package domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PaymentState {

    protected final SubOrder subOrder;

    public PaymentState(SubOrder subOrder) {
        this.subOrder=subOrder;
    }

    public abstract int getId() ;

    public abstract String getName() ;

    public boolean pay() {
        throw new UnsupportedOperationException();
    }

}
