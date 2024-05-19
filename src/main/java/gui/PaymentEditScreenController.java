package gui;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import domain.PaymentMethod;
import domain.PaymentMethodController;
import domain.Supplier;
import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class PaymentEditScreenController extends VBox{
  
	@FXML
    private VBox paymentMethodsVBox;

    @FXML
    private Button buttonSave;


    private PaymentMethodController pc;
    private GuiController gc;
    private AccountScreenController asc;
    private User user;
	private MainScreen mainScreen;
	private List<PaymentMethod> paymentMethods;
	private List<CheckBox> checkBoxes;
    
    public PaymentEditScreenController(GuiController gc, AccountScreenController asc,MainScreen mainScreen) {
    	this.gc = gc;
    	this.asc = asc;
    	this.pc= gc.getPc();
    	this.mainScreen=mainScreen;
    	this.user = User.getInstance();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("PaymentEditScreen.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		try {
			loader.load();
			init();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
    }
    
    public void init() {
    	
    	paymentMethods = pc.getAllPaymentMethods();
    	checkBoxes = new ArrayList<CheckBox>();

    	for(PaymentMethod p : paymentMethods) {
    		CheckBox checkBox = new CheckBox(p.getMethod()); 
    		checkBoxes.add(checkBox);
    		if(((Supplier) user.getAccount()).getPaymentMethods().stream().map(PaymentMethod::getMethod).anyMatch(m->m.contains(p.getMethod()))) {
    			checkBox.setSelected(true);
    		}
    	    paymentMethodsVBox.getChildren().add(checkBox);
    	}
    	
    }
    
    @FXML
    void onSaveClicked(ActionEvent event) throws IOException {
    	
    	List<PaymentMethod> newPaymentmethods = new ArrayList<>();
    	
    	((Supplier) user.getAccount()).getPaymentMethods().clear();
    	
    	for(CheckBox c: checkBoxes) {
    		if(c.isSelected()) {
        		newPaymentmethods.add(pc.getPaymentMethodByName(c.getText()));
        	}	
    	}
    	
    	((Supplier) user.getAccount()).getPaymentMethods().addAll(newPaymentmethods);
    	
    	gc.getAc().updateSupplier((Supplier) user.getAccount());
		asc.fillInfo();
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Edit succesfull!");
		alert.setContentText(String.format("You have succesfully editted your payment methods"));
		alert.show();
		
    }
    
    
}
