package gui;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import domain.AccountController;
import domain.Business;
import domain.BusinessController;
import domain.Client;
import domain.Order;
import domain.Supplier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import util.Hash;

public class AddBusinessScreenController extends VBox {
	
	@FXML
    private Button closeButton;

    @FXML
    private TextField nameText;

    @FXML
    private TextField industryText;

    @FXML
    private TextField vatNumberText;

    @FXML
    private TextField countryText;

    @FXML
    private TextField imageURLText;

    @FXML
    private TextField streetText;

    @FXML
    private TextField streetNrText;

    @FXML
    private TextField cityText;

    @FXML
    private TextField zipcodeText;

    @FXML
    private TextField supplierFirstNameText;

    @FXML
    private TextField supplierPhoneNumberText;

    @FXML
    private TextField supplierLastName;

    @FXML
    private TextField supplierEmailText;

    @FXML
    private TextField clientFirstNameText;

    @FXML
    private TextField clientPhoneNumberText;

    @FXML
    private TextField clientLastNameText;

    @FXML
    private TextField clientEmailText;

    @FXML
    private Button buttonAdd;
    
    @FXML
    private PasswordField supplierPasswordText;
    
    @FXML
    private PasswordField clientPasswordText;
    
    @FXML
    private Label errorLabel;

	private GuiController gc;
	private BusinessesScreenController bs;
	private BusinessController bc;
	private AccountController ac;
	private MainScreen mainScreen;
	
	public AddBusinessScreenController(GuiController gc, MainScreen mainScreen, BusinessesScreenController bs) {
		this.gc = gc;
		this.bs = bs;
		this.bc = gc.getBc();
		this.ac = gc.getAc();
		this.mainScreen = mainScreen;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBusinessScreen.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		try {
			loader.load();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

    @FXML
    void onCloseButtonClicked(ActionEvent event) {
    	mainScreen.setRightNull();
    }
    
    @FXML
    void onAddClicked(ActionEvent event) throws GeneralSecurityException {
    	errorLabel.setText("");
    	Business bAdd = Business.builder().imageUrl(imageURLText.getText()).nameBusiness(nameText.getText()).street(streetText.getText())
    			.vatNumber(vatNumberText.getText()).country(countryText.getText()).zipcode(zipcodeText.getText()).streetNr(streetNrText.getText())
    			.city(cityText.getText()).sector(industryText.getText()).supplier(null).client(null).build();
    	List<String> validationBusiness = bAdd.validate();
    	
    	Supplier sAdd = Supplier.builder().email(supplierEmailText.getText()).password(Hash.make(supplierPasswordText.getText()))
    			.firstname(supplierFirstNameText.getText()).lastname(supplierLastName.getText()).phoneNumber(supplierPhoneNumberText.getText())
    			.business(bAdd).enabled(true).build();
    	
    	List<String> validationSupplier = sAdd.validate();
    	
    	Client cAdd = Client.builder().email(clientEmailText.getText()).password(Hash.make(clientPasswordText.getText()))
				.firstname(clientFirstNameText.getText()).lastname(clientLastNameText.getText()).phoneNumber(clientPhoneNumberText.getText())
				.enabled(true).business(bAdd).orders(new ArrayList<Order>()).build();
		List<String> validationClient = cAdd.validate();
		
		bAdd.setSupplier(sAdd);
		bAdd.setClient(cAdd);
		
		if (validationBusiness.size() == 0 && validationClient.size() == 0 && validationSupplier.size() == 0) {
			ac.addClient(cAdd);
			ac.addSupplier(sAdd);
			bc.addBusiness(bAdd);
			mainScreen.setRightNull();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("Edit succesfull!");
			alert.setContentText(
					String.format("You have succesfully added a new business"));
			alert.show();
			bs.update();
		} else {
			if (validationClient.size() != 0) {
				errorLabel.setText("\n" + errorLabel.getText() + String.join("\n",	validationClient));
			}
			if (validationBusiness.size() != 0) {
				errorLabel.setText("\n" + errorLabel.getText() + String.join("\n",	validationBusiness));
			}
			if (validationSupplier.size() != 0) {
				errorLabel.setText("\n" + errorLabel.getText() + String.join("\n",	validationSupplier));
			}
		} 
    }
}
