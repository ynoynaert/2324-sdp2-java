package gui;

import domain.AccountController;
import domain.BusinessController;
import domain.LoginController;
import domain.NotificationController;
import domain.OrderController;
import domain.PaymentMethodController;
import domain.SubOrderController;

public class GuiController {

	private LoginController lc;
	private SubOrderController sc;
	private PaymentMethodController pc;
	private NotificationController nc;
	private BusinessController bc;
	private OrderController oc;
	
	public LoginController getLc() {
		return lc;
	}
	
	public SubOrderController getSc() {
		return sc;
	}
	
	public AccountController getAc() {
		return lc.getAc();
	}
	
	public PaymentMethodController getPc() {
		return pc;
	}

	public NotificationController getNc() {
		return nc;
	}
	
	public BusinessController getBc() {
		return bc;
	}
	
	public OrderController getOc() {
		return oc;
	}
	
	public GuiController() {
		this.lc = new LoginController();
		this.sc = new SubOrderController();
		this.pc = new PaymentMethodController();
		this.nc = new NotificationController();
		this.bc = new BusinessController();
		this.oc = new OrderController();
	}
	
	public double getItemPriceVAT() {
		return oc.getItemPriceVAT();
	}
}
