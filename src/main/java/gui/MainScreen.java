package gui;

import domain.User;
import javafx.scene.layout.BorderPane;

public class MainScreen extends BorderPane {

    private GuiController gc;
    private User user;

    public MainScreen(GuiController gc) {
        this.gc = gc;
        this.user = User.getInstance();
        
        if (!user.isAdmin()) {
            NavBarSupplier ns = new NavBarSupplier(this, gc);
            this.setLeft(ns);

            OrdersScreenController oc = new OrdersScreenController(gc, this);
            setOrders(oc);
        } else if (user.isAdmin()) {
            NavBarAdmin na = new NavBarAdmin(this, gc);
            this.setLeft(na);
            
            BusinessesScreenController bc = new BusinessesScreenController(gc, this);
            setBusinessCenter(bc);
            
        }
    }

    public void setBusinessCenter(BusinessesScreenController screen) {
    	this.setRight(null);
    	this.setCenter(screen);
    }
    
    public void setOrderDetailRight(OrderDetailScreenController screen) {
        this.setRight(screen);
    }

    public void setOrders(OrdersScreenController screen) {
        this.setRight(null);
        this.setCenter(screen);
    }

    public void setClients(ClientsScreenController screen) {
        this.setRight(null);
        this.setCenter(screen);
    }

    public void setClientDetailCenter(ClientDetailScreenController screen) {
        this.setRight(screen);
    }

    public void setEditBusinessRight(EditBusinessScreenController ebsc) {
        this.setRight(ebsc);
    }

    public void setAccount(AccountScreenController screen, PaymentEditScreenController pesc) {
        this.setCenter(screen);
        this.setRight(pesc);
    }
    
    public void setAddBusiness(AddBusinessScreenController screen) {
    	this.setRight(screen);
    }
    
    public void setRightNull() {
    	this.setRight(null);
    }
}
