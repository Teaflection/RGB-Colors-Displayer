package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	public void start(Stage primaryStage) 
	{
		try
		{
			primaryStage.setTitle("RGB Color Displayer");
			primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/application/Main.fxml"))));
			primaryStage.setResizable(false);
			primaryStage.show();
		}
		catch(Exception exception) 
		{
			exception.printStackTrace();
		}
	}
	public static void main(String[] args) 
	{
		launch(args);
	}
}