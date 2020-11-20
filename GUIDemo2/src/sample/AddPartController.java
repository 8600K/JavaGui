package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable  {


    /**
     * Items dedicated for the TextFields within the Add Part fxml.
     * These are used to generate Part data.
     */
    @FXML private TextField idTxt;
    @FXML private TextField nameTxt;
    @FXML private TextField invTxt;
    @FXML private TextField priceTxt;
    @FXML private TextField maxTxt;
    @FXML private TextField swappableTxt;
    @FXML private TextField minTxt;

    /**
     * Items dedicated for Radio Buttons and respective Label
     */
    @FXML private RadioButton inHouseRdoBtn;
    @FXML private RadioButton outSourcedRdoBtn;
    @FXML private Label rdoLabel;
    private ToggleGroup inHouseOutSource;
    private Alert alert = new Alert(Alert.AlertType.NONE);


    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;


    /**
     * Initializes the Radio Buttons and their respective Toggle Group.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseOutSource = new ToggleGroup();
        this.inHouseRdoBtn.setToggleGroup(inHouseOutSource);
        this.outSourcedRdoBtn.setToggleGroup(inHouseOutSource);
    }

    /**
     * saveButton method handles most of the logic of this controller.
     * Firstly, it handles input validation, by ensuring the user has selected one of the two radio buttons.
     * Then it makes sure all the Text Fields have been filled out, after that it makes sure the data within those fields are the correct data using Regex.
     * After that, it makes sure the max field is equal or above the inventory field, and is above the min field, and makes sure the min field is lower or equal than the inv field, and lower than the max field.
     * Once that's all done, it calls the necessary methods to add the data to the LinkedList(s) and an alert tells the user their changes have been committed.
     */

    public void saveButton() {

        alert.setAlertType(Alert.AlertType.ERROR);

        if (!inHouseRdoBtn.isSelected() && !outSourcedRdoBtn.isSelected()) {
            alert.setContentText("Please select whether this part is In-House or is being Outsourced.");
            alert.show();
        } else if (nameTxt.getText().isEmpty() || invTxt.getText().isEmpty() || priceTxt.getText().isEmpty()
                || maxTxt.getText().isEmpty() || minTxt.getText().isEmpty() || swappableTxt.getText().isEmpty()) {
            alert.setContentText("Please fill out all available fields, excluding ID.");
            alert.show();
        } else {

            String name, swappable;
            int inv, max, min, tempID = -1;
            double price;

            if (!nameTxt.getText().matches("[a-zA-Z].*"  )) //Regex to match lower/uppercase strings.
            {
                alert.setContentText("Please input a String in the Name Field.  EX: \"Power Supply\" ");
                alert.show();
            } else if (!invTxt.getText().matches("[0-9]*"))
            {
                alert.setContentText("Please input a Number in the Inv Field.  EX: \"12\" \n Note: The Inv Field should have a higher value than Min, and a lower value than Max. ");
                alert.show();
            } else if (!priceTxt.getText().matches("[0-9].*"))
            {
                alert.setContentText("Please input a Number or a Floating Number in the Price Field.  EX: \"32 or 32.0\" ");
                alert.show();
            } else if (!maxTxt.getText().matches("[0-9]*"))
            {
                alert.setContentText("Please input a Number in the Max Field.  EX: \"12\" ");
                alert.show();
            } else if (!minTxt.getText().matches("[0-9]*"))
            {
                alert.setContentText("Please input a Number in the Min Field.  EX: \"5\" ");
                alert.show();
            } else if (inHouseRdoBtn.isSelected() && !swappableTxt.getText().matches("[0-9]*"))
            {
                alert.setContentText("Please input a Number in the Inv Field.  EX: \"12\" ");
                alert.show();
            } else if (outSourcedRdoBtn.isSelected() && !swappableTxt.getText().matches("[a-zA-Z].*"))
            {
                alert.setContentText("Please input a String in the Company Name Field.  EX: \"SK Hynix\" ");
                alert.show();
            } else { //Once the program finally reaches this stage, type data has been validated, however we still need to ensure min/max/inv is correct and there are no logic errors.
                name = nameTxt.getText();
                inv = Integer.parseInt(invTxt.getText());
                price = Double.parseDouble(priceTxt.getText());
                max = Integer.parseInt(maxTxt.getText());
                min = Integer.parseInt(minTxt.getText());
                swappable = swappableTxt.getText();

                if(min < max && inv >= min && inv <= max)
                {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Changes have been committed.");
                    alert.show();

                    Part part = new Part(tempID, name, price, inv, min, max) {};

                    DataArray.addPartData(part, swappable);

                    cancelBtn.fire();
                    //I just call the cancel button to return to the main scene.  Kind of cheap but works like a charm.

                } else {
                    alert.setContentText("Please ensure that the Minimum Field is less than the Maximum Field, and that the Inventory Field is between the two.");
                    alert.show();
                }
            }
        }
    }


    /**
     * This is the method used by the cancel button.  It makes the scene go back to the main scene, as the name implies.
     * @param event
     * @throws IOException
     */
    public void goBackToMainScene(ActionEvent event) throws IOException {
        Parent mainParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene mainScene = new Scene(mainParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }


    /**
     * This method is what determines whether the user inputs Machine ID or Company Name.
     * NOTE since there are only two options, for the sake of optimization I only used an if/else statement, instead of two if statements.
     * Either way would work, just figured I'd try and be efficient.
     */
    public void onRdoBtnChange()
    {
        if (this.inHouseOutSource.getSelectedToggle().equals(this.inHouseRdoBtn))
        {
            rdoLabel.setText("Machine ID");
        } else {
            rdoLabel.setText("Company Name");
        }

    }


}
