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

/**
 * ModifyPartController.
 * This is the controller that handles all methods for the ModifyPart.fxml document.
 * Primarily, this expands upon the AddPartController, and even calls some methods from that Controller.
 * Also uses a method from the MainController, more details below in the respective methods.
 */
public class ModifyPartController implements Initializable {



    /**
     * Sets swappableTxt to machineId.
     */
    @FXML private RadioButton inHouseRdoBtn;

    /**
     * Sets swappableTxt to companyName.
     */
    @FXML private RadioButton outSourcedRdoBtn;

    /**
     * Label used to tell the user whether to enter machineId or companyName.
     */
    @FXML private Label rdoLabel;

    /**
     * ToggleGroup to control RadioButtons.
     */
    private ToggleGroup inHouseOutSource;

    /**
     * ID TextField.  Disabled.
     */
    @FXML private TextField idTxt;

    /**
     * Name TextField.  Enabled.
     */
    @FXML private TextField nameTxt;

    /**
     * Stock TextField.  Enabled.
     */
    @FXML private TextField invTxt;

    /**
     * Price TextField.  Enabled.
     */
    @FXML private TextField priceTxt;

    /**
     * Max TextField.  Enabled.
     */
    @FXML private TextField maxTxt;

    /**
     * MachineId/CompanyName TextField dependent upon which Radio Button is active.  Enabled.
     */
    @FXML private TextField swappableTxt;

    /**
     * Min TextField.  Enabled.
     */
    @FXML private TextField minTxt;

    /**
     * Cancel Button.  Activates goBackToMainScene method.
     */
    @FXML private Button cancelBtn;

    /**
     * Save Button.  Activates replacePart method.
     */
    @FXML private Button saveBtn;

    /**
     * Alert used by methods within the Class.
     */
    private Alert alert = new Alert(Alert.AlertType.NONE);

    /**
     * This controller's initialize method.  Sets the Radio Buttons into their group, and calls getData().
     * The getData method fills out the TextFields based upon what was selected in the MainController.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseOutSource = new ToggleGroup();
        this.inHouseRdoBtn.setToggleGroup(inHouseOutSource);
        this.outSourcedRdoBtn.setToggleGroup(inHouseOutSource);

        getData();

    }

    /**
     * This method is called when the Save button is pressed.
     * Much like the addPart method in AddPartController, this method handles input data validation, such as making sure the text fields are all filled out and have
     * the proper data types associated with them.
     * It also checks that min is less than max and inventory is greater than or equal to min and inventory is less than or equal to max.
     * Once those conditions have been met, the program also has to check for the Radio Button groups, and make sure either InHouse or Outsourced has been pressed,
     * and depending on that, the part is updated.  One of the notable changes with this method, and with the class, really, is the lack of partCounter.
     * Since this class gets the ID of the part that is being updated, that just becomes the index for everything, and with this method as an example, the
     * updated part's id parameter that is passed in is gotten from the id TextField, which is the same as the old id.
     * If everything has been met, and there are no errors, an alert is sent to the user, informing them that their change has been committed.
     */
    public void replacePart() {
        alert.setAlertType(Alert.AlertType.ERROR);

        if (!inHouseRdoBtn.isSelected() && !outSourcedRdoBtn.isSelected()) {
            alert.setContentText("Please select whether this part is In-House or is being Outsourced.");
            alert.show();
        } else if (nameTxt.getText().isEmpty() || invTxt.getText().isEmpty() || priceTxt.getText().isEmpty()
                || maxTxt.getText().isEmpty() || minTxt.getText().isEmpty() || swappableTxt.getText().isEmpty()) {
            alert.setContentText("Please fill out all available fields, excluding ID.");
            alert.show();
        } else {

            String name, outsourceStr;
            int inv, max, min, machineId, tempId;
            double price;

            if (!nameTxt.getText().matches("[a-zA-Z].*")) //Regex to match lower/uppercase strings.
            {
                alert.setContentText("Please input a String in the Name Field.  EX: \"Power Supply\" ");
                alert.show();
            } else if (!invTxt.getText().matches("[0-9]*")) {
                alert.setContentText("Please input a Number in the Inv Field.  EX: \"12\" \n Note: The Inv Field should have a higher value than Min, and a lower value than Max. ");
                alert.show();
            } else if (!priceTxt.getText().matches("[0-9].*")) {
                alert.setContentText("Please input a Number or a Floating Number in the Price Field.  EX: \"32 or 32.0\" ");
                alert.show();
            } else if (!maxTxt.getText().matches("[0-9]*")) {
                alert.setContentText("Please input a Number in the Max Field.  EX: \"12\" ");
                alert.show();
            } else if (!minTxt.getText().matches("[0-9]*")) {
                alert.setContentText("Please input a Number in the Min Field.  EX: \"5\" ");
                alert.show();
            } else if (inHouseRdoBtn.isSelected() && !swappableTxt.getText().matches("[0-9]*")) {
                alert.setContentText("Please input a Number in the Inv Field.  EX: \"12\" ");
                alert.show();
            } else if (outSourcedRdoBtn.isSelected() && !swappableTxt.getText().matches("[a-zA-Z].*")) {
                alert.setContentText("Please input a String in the Company Name Field.  EX: \"SK Hynix\" ");
                alert.show();
            } else { //Once the program finally reaches this stage, type data has been validated, however we still need to ensure min/max/inv is correct and there are no logic errors.
                name = nameTxt.getText();
                inv = Integer.parseInt(invTxt.getText());
                price = Double.parseDouble(priceTxt.getText());
                max = Integer.parseInt(maxTxt.getText());
                min = Integer.parseInt(minTxt.getText());
                tempId = Integer.parseInt(idTxt.getText());

                if (min < max && inv >= min && inv <= max) {
                    AddPartController addPartController = new AddPartController();

                    if (inHouseRdoBtn.isSelected()) {
                        machineId = Integer.parseInt(swappableTxt.getText());
                        InHouse inHouse = new InHouse(tempId, name, price, inv, min, max, machineId);
                        inHouse.setMachineId(machineId);
                        Inventory.updatePart(tempId - 1, inHouse);
                        addPartController.replaceInHouse(tempId - 1, inHouse);
                        addPartController.replaceOutsourced(tempId - 1, null);

                    } else {
                        outsourceStr = swappableTxt.getText();
                        Outsourced outsourced = new Outsourced(tempId, name, price, inv, min, max, outsourceStr);
                        outsourced.setCompanyName(outsourceStr);
                        Inventory.updatePart(tempId - 1, outsourced);
                        addPartController.replaceOutsourced(tempId - 1, outsourced);
                        addPartController.replaceInHouse(tempId - 1, null);
                    }


                    cancelBtn.fire();
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Changes have been committed.");
                } else {
                    alert.setContentText("Please ensure that the Minimum Field is less than the Maximum Field, and that the Inventory Field is between the two.");
                }
                alert.show();
            }
        }
    }

    /**
     * This method creates two objects from two other Controller classes.
     * The purpose is to set the text fields based on what Part was chosen in MainController.
     * It also uses the null feature I made in the addPart method in AddPartController to determine which
     * radio button should be enabled, and determines the value of swappableTxt.
     */
    public void getData() {
        MainController mc = new MainController();
        AddPartController addPartController = new AddPartController();

        int index = mc.getFocusedIndex();

        Part part = Inventory.getAllParts().get(index);

        InHouse inHouse = addPartController.getInHouse(part.getId() - 1);
        Outsourced outsourced = addPartController.getOutsourced(part.getId() - 1);

        if(inHouse != null)
        {
            inHouseRdoBtn.fire();
            swappableTxt.setText(String.valueOf(inHouse.getMachineId()));
        } else {
            outSourcedRdoBtn.fire();
            swappableTxt.setText(outsourced.getCompanyName());
        }

        idTxt.setText(String.valueOf(part.getId()));
        nameTxt.setText(part.getName());
        invTxt.setText(String.valueOf(part.getStock()));
        priceTxt.setText(String.valueOf(part.getPrice()));
        maxTxt.setText(String.valueOf(part.getMax()));
        minTxt.setText(String.valueOf(part.getMin()));
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

    /**
     * This method runs when the Cancel button is pressed/.fire().
     * As the name implies, this method sends the user back to the Main.fxml document.
     * @param event
     * @throws IOException
     */
    public void goToMainScene(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene mainScene = new Scene(addPartParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }

}
