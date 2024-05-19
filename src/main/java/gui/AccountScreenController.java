package gui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.stream.Collectors;

import domain.PaymentMethod;
import domain.Supplier;
import domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class AccountScreenController extends VBox{
	@FXML
    private ImageView imageView;

    @FXML
    private Text businessNameText;

    @FXML
    private Text contactNameText;

    @FXML
    private Text industryText;

    @FXML
    private Text addressText;

    @FXML
    private Text paymentMethodsText;

    @FXML
    private Text contactInfoText;

    @FXML
    private Text vatNumberText;

    private GuiController gc;
	private MainScreen mainscreen;
	private User user;
	
	public AccountScreenController(GuiController gc, MainScreen mainscreen) {
		this.gc = gc;
		this.mainscreen = mainscreen;
		this.user = User.getInstance();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountScreen.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		try {
			loader.load();
			fillInfo();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void fillInfo() throws IOException {
		URL url = new URL(((Supplier) user.getAccount()).getBusiness().getImageUrl());
        InputStream inputStream = url.openStream();
        Image image = new Image(inputStream);		
		imageView.setImage(image);
		
		businessNameText.setText(((Supplier) user.getAccount()).getBusiness().getNameBusiness());
		contactNameText.setText(user.getName());
		industryText.setText(((Supplier) user.getAccount()).getBusiness().getSector());
		addressText.setText(user.getAddress());
		paymentMethodsText.setText(((Supplier) user.getAccount()).getPaymentMethods().stream().map(PaymentMethod::getMethod).collect(Collectors.joining(", ")));
		contactInfoText.setText(((Supplier) user.getAccount()).getEmail() + "\n" + ((Supplier) user.getAccount()).getPhoneNumber());
		vatNumberText.setText(((Supplier) user.getAccount()).getBusiness().getVatNumber());
	}
}
