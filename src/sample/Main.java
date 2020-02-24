package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;


public class Main extends Application implements EventHandler<ActionEvent> {

    TextField integerField;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Happy Number checker"); //sets title
        BorderPane layout = new BorderPane();
        integerField = createIntegerField();
        layout.setCenter(integerField); //puts it in center
        Button button = new Button();
        button.setText("Check"); //calls the button "check" for the user.
        button.setOnAction(this::handle); //tell it what function to run when it is clicked.
        layout.setBottom(button); //puts the window at the bottom
        primaryStage.setScene(new Scene(layout, 300, 275));
        primaryStage.show(); //shows the window
    }

    @Override
    public void handle(ActionEvent event) {

        if (integerField.getText().matches("[a-zA-Z]")) {
            return; //if it contains letters, do nothing.
        }
        int number = Integer.parseInt(integerField.getText()); //turn the letters into a number
        Boolean evaluation = isHappy(number);
        integerField.setText(evaluation.toString()); //set the field equal to the result of the calculation
    }

    public boolean isHappy(int n) {
        List<Integer> previousNumbers = new ArrayList<>();
        previousNumbers.add(n);
        while (true) { //ensures it doesn't stop until it returns.
            int squareNumber = 0;
            String string = Integer.toString(n); //converts to string.
            for (String character : string.split("")) {
                int characterAsNumber = Integer.parseInt(character); //gets the number of each character
                squareNumber += characterAsNumber * characterAsNumber; //squares this number and adds to the total
            }
            if (squareNumber == 1) { //if the total equals one, we know it is happy.
                return true;
            } else { //if it is not happy, then check if we have seen this number before, if we have it is not happy.
                for (int foundNumber : previousNumbers) {
                    if (foundNumber == squareNumber) {
                        return false;
                    }
                }
            }
            previousNumbers.add(squareNumber); //add the number to the previousNumbers list.
            n = squareNumber; //set n equal to the new number.
        }

    }

    public TextField createIntegerField() {
        TextField textField = new TextField();
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (change.isDeleted()) {
                return change;
            }
            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        }; //ensure what is being written into the textbox is either a number, or a backspace.
        TextFormatter<String> formatter = new TextFormatter<>(filter);
        textField.setTextFormatter(formatter);
        return textField;
    }


    public static void main(String[] args) {
        launch(args);
    }


}
