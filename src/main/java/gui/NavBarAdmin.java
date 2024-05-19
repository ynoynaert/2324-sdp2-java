package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NavBarAdmin extends VBox{
    @FXML
    private Button businessesButton;

    @FXML
    private Button logOutButton;

    private MainScreen mainScreen;
    private GuiController gc;
    private FXMLLoader loader;

    public NavBarAdmin(MainScreen mainScreen, GuiController gc) {
        this.mainScreen = mainScreen;
        this.gc = gc;
        this.loader = new FXMLLoader(getClass().getResource("NavBarAdmin.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            businessesButton.getStyleClass().add("redborder");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    void onBusinessesClicked(ActionEvent event) {
        BusinessesScreenController bc = new BusinessesScreenController(gc, mainScreen);
        mainScreen.setBusinessCenter(bc);
        businessesButton.getStyleClass().add("redborder");
    }

    @FXML
    void onLogoutClicked(ActionEvent event) {
    	businessesButton.getStyleClass().remove("redborder");
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
