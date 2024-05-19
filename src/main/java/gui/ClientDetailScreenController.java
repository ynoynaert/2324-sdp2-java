package gui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.UnexpectedException;
import java.util.Set;

import domain.AccountController;
import domain.Client;
import domain.SubOrder;
import domain.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ClientDetailScreenController extends VBox {
	
	private GuiController gc;	

    @FXML
    private Button closeButton;

    @FXML
    private TableView<SubOrder> itemTable;

    @FXML
    private TableColumn<SubOrder, String> orderIdColom;

    @FXML
    private TableColumn<SubOrder, String> dateColumn;

    @FXML
    private TableColumn<SubOrder, String> priceColumn;

    @FXML
    private TableColumn<SubOrder, String> orderStateColumn;

    @FXML
    private TableColumn<SubOrder, String> paymentStateColumn;

    @FXML
    private ImageView imgView;

    @FXML
    private Text nameText;

    @FXML
    private Text contactText;

    @FXML
    private Text addressText;

    private AccountController ac;
    private MainScreen mainScreen;
    private User u;
    
	public ClientDetailScreenController(GuiController gc, Long id, MainScreen mainScreen) {
		this.gc = gc;	
		this.ac = gc.getAc();
		this.mainScreen = mainScreen;
		this.u = User.getInstance();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientDetailScreen.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		try {
			loader.load();
			fillItemTable(id);
			fillInformationTable(id);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private void fillInformationTable(Long id) throws IOException {
		Client c = ac.getClientById(id);
		
		URL url = new URL(c.getBusiness().getImageUrl());
        InputStream inputStream = url.openStream();
        Image image = new Image(inputStream);
		imgView.setImage(image);
		inputStream.close();
		nameText.setText(String.format("%s %s", c.getFirstname(),c.getLastname()));
		contactText.setText(String.format("%s%n%s", c.getEmail(),c.getPhoneNumber()));
		addressText.setText(String.format("%s %s%n%s %s %s", c.getBusiness().getStreet(),c.getBusiness().getStreetNr(),c.getBusiness().getCity(),c.getBusiness().getZipcode(),c.getBusiness().getCountry()));
	}
	
	private void fillItemTable(Long id) throws UnexpectedException {
		 Set<SubOrder> subOrder = gc.getSc().getSubOrdersByClient(u.getAccount().getId(), id);
		 
		 ObservableList<SubOrder> observableList = FXCollections.observableArrayList((subOrder));
	
		 itemTable.setItems(observableList);
		 orderIdColom.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getUuid())));
		 dateColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getOrder().getDate())));
		 priceColumn.setCellValueFactory(data -> new SimpleStringProperty(String.format("€ %.2f (+ € %.2f btw)",gc.getSc().getTotalPrice(data.getValue().getUuid())-(gc.getSc().getTotalPrice(data.getValue().getUuid()) * 0.21),gc.getSc().getTotalPrice(data.getValue().getUuid()) * 0.21)));
		 orderStateColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCurrentOState().getName())));
		 paymentStateColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCurrentPState().getName())));
		 
		 orderIdColom.setReorderable(false);
		 dateColumn.setReorderable(false);
		 priceColumn.setReorderable(false);
		 orderStateColumn.setReorderable(false);
		 paymentStateColumn.setReorderable(false);
	}
	
    @FXML
    void onCloseButtonClicked(ActionEvent event) {
    	mainScreen.setRightNull();
    }
}
