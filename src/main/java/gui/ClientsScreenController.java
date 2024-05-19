package gui;

import java.io.IOException;
import java.util.function.Predicate;

import domain.AccountController;
import domain.Client;
import domain.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ClientsScreenController extends VBox {
    @FXML
    private TextField filterBusinessName;

	@FXML
	private TextField filterContactName;

    @FXML
    private TableView<Client> clientsTable;

    @FXML
    private TableColumn<Client, String> businessColumn;

    @FXML
    private TableColumn<Client, String> contactColumn;

    @FXML
    private TableColumn<Client, String> emailColumn;

    @FXML
    private TableColumn<Client, String> phoneColumn;

    private GuiController gc;
    private AccountController ac;
    private MainScreen mainscreen;
    private ObservableList<Client> clientsData;
    private FilteredList<Client> filteredClients;
    private SortedList<Client> sortedClients;
    private User u = User.getInstance();


    public ClientsScreenController(GuiController gc, MainScreen mainscreen) {
        this.gc = gc;
        this.ac = gc.getAc();
        this.mainscreen = mainscreen;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientsScreen.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            populateClientsTable();
            clickOnRow();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    } 

    private void populateClientsTable() {    	
    	clientsData = ac.getClientsBySupplierId(u.getAccount().getId());
    	
    	filteredClients = new FilteredList<>(clientsData, p -> true);
    	filterBusinessName.textProperty().addListener((observable, oldValue, newValue) -> updateFilteredSubOrders());
    	filterContactName.textProperty().addListener((observable, oldValue, newValue) -> updateFilteredSubOrders());
    	
        sortedClients = new SortedList<>(filteredClients);
        sortedClients.comparatorProperty().bind(clientsTable.comparatorProperty());
        
        clientsTable.setItems(sortedClients);
        clientsTable.setPlaceholder(new Label("There are no clients"));
        populateTable(filteredClients);
    }

    private void populateTable(FilteredList<Client> filteredClients) {
        businessColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBusiness().getNameBusiness()));
        contactColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFirstname() + " " + data.getValue().getLastname()));
        emailColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        phoneColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhoneNumber()));
        
        businessColumn.setReorderable(false);
        contactColumn.setReorderable(false);
        emailColumn.setReorderable(false);
        phoneColumn.setReorderable(false);
    }
    
    private void updateFilteredSubOrders() {
    	Predicate<Client> combinedPredicate = client -> true;
    	
    	combinedPredicate = combinedPredicate.and(client -> {
    		if (filterBusinessName.getText() == null || filterBusinessName.getText().isEmpty()) {
                return true;
            }
            return client.getBusiness().getNameBusiness().toLowerCase().contains(filterBusinessName.getText().toLowerCase());
        });
    	
    	combinedPredicate = combinedPredicate.and(client -> {
    		if (filterContactName.getText() == null || filterContactName.getText().isEmpty()) {
                return true;
            }
    		String clientfullname = String.format("%s %s", client.getFirstname(), client.getLastname());
            return clientfullname.toLowerCase().contains(filterContactName.getText().toLowerCase());
        });
    	
    	filteredClients.setPredicate(combinedPredicate);
    }

    private void clickOnRow() {
        clientsTable.setOnMouseClicked(event -> {
            if (clientsTable.getSelectionModel().getSelectedItem() != null) {
                Long id = clientsTable.getSelectionModel().getSelectedItem().getId();
                ClientDetailScreenController cdsc = new ClientDetailScreenController(gc, id, mainscreen);
                mainscreen.setClientDetailCenter(cdsc);
            }
        });
    }
}
