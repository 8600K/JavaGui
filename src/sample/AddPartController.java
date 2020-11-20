package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
 * AddPartController.
 * This is the controller that creates new Parts using the AddPart.fxml document.
 */
public class AddPartController implements Initializable  {


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
     * Alert used by methods within the Class.
     */
    private Alert alert = new Alert(Alert.AlertType.NONE);

    /**
     * Counter used when a new Part is created.  Continually increases.  Id = Counter.
     */
    private static int partCounter = 1;

    /**
     * ObservableList for machineIds.  (InHouse).
     */
    private static ObservableList<InHouse> machineIdList = FXCollections.observableArrayList();

    /**
     * ObservableList for companyNames.  (Outsourced).
     */
    private static ObservableList<Outsourced> companyNameList = FXCollections.observableArrayList();

    /**
     * Cancel Button.  Activates goBackToMainScene method.
     */
    @FXML private Button cancelBtn;

    /**
     * Save Button.  Activates addPart method.
     */
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
     * This method runs when the Save button is pressed.
     * This method handles input data validation, such as making sure the text fields are all filled out and have
     * the proper data types associated with them.
     * It also checks that min is less than max and inventory is greater than or equal to min and inventory is less than or equal to max.
     * Once those conditions have been met, the program also has to check for the Radio Button groups, and make sure either InHouse or Outsourced has been pressed,
     * and depending on that, a Part is generated using the InHouse class or the Outsourced Class.
     * If everything has been met, and there are no errors, an alert is sent to the user, informing them that their change has been committed.
     */
    public void addPart()
    {
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
            int inv, max, min, machineId;
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
                alert.setContentText("Please input a Number in the Machine ID Field.  EX: \"12\" ");
                alert.show();
            } else if (outSourcedRdoBtn.isSelected() && !swappableTxt.getText().matches("[a-zA-Z].*"))
            {
                alert.setContentText("Please input a String in the Company Name Field.  EX: \"Intel\" ");
                alert.show();
            } else { //Once the program finally reaches this stage, type data has been validated, however we still need to ensure min/max/inv is correct and there are no logic errors.
                name = nameTxt.getText();
                inv = Integer.parseInt(invTxt.getText());
                price = Double.parseDouble(priceTxt.getText());
                max = Integer.parseInt(maxTxt.getText());
                min = Integer.parseInt(minTxt.getText());


                if(min < max && inv >= min && inv <= max)
                {


                    if(inHouseRdoBtn.isSelected())
                    {
                        machineId = Integer.parseInt(swappableTxt.getText());
                        InHouse inHouse = new InHouse(partCounter, name, price, inv, min, max, machineId);
                        inHouse.setMachineId(machineId);
                        Inventory.addPart(inHouse);
                        machineIdList.add(partCounter - 1, inHouse);
                        companyNameList.add(partCounter - 1, null);  //This becomes null, because for this index, the companyName is not being used.
                                                                                  //This is used in the getData method in ModifyPartController.
                    } else {
                        outsourceStr = swappableTxt.getText();
                        Outsourced outsourced = new Outsourced(partCounter, name, price, inv, min, max, outsourceStr);
                        outsourced.setCompanyName(outsourceStr);
                        Inventory.addPart(outsourced);
                        companyNameList.add(partCounter - 1, outsourced);
                        machineIdList.add(partCounter - 1, null);  //This becomes null, because for this index, the machineID is not being used.
                    }                                                           //This is used in the getData method in ModifyPartController.


                    partCounter++;
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
     * A getter method for the machineIdList ObservableList.
     * This list contains the machineId which is then used by the ModifyPartController.
     * @param i in this case i is synonymous with index.  This parameter ensures the right location has been accessed.
     * @return the machineIdList at the spot specified by i/index.
     */
    public InHouse getInHouse(int i)
    {
        return machineIdList.get(i);
    }

    /**
     * This is a setter/replacement method for the machineIdList ObservableList.
     * @param i in this case i is synonymous with index.  This parameter ensures the right location has been accessed.
     * @param ih = inHouse, the InHouse object being passed in.
     */
    public void replaceInHouse(int i, InHouse ih)
    {
        machineIdList.set(i, ih);
    }

    /**
     * A getter method for the companyNameList ObservableList.
     * This list contains the companyName which is then used by the ModifyPartController.
     * @param i in this case i is synonymous with index.  This parameter ensures the right location has been accessed.
     * @return the companyNameList at the spot specified by i/index.
     */
    public Outsourced getOutsourced(int i)
    {
        return companyNameList.get(i);
    }

    /**
     * This is a setter/replacement method for the companyNameList ObservableList.
     * @param i in this case i is synonymous with index.  This parameter ensures the right location has been accessed.
     * @param out = outsourced, the Outsourced object being passed in.
     */
    public void replaceOutsourced(int i, Outsourced out)
    {
        companyNameList.set(i, out);
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
