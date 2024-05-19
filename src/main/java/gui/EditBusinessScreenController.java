package gui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import domain.AccountController;
import domain.Business;
import domain.BusinessController;
import domain.Client;
import domain.PaymentMethod;
import domain.Supplier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EditBusinessScreenController extends VBox {
	
    @FXML
    private Button cancelButton;	
    
	@FXML
	private ImageView imageView;

	@FXML
	private Text businessNameText;

	@FXML
	private TextField nameText;

	@FXML
	private TextField industryText;

	@FXML
	private Text paymentMethodsText;

	@FXML
	private TextField vatNumberText;

	@FXML
	private TextField emailText;

	@FXML
	private TextField phoneText;

	@FXML
	private TextField streetText;

	@FXML
	private TextField streetNrText;

	@FXML
	private TextField cityText;

	@FXML
	private TextField zipcodeText;

	@FXML
	private TextField countryText;

	@FXML
	private Button buttonEditOrSubmit;

	@FXML
	private Button closeButton;

	@FXML
	private Button deactiveButton;

    @FXML
    private Label errorLabel;

	private GuiController gc;
	private AccountController ac;
	private BusinessController bc;
	private MainScreen mainScreen;
	private BusinessesScreenController bs;
	private Business b;
	private long id;

	public EditBusinessScreenController(GuiController gc, long id, MainScreen mainScreen,
			BusinessesScreenController bs) {
		this.gc = gc;
		this.ac = gc.getAc();
		this.bc = gc.getBc();
		this.mainScreen = mainScreen;
		this.bs = bs;
		this.id = id;

		FXMLLoader loader = new FXMLLoader(getClass().getResource("EditBusinessScreen.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
			fillInformationTable(id);
			textFields(true, "EDIT");
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void fillInformationTable(long id) throws IOException {
		b = bc.getBusinessById(id);

		URL url = new URL(b.getImageUrl());
		InputStream inputStream = url.openStream();
		Image image = new Image(inputStream);
		imageView.setImage(image);
		inputStream.close();
		businessNameText.setText(b.getNameBusiness());
		nameText.setText(b.getNameBusiness());
		industryText.setText(b.getSector());
		String paymentMethods = b.getSupplier().getPaymentMethods().stream().map(PaymentMethod::getMethod)
				.collect(Collectors.joining(", "));
		if(paymentMethods.isBlank())
			paymentMethods = "/";
		paymentMethodsText.setText(paymentMethods);
		vatNumberText.setText(b.getVatNumber());
		emailText.setText(b.getClient().getEmail());
		phoneText.setText(b.getClient().getPhoneNumber());
		streetText.setText(b.getStreet());
		streetNrText.setText(b.getStreetNr());
		cityText.setText(b.getCity());
		zipcodeText.setText(b.getZipcode());
		countryText.setText(b.getCountry());
	}

	private void textFields(boolean enable, String text) {
		cancelButton.setDisable(enable);
		
		businessNameText.setDisable(enable);
		nameText.setDisable(enable);
		industryText.setDisable(enable);
		paymentMethodsText.setDisable(enable);
		vatNumberText.setDisable(enable);
		emailText.setDisable(enable);
		phoneText.setDisable(enable);
		streetText.setDisable(enable);
		streetNrText.setDisable(enable);
		cityText.setDisable(enable);
		zipcodeText.setDisable(enable);
		countryText.setDisable(enable);

		buttonEditOrSubmit.setText(text);
	}

	@FXML
	void onCloseButtonClicked(ActionEvent event) {
		mainScreen.setRightNull();
	}
	
	@FXML
    void onCancelClicked(ActionEvent event) throws IOException {
		fillInformationTable(id);
    	textFields(true, "EDIT");
    }

	@FXML
	void onDeactiveButtonClicked(ActionEvent event) {
		Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
		confirmationAlert.setTitle("Confirmation");
		confirmationAlert.setHeaderText("Deactivate Confirmation");
		confirmationAlert.setContentText("Are you sure you want to deactivate?");

		ButtonType deleteButtonType = new ButtonType("Deactivate");
		ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		confirmationAlert.getButtonTypes().setAll(deleteButtonType, cancelButtonType);

		confirmationAlert.showAndWait().ifPresent(result -> {
			if (result == deleteButtonType) {
				Supplier supplier = b.getSupplier();
				supplier.setEnabled(false);
				ac.updateSupplier(supplier);

				Client client = b.getClient();
				client.setEnabled(false);
				ac.updateClient(client);
			}
		});
		bs.update();
	}

	@FXML
	void onEditOrSubmitClicked(ActionEvent event) {
		errorLabel.setText("");
		if (buttonEditOrSubmit.getText() == "EDIT") {
			textFields(false, "SAVE");
		} else if (buttonEditOrSubmit.getText() == "SAVE") {
			Client cEdit = Client.builder().id(b.getClient().getId()).email(emailText.getText()).password(b.getClient().getPassword())
					.firstname(b.getClient().getFirstname()).lastname(b.getClient().getLastname()).phoneNumber(phoneText.getText())
					.enabled(b.getClient().isEnabled()).business(b).orders(b.getClient().getOrders()).build();
			List<String> validationClient = cEdit.validate();
	
			Business bEdit = Business.builder().id(b.getId()).imageUrl(b.getImageUrl()).nameBusiness(nameText.getText()).street(streetText.getText())
							.vatNumber(vatNumberText.getText()).country(countryText.getText()).zipcode(zipcodeText.getText()).streetNr(streetNrText.getText())
							.city(cityText.getText()).sector(industryText.getText()).supplier(b.getSupplier()).client(cEdit).build();
			List<String> validationBusiness = bEdit.validate();
			
			if (validationBusiness.size() == 0 && validationClient.size() == 0) {
				ac.updateBusiness(bEdit);
				ac.updateClient(cEdit);
				mainScreen.setRightNull();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText("Edit succesfull!");
				alert.setContentText(
						String.format("You have succesfully editted %s's information", b.getNameBusiness()));
				alert.show();
				bs.update();
			} else {
				if (validationClient.size() != 0) {
					errorLabel.setText("\n" + errorLabel.getText() + String.join("\n",	validationClient));
				}
				if (validationBusiness.size() != 0) {
					errorLabel.setText("\n" + errorLabel.getText() + String.join("\n",	validationBusiness));
				}
			} 
		}		
	}
}
