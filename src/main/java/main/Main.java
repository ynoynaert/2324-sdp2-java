package main;


import java.awt.Dimension;
import java.io.IOException;

import domain.LoginController;
import gui.GuiController;
import gui.LoginScreenController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
    @Override
    public void start(Stage primaryStage) throws IOException {
    	try {
    		//LoginController lc = new LoginController();
    		GuiController gc = new GuiController();
	        LoginScreenController root = new LoginScreenController(gc);
	        Scene scene = new Scene(root);
	        
	        primaryStage.setTitle("Delaware B2B");
	        primaryStage.setScene(scene);
	        primaryStage.setMaximized(true);
	        primaryStage.setMinHeight(550);
	        primaryStage.setMinWidth(550);
	        primaryStage.show();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
