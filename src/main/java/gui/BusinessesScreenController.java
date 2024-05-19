package gui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import domain.AccountController;
import domain.Business;
import domain.BusinessController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BusinessesScreenController extends VBox {
	
	@FXML
    private TextField filterBusinessName;
	
    @FXML
    private TextField filterIndustry;

    @FXML
    private ComboBox<String> filterActive;

    @FXML
    private TableView<Business> businessesTable;

    @FXML
    private TableColumn<Business, String> businessColumn;

    @FXML
    private TableColumn<Business, String> industryColumn;

    @FXML
    private TableColumn<Business, String> addressColumn;

    @FXML
    private TableColumn<Business, String> clientAmountColumn;

    @FXML
    private TableColumn<Business, ImageView> activeColumn;

    @FXML
    private Button addBusinessButton;
    
    private GuiController gc;
    private AccountController ac;
    private BusinessController bc;
    private MainScreen mainscreen;
	
	private ObservableList<Business> businessesData;
	private FilteredList<Business> filteredBusinesses;
	private SortedList<Business> sortedBusinesses;

	public BusinessesScreenController(GuiController gc, MainScreen mainscreen) {
		this.gc = gc;
		this.ac = gc.getAc();
		this.bc = gc.getBc();
		this.mainscreen = mainscreen;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("BusinessesScreen.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		try {
			loader.load();
			populateBusinessesTable();
			clickOnRow();
			populateComboBox();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private void populateBusinessesTable() {
		businessesData = bc.getAllBusinesses();
		
		filteredBusinesses = new FilteredList<>(businessesData, p -> true);
		filterBusinessName.textProperty().addListener((observable, oldValue, newValue) -> updateFilteredBusinesses());
		filterIndustry.textProperty().addListener((observable, oldValue, newValue) -> updateFilteredBusinesses());
		filterActive.valueProperty().addListener((observable, oldValue, newValue) -> updateFilteredBusinesses());
		
		sortedBusinesses = new SortedList<>(filteredBusinesses);
		sortedBusinesses.comparatorProperty().bind(businessesTable.comparatorProperty());
	
		businessesTable.setItems(sortedBusinesses);
		businessesTable.setPlaceholder(new Label("There are no businesses"));
		populateTable(filteredBusinesses);
	}
	
	private void populateTable(FilteredList<Business> filteredBusinesses) {
		businessColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNameBusiness()));
		industryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSector()));
		addressColumn.setCellValueFactory(data -> new SimpleStringProperty(String.format("%s %s %s %s %s", data.getValue().getStreet(), data.getValue().getStreetNr(), data.getValue().getZipcode(), data.getValue().getCity(), data.getValue().getCountry())));
		clientAmountColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(ac.getClientAmount(data.getValue().getId()))));
		//activeColumn.setCellValueFactory(data -> new SimpleStringProperty(ac.getIsActive(data.getValue().getId())));
		activeColumn.setCellValueFactory(data -> {
            ImageView imageView = new ImageView();
            if (ac.getIsActive(data.getValue().getId())== "yes") {
                imageView.setImage(new Image(getClass().getResourceAsStream("/images/check.png")));
                imageView.setFitHeight(15);
                imageView.setFitWidth(15);
                return new SimpleObjectProperty<>(imageView);
            } else {
            	return null;
            }
        });

		businessColumn.setReorderable(false);
		industryColumn.setReorderable(false);
		addressColumn.setReorderable(false);
		clientAmountColumn.setReorderable(false);
		activeColumn.setReorderable(false);
	}
	
	private void populateComboBox() {
		Map<Integer, String> actives = new HashMap<>();
		actives.put(0, "No");
		actives.put(1, "Yes");
		actives.put(3, "All");

		filterActive.setItems(FXCollections.observableArrayList(actives.values()));
	}
	
	private void updateFilteredBusinesses() {
		Predicate<Business> combinedPredicate = business -> true;

	    combinedPredicate = combinedPredicate.and(business -> {
	        if (filterBusinessName.getText() == null || filterBusinessName.getText().isEmpty()) {
	            return true;
	        }
	        return business.getNameBusiness().toLowerCase().contains(filterBusinessName.getText().toLowerCase());
	    });
	    combinedPredicate = combinedPredicate.and(business -> {
	        if (filterIndustry.getText() == null || filterIndustry.getText().isEmpty()) {
	            return true;
	        }
	        return business.getSector().toLowerCase().contains(filterIndustry.getText().toLowerCase());
	    });
	    combinedPredicate = combinedPredicate.and(business -> {
	        if (filterActive.getValue() == null || filterActive.getValue().isEmpty() || filterActive.getValue().equalsIgnoreCase("All")) {
	            return true;
	        }
	        boolean isActive = filterActive.getValue().equalsIgnoreCase("Yes");
	        return (isActive && (business.getClient().isEnabled() || business.getSupplier().isEnabled()))
	            || (!isActive && (!business.getClient().isEnabled() && !business.getSupplier().isEnabled()));
	    });

	    filteredBusinesses.setPredicate(combinedPredicate);
	}
	
	private void clickOnRow() {
        businessesTable.setOnMouseClicked(event -> {
            if (businessesTable.getSelectionModel().getSelectedItem() != null) {
                long id = businessesTable.getSelectionModel().getSelectedItem().getId();

                EditBusinessScreenController ebsc = new EditBusinessScreenController(gc, id, mainscreen,this);               
                mainscreen.setEditBusinessRight(ebsc);
            }
        });
    }
	 
	 @FXML
	 void onAddBusinessClicked(ActionEvent event) {
		 AddBusinessScreenController absc = new AddBusinessScreenController(gc, mainscreen, this);               
         mainscreen.setAddBusiness(absc);
	 }
	 
	 public void update() {
		 clickOnRow();
		 populateBusinessesTable();
		 businessesTable.refresh();
	 }

}
