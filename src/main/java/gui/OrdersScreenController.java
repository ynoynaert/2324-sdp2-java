package gui;

import java.io.IOException;
import java.rmi.UnexpectedException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import domain.NotificationController;
import domain.OrderController;
import domain.SubOrder;
import domain.SubOrderController;
import domain.Supplier;
import domain.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class OrdersScreenController extends VBox {

    @FXML
    private TableView<SubOrder> ordersTable;

    @FXML
    private TableColumn<SubOrder, String> idColumn;

    @FXML
    private TableColumn<SubOrder, String> dateColumn;

    @FXML
    private ComboBox<String> filterPaymentStatus;

    @FXML
    private TableColumn<SubOrder, String> statusColumn;

    @FXML
    private TableColumn<SubOrder, String> paymentColumn;

    @FXML
    private TableColumn<SubOrder, String> buyerColumn;

    @FXML
    private TextField filterOrderId;

    @FXML
    private DatePicker filterDateFrom;

    @FXML
    private DatePicker filterDateTo;

    @FXML
    private ComboBox<String> filterOrderStatus;

    @FXML
    private TextField filterNameBuyer;

    private GuiController gc;
    private NotificationController nc;
    private User u = User.getInstance();
    private SubOrderController sc;
    private OrderController oc;
    private MainScreen mainscreen;
    private ObservableList<SubOrder> subOrderData;
    private FilteredList<SubOrder> filteredSubOrders;
    private SortedList<SubOrder> sortedSubOrders;

    public OrdersScreenController(GuiController gc, MainScreen mainscreen) {
        this.gc = gc;
        this.nc = gc.getNc();
        this.sc = gc.getSc();
        this.oc = gc.getOc();
        this.mainscreen = mainscreen;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OrdersScreen.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            sendPaymentReminder();
            paymentUpdate();
            populateOrdersTable();
            clickOnRow();
            populateComboBoxes();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

	private void populateComboBoxes() {
        Map<Integer, String> orderStates = new HashMap<>();
        orderStates.put(4, "Finished");
        orderStates.put(3, "In Progress");
        orderStates.put(2, "Open");
        orderStates.put(1, "All");

        Map<Integer, String> paymentStates = new HashMap<>();
        paymentStates.put(3, "Paid");
        paymentStates.put(2, "Unpaid");
        paymentStates.put(1, "All");

        filterOrderStatus.setItems(FXCollections.observableArrayList(orderStates.values()));
        filterPaymentStatus.setItems(FXCollections.observableArrayList(paymentStates.values()));
    }

    private void populateOrdersTable() throws UnexpectedException {
        filteredSubOrders = new FilteredList<>(subOrderData, p -> true);
        filterNameBuyer.textProperty().addListener((observable, oldValue, newValue) -> updateFilteredSubOrders());
        filterOrderId.textProperty().addListener((observable, oldValue, newValue) -> updateFilteredSubOrders());
        filterDateFrom.valueProperty().addListener((observable, oldValue, newValue) -> updateFilteredSubOrders());
        filterDateTo.valueProperty().addListener((observable, oldValue, newValue) -> updateFilteredSubOrders());
        filterPaymentStatus.valueProperty().addListener((observable, oldValue, newValue) -> updateFilteredSubOrders());
        filterOrderStatus.valueProperty().addListener((observable, oldValue, newValue) -> updateFilteredSubOrders());
        
        sortedSubOrders = new SortedList<>(filteredSubOrders);
        sortedSubOrders.comparatorProperty().bind(ordersTable.comparatorProperty());
        
        ordersTable.setItems(sortedSubOrders);
        ordersTable.setPlaceholder(new Label("There are no orders"));
        populateTable(filteredSubOrders);
    }
    
    private void populateTable(FilteredList<SubOrder> filteredSubOrders) {
    	idColumn.setReorderable(false);
    	dateColumn.setReorderable(false);
    	statusColumn.setReorderable(false);
    	paymentColumn.setReorderable(false);
    	buyerColumn.setReorderable(false);
    	
        idColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUuid()));
        dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCreatedAt().toString()));
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCurrentOState().getName()));
        paymentColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCurrentPState().getName()));
        buyerColumn.setCellValueFactory(data -> new SimpleStringProperty(oc.getBuyerName(data.getValue().getOrder().getId())));
    }
    
    private void updateFilteredSubOrders() {
    	Predicate<SubOrder> combinedPredicate = suborder -> true;

        combinedPredicate = combinedPredicate.and(suborder -> {
            if (filterNameBuyer.getText() == null || filterNameBuyer.getText().isEmpty()) {
                return true;
            }
            return oc.getBuyerName(suborder.getId()).toLowerCase().contains(filterNameBuyer.getText().toLowerCase());
        });

        combinedPredicate = combinedPredicate.and(suborder -> {
            if (filterOrderId.getText() == null || filterOrderId.getText().isEmpty()) {
                return true;
            }
            return suborder.getUuid().toLowerCase().contains(filterOrderId.getText().toLowerCase());
        });

        LocalDate fromDate = filterDateFrom.getValue() != null ? filterDateFrom.getValue() : LocalDate.MIN;
        LocalDate toDate = filterDateTo.getValue() != null ? filterDateTo.getValue() : LocalDate.now();

        combinedPredicate = combinedPredicate.and(suborder -> {
            return suborder.getCreatedAt() != null && !suborder.getCreatedAt().isBefore(fromDate) && !suborder.getCreatedAt().isAfter(toDate);
        });

        combinedPredicate = combinedPredicate.and(suborder -> {
            if (filterPaymentStatus.getValue() == null || filterPaymentStatus.getValue().isEmpty() || filterPaymentStatus.getValue().equalsIgnoreCase("All")) {
                return true;
            }
            return suborder.getCurrentPState().getName().toLowerCase().equals(filterPaymentStatus.getValue().toLowerCase());
        });

        combinedPredicate = combinedPredicate.and(suborder -> {
            if (filterOrderStatus.getValue() == null || filterOrderStatus.getValue().isEmpty() || filterOrderStatus.getValue().equalsIgnoreCase("All")) {
                return true;
            }
            return suborder.getCurrentOState().getName().toLowerCase().equals(filterOrderStatus.getValue().toLowerCase());
        });

        filteredSubOrders.setPredicate(combinedPredicate);
    }

    private void clickOnRow() {
        ordersTable.setOnMouseClicked(event -> {
            if (ordersTable.getSelectionModel().getSelectedItem() != null) {
                String uuid = ordersTable.getSelectionModel().getSelectedItem().getUuid();

                OrderDetailScreenController odsc = new OrderDetailScreenController(gc, uuid, this, mainscreen);
                mainscreen.setOrderDetailRight(odsc);
            }
        });
    }

    public void update() throws UnexpectedException {
        populateOrdersTable();
        clickOnRow();
        populateComboBoxes();
        ordersTable.refresh();
    }
    
    private void paymentUpdate() {
    	for(SubOrder sub : subOrderData)
    		if(sub.getCurrentPState().getName() == "Unpaid")
    			sub.getCurrentPState().pay();
    }

    private void sendPaymentReminder() {
    	subOrderData = sc.getSubOrders(u.getAccount().getId());
    	for (SubOrder sub: subOrderData) {
    		int days = Integer.valueOf(sub.getOrder().getPaymentPeriod().replace(" days", ""));
    		LocalDate paymentDay = LocalDate.of(sub.getCreatedAt().getYear(), sub.getCreatedAt().getMonth(), sub.getCreatedAt().getDayOfMonth());
    		paymentDay.plusDays(days);
    		if (LocalDate.now().until(paymentDay).getDays() <= 3) {
    			nc.sendPaymentReminder(sub.getUuid(), (Supplier) u.getAccount());
    		}
    	}
	}
}
