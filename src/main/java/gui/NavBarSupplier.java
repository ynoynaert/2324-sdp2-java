package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NavBarSupplier extends VBox{
    @FXML
    private Button ordersButton;

    @FXML
    private Button clientButton;

    @FXML
    private Button accountButton;

    @FXML
    private Button logOutButton;
    
    @FXML
    private Button firstSelectionButton;

    private MainScreen mainScreen;
    private GuiController gc;
    private FXMLLoader loader;

    public NavBarSupplier(MainScreen mainScreen, GuiController gc) {
        this.mainScreen = mainScreen;
        this.gc = gc;
        this.loader = new FXMLLoader(getClass().getResource("NavBarSupplier.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            firstSelectionButton.getStyleClass().add("firstSelection");
            clientButton.getStyleClass().remove("redborder");
            ordersButton.getStyleClass().add("redborder");
            accountButton.getStyleClass().remove("redborder");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    void onClientsClicked(ActionEvent event) {
        ClientsScreenController csc = new ClientsScreenController(gc, mainScreen);
        mainScreen.setClients(csc);
        clientButton.getStyleClass().add("redborder");
        ordersButton.getStyleClass().remove("redborder");
        accountButton.getStyleClass().remove("redborder");
    }

    @FXML
    void onOrdersClicked(ActionEvent event) {
        OrdersScreenController osc = new OrdersScreenController(gc, mainScreen);
        mainScreen.setOrders(osc);
        clientButton.getStyleClass().remove("redborder");
        ordersButton.getStyleClass().add("redborder");
        accountButton.getStyleClass().remove("redborder");
    }

    @FXML
    void onAccountClicked(ActionEvent event) {
        AccountScreenController asc = new AccountScreenController(gc, mainScreen);
    	PaymentEditScreenController pesc = new PaymentEditScreenController(gc, asc, mainScreen);
        mainScreen.setAccount(asc, pesc);
        clientButton.getStyleClass().remove("redborder");
        ordersButton.getStyleClass().remove("redborder");
        accountButton.getStyleClass().add("redborder");
    }

    @FXML
    void onLogoutClicked(ActionEvent event) {
    	clientButton.getStyleClass().remove("redborder");
        ordersButton.getStyleClass().remove("redborder");
        accountButton.getStyleClass().remove("redborder");
        logOutButton.getStyleClass().add("redborder");
    	gc.getAc().logout();
    	
    	Scene scene = new Scene(new LoginScreenController(gc));
        Stage stage = (Stage) this.getScene().getWindow();
        double previousWidth = stage.getWidth();
        double previousHeight = stage.getHeight();

        stage.setScene(scene);
        stage.setWidth(previousWidth);
        stage.setHeight(previousHeight);

        stage.show();
    }
}
