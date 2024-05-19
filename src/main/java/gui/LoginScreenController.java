package gui;


import java.io.IOException;

import domain.Account;
import domain.AccountController;
import domain.Admin;
import domain.LoginController;
import jakarta.persistence.EntityNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LoginScreenController extends HBox {

    private GuiController gc;
    private LoginController lc;

    @FXML
    private TextField txfEmail;

    @FXML
    private PasswordField txfPassword;

    public LoginScreenController(GuiController gc) {
        this.gc = gc;
        lc = gc.getLc();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    void signIn(ActionEvent event) {
        try {
            // admin account: admin@delaware.com
            // supplier account: account@supplier.com
            // client account: account@client.com
            String email = txfEmail.getText();
            String password = txfPassword.getText();

            lc.login(email, password);

            MainScreen ms = new MainScreen(gc);

            Scene scene = new Scene(ms);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            Stage stage = (Stage) this.getScene().getWindow();
            double previousWidth = stage.getWidth();
            double previousHeight = stage.getHeight();

            stage.setScene(scene);
            stage.setWidth(previousWidth);
            stage.setHeight(previousHeight);
            stage.setMinHeight(previousHeight);
            stage.setMinWidth(previousWidth);

            stage.show();
        } catch (EntityNotFoundException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(ex.getMessage());
            alert.setContentText(ex.getMessage());
            alert.show();
            txfEmail.clear();
            txfPassword.clear();
        } catch(IllegalArgumentException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(e.getMessage());
            alert.setContentText(e.getMessage());
            alert.show();
            txfEmail.clear();
            txfPassword.clear();
        }

    }

}
