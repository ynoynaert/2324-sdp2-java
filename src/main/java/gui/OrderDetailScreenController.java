package gui;

import java.io.IOException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.rmi.UnexpectedException;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableColumn;
import domain.NotificationController;
import domain.Order;
import domain.OrderController;
import domain.OrderState;
import domain.PaymentState;
import domain.SubOrder;
import domain.SubOrderController;
import domain.Supplier;
import domain.User;
import dtos.SubOrderInformationDTO;
import dtos.SubOrderItemsDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class OrderDetailScreenController extends VBox {

    private final OrdersScreenController orderScreen;
    @FXML
    private Button closeButton;
    @FXML
    private Button updateOrderStatusBtn;
    @FXML
    private Button updatePaymentStatusBtn;
    @FXML
    private Text dateText;
    @FXML
    private Text paymentText;
    @FXML
    private Text clientInfoText;
    @FXML
    private Text shippingAddressText;
    @FXML
    private Text paidText;
    @FXML
    private Text statusText;
    @FXML
    private Text totalText;
    @FXML
    private Button paymentReminderBtn;
    @FXML
    private TableView<SubOrderItemsDTO> itemTable;
    @FXML
    private TableColumn<SubOrderItemsDTO, ImageView> availabilityColumn;
    @FXML
    private TableColumn<SubOrderItemsDTO, String> nameColumn;
    @FXML
    private TableColumn<SubOrderItemsDTO, String> pricePerUnitColumn;
    @FXML
    private TableColumn<SubOrderItemsDTO, String> amountColumn;
    @FXML
    private TableColumn<SubOrderItemsDTO, String> totalColumn;
    @FXML
    private TableColumn<SubOrderItemsDTO, String> totalVatColumn;
    
    private GuiController gc;
    private SubOrderController sc;
    private NotificationController nc;
    private OrderController oc;
    private String uuid;
    private MainScreen mainScreen;
    private Supplier s;

    public OrderDetailScreenController(GuiController gc, String uuid, OrdersScreenController orderScreen, MainScreen mainScreen) {
        this.orderScreen = orderScreen;
        this.gc = gc;
        this.sc = gc.getSc();
        this.nc = gc.getNc();
        this.oc = gc.getOc();
        this.uuid = uuid;
        this.mainScreen = mainScreen;
        s = (Supplier) User.getInstance().getAccount();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderDetailScreen.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            fillItemTable(uuid);
            fillInformationTable(uuid);
            checkOrderStatus();
            checkPaymentStatus();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void fillInformationTable(String uuid) throws UnexpectedException {
        SubOrder subOrder = sc.getSubOrder(uuid);
        SubOrderInformationDTO dto = sc.getSubOrderInformation(subOrder.getId());

        dateText.setText(String.valueOf(dto.suborderCreatedAt));
        paymentText.setText(String.valueOf(dto.paymentReminder));
        clientInfoText.setText(String.format("%s%n%s%n%s", dto.businessName, dto.email, dto.phoneNumber));
        shippingAddressText.setText(String.format("%s %s%n%s %s%n%s", dto.shippingAddressStreet, dto.shippingAddressStreetNr, dto.shippingAddressZipcode, dto.shippingAddressCity, dto.shippingAddressCountry));
        paidText.setText(String.valueOf(subOrder.getCurrentPState().getName()));
        statusText.setText(String.valueOf(subOrder.getCurrentOState().getName()));
        paymentReminderBtn.setVisible(subOrder.getCurrentPState().getName() == "Unpaid");
        totalText.setText(String.format("€ %.2f", sc.getTotalPrice(uuid)));
    }

    private void fillItemTable(String uuid) throws UnexpectedException {
        SubOrder subOrder = sc.getSubOrder(uuid);
        List<SubOrderItemsDTO> dto = sc.getItemsByOrderId(subOrder.getOrder().getId());

        ObservableList<SubOrderItemsDTO> observableItems = FXCollections.observableArrayList(dto);

        itemTable.setItems(observableItems);

        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().productName));
        amountColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().quantity)));
        pricePerUnitColumn.setCellValueFactory(data -> new SimpleStringProperty(String.format("€ %.2f",data.getValue().netAmount)));
        totalColumn.setCellValueFactory(data -> new SimpleStringProperty(String.format("€ %.2f", data.getValue().total)));
        totalVatColumn.setCellValueFactory(data -> new SimpleStringProperty(String.format("€ %.2f", data.getValue().total * 0.21)));
        availabilityColumn.setCellValueFactory(data -> {
            ImageView imageView = new ImageView();
            if (data.getValue().productAvailability) {
                imageView.setImage(new Image(getClass().getResourceAsStream("/images/check.png")));
                imageView.setFitHeight(15);
                imageView.setFitWidth(15);
                return new SimpleObjectProperty<>(imageView);
            } else {
            	return null;
            }
        });
        nameColumn.setReorderable(false);
        amountColumn.setReorderable(false);
        pricePerUnitColumn.setReorderable(false);
        totalColumn.setReorderable(false);
        availabilityColumn.setReorderable(false);
    }

    private void checkOrderStatus() {
        SubOrder subOrder = sc.getSubOrder(uuid);
        OrderState os = subOrder.getCurrentOState();
        if (os.toString().contains("Finished")) {
            updateOrderStatusBtn.setDisable(true);
        }
    }

    private void checkPaymentStatus() {
        SubOrder subOrder = sc.getSubOrder(uuid);
        PaymentState ps = subOrder.getCurrentPState();
        if (ps.toString().contains("Paid")) {
            updatePaymentStatusBtn.setDisable(true);
        }
    }

    @FXML
    void OnUpdateOrderStatusClicked(ActionEvent event) throws UnexpectedException {
        SubOrder subOrder = sc.getSubOrder(uuid);
        OrderState os = subOrder.getCurrentOState();

        if (os.toString().contains("Open")) {
            subOrder.getCurrentOState().setInProgress();
        } else if (os.toString().contains("InProgress"))
            subOrder.getCurrentOState().setFinished();
        
        sc.updateSubOrder(new SubOrder(subOrder.getId(), subOrder.getUuid(), subOrder.getSupplierId(),
        		subOrder.getCreatedAt(), subOrder.getPaymentStateId(), subOrder.getCurrentOState().getId(), subOrder.getPaymentReminder(), subOrder.getOrder(), subOrder.getOrderItems()));
        oc.updateOrderPaymentStatus(new Order(subOrder.getOrder().getId(), subOrder.getOrder().getUuid(), subOrder.getOrder().getBillingAddressStreet(),
        		subOrder.getOrder().getBillingAddressZipcode(), subOrder.getOrder().getBillingAddressCountry(), subOrder.getOrder().getBillingAddressStreetNr(), 
        		subOrder.getOrder().getBillingAddressCity(), subOrder.getOrder().getShippingAddressStreet(), subOrder.getOrder().getShippingAddressZipcode(), 
        		subOrder.getOrder().getShippingAddressCountry(), subOrder.getOrder().getShippingAddressStreetNr(), subOrder.getOrder().getShippingAddressCity(),
        		subOrder.getOrder().getRemark(), subOrder.getOrder().getPaymentPeriod(), subOrder.getOrder().getDate(), subOrder.getOrder().getSubOrders(), 
        		subOrder.getOrder().getClient()));

        try {
            fillInformationTable(uuid);
            fillItemTable(uuid);
            checkOrderStatus();
        } catch (UnexpectedException ex) {
            ex.printStackTrace();
        }

        orderScreen.update();

    }

    @FXML
    void OnUpdatePaymentStatusClicked(ActionEvent event) throws UnexpectedException {
        SubOrder subOrder = sc.getSubOrder(uuid);
        PaymentState ps = subOrder.getCurrentPState();

        if (ps.toString().contains("Unpaid")) {
            subOrder.getCurrentPState().pay();
        }
        
        sc.updateSubOrder(new SubOrder(subOrder.getId(), subOrder.getUuid(), subOrder.getSupplierId(),
        		subOrder.getCreatedAt(), subOrder.getCurrentPState().getId(), subOrder.getOrderStateId(), subOrder.getPaymentReminder(), subOrder.getOrder(), subOrder.getOrderItems()));
        oc.updateOrderPaymentStatus(new Order(subOrder.getOrder().getId(), subOrder.getOrder().getUuid(), subOrder.getOrder().getBillingAddressStreet(),
        		subOrder.getOrder().getBillingAddressZipcode(), subOrder.getOrder().getBillingAddressCountry(), subOrder.getOrder().getBillingAddressStreetNr(), 
        		subOrder.getOrder().getBillingAddressCity(), subOrder.getOrder().getShippingAddressStreet(), subOrder.getOrder().getShippingAddressZipcode(), 
        		subOrder.getOrder().getShippingAddressCountry(), subOrder.getOrder().getShippingAddressStreetNr(), subOrder.getOrder().getShippingAddressCity(),
        		subOrder.getOrder().getRemark(), subOrder.getOrder().getPaymentPeriod(), subOrder.getOrder().getDate(), subOrder.getOrder().getSubOrders(), 
        		subOrder.getOrder().getClient()));

        try {
            fillInformationTable(uuid);
            fillItemTable(uuid);
            checkPaymentStatus();
        } catch (UnexpectedException ex) {
            ex.printStackTrace();
        }

        orderScreen.update();
    }


    @FXML
    void onCloseButtonClicked(ActionEvent event) {
        mainScreen.setRightNull();
    }

    @FXML
    void sendPaymentReminder(ActionEvent event) {
    	SubOrder subOrder = sc.getSubOrder(uuid);
    	nc.sendPaymentReminder(uuid, s);
    	sc.updateSubOrder(new SubOrder(subOrder.getId(), subOrder.getUuid(), subOrder.getSupplierId(), 
    			subOrder.getCreatedAt(), subOrder.getPaymentStateId(), subOrder.getOrderStateId(), LocalDate.now(), 
    			subOrder.getOrder(), subOrder.getOrderItems()));
    	
        mainScreen.setOrderDetailRight(new OrderDetailScreenController(gc, uuid, orderScreen, mainScreen));
    	Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Notification sent!");
        alert.setContentText(String.format("You have sent a payment reminder for this order!"));
        alert.show();
    }
}
