package application;

import java.lang.reflect.Field;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class MainController
{
	@FXML
	private ComboBox<String> preparedColors;

	@FXML
	private TextField RGetter, GGetter, BGetter;

	private HashMap<String, Background> preparedColorsBackgrounds;

	public void initialize()
	{
		loadPreparedColors();
	}
	public void onDisplayPreparedColorButtonClicked(ActionEvent event) 
	{
		if(this.preparedColors.getValue() == null) 
		{
			displayColorDetectionErrorAlert("Please choose one of the colors within the list!");
			return;
		}
		((Button) event.getSource()).setBackground(this.preparedColorsBackgrounds.get(this.preparedColors.getValue()));
	}
	public void onDisplayColorButtonClicked(ActionEvent event) 
	{
		try 
		{
			int R = Integer.parseInt(this.RGetter.getText());
			int G = Integer.parseInt(this.GGetter.getText());
			int B = Integer.parseInt(this.BGetter.getText());

			((Button) event.getSource()).setBackground(createColoredBackground(Color.rgb(R, G, B)));
		}
		catch(NumberFormatException exception) 
		{
			displayColorDetectionErrorAlert("You have entered at least 1 wrong value(not Integer) within the R, G, B values!");
		}
		catch(IllegalArgumentException exception) 
		{
			displayColorDetectionErrorAlert("The R, G, B cannot accept numbers beyond 0-255(inclusive)!");
		}
	}

	//Loads all the Color constants in the Color class
	private void loadPreparedColors() 
	{
		HashMap<String, Background> colors = new HashMap<>();

		for(Field field : Color.class.getFields()) 
		{
			char[] letters = field.getName().toLowerCase().toCharArray();
			letters[0] = Character.toUpperCase(letters[0]);

			String colorName = new String(letters);

			try 
			{
				Color color = (Color) field.get(field.getType());
				colors.put(colorName, createColoredBackground(color));
			}
			catch(IllegalArgumentException | IllegalAccessException exception)
			{
				exception.printStackTrace();
			}
		}
		this.preparedColors.getItems().addAll(colors.keySet());
		this.preparedColorsBackgrounds = colors;
	}
	private Background createColoredBackground(Color color) 
	{
		return new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY));
	}
	private void displayColorDetectionErrorAlert(String message)
	{
		Alert alert = new Alert(AlertType.ERROR);
		
		alert.setHeaderText("Color Detection Error");
		alert.setContentText(message);

		alert.showAndWait();
	}
}