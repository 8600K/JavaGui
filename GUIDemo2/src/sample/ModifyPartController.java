package sample;

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

public class ModifyPartController implements Initializable {



    /**
     * Declarations for Radio Buttons and respective Labels.
     * Along with regular buttons and TextFields.  And lastly, and Alert.
     */
    @FXML private RadioButton inHouseRdoBtn;
    @FXML private RadioButton outSourcedRdoBtn;
    @FXML private Label rdoLabel;
    private ToggleGroup inHouseOutSource;

    @FXML private TextField idTxt;
    @FXML private TextField nameTxt;
    @FXML private TextField invTxt;
    @FXML private TextField priceTxt;
    @FXML private TextField maxTxt;
    @FXML private TextField swappableTxt;
    @FXML private TextField minTxt;

    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;

    private Alert alert = new Alert(Alert.AlertType.NONE);

    /**
     * This controller's initialize method.  Sets the Radio Buttons into their group, and then performs a try catch to get DataFromMainController, (see that method below in this class).
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseOutSource = new ToggleGroup();
        this.inHouseRdoBtn.setToggleGroup(inHouseOutSource);
        this.outSourcedRdoBtn.setToggleGroup(inHouseOutSource);
        try {
            getDataFromMainController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method handles getting all relevant data from the Main Controller, and by extensions the Main Controller's TableViews.
     * It sets all Text Fields based upon the Part that is being modified from the Main Controller.
     * It also sets the Radio Button based upon a Regex filter.
     * @throws IOException
     */
    public void getDataFromMainController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load(); //Even though root is not used, this line still needs to run.

        MainController mc = loader.getController();
        int i = mc.getFocusedIndex();

        Part part = mc.getModifyPartData(i);




        String swappableData = DataArray.getSwappableData(i);
        idTxt.setText(String.valueOf(part.getId()));
        nameTxt.setText(part.getName());
        invTxt.setText(String.valueOf(part.getStock()));
        priceTxt.setText(String.valueOf(part.getPrice()));
        maxTxt.setText(String.valueOf(part.getMax()));
        swappableTxt.setText(swappableData);
        minTxt.setText(String.valueOf(part.getMin()));

        if(swappableData.matches("[0-9]*"))
        {
            inHouseRdoBtn.fire();
        } else {
            outSourcedRdoBtn.fire();
        }


    }

    /**
     * Like in AddPart, this SaveButton handles all of the logic to make sure the user has entered the correct data,
     * and that logic errors do not exist.  After that, the replace method from DataArray (See DataArray for more details) is called, and the user
     * is sent back to the MainScene.
     * @throws IOException
     */
    public void saveButton() throws IOException {

        alert.setAlertType(Alert.AlertType.ERROR);

        int i = Integer.parseInt(idTxt.getText()) - 1;



        if (!inHouseRdoBtn.isSelected() && !outSourcedRdoBtn.isSelected()) {
            alert.setContentText("Please select whether this part is In-House or is being Outsourced.");
            alert.show();
        } else if (nameTxt.getText().isEmpty() || invTxt.getText().isEmpty() || priceTxt.getText().isEmpty()
                || maxTxt.getText().isEmpty() || minTxt.getText().isEmpty() || swappableTxt.getText().isEmpty()) {
            alert.setContentText("Please fill out all available fields, excluding ID.");
            alert.show();
        } else {

            String name, swappable;
            int inv, max, min;
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
                    Part part = new Part(i + 1, name, price, inv, min, max) {};
                    DataArray.replacePartData(part, i, swappable);
                    cancelBtn.fire();
                } else {
                    alert.setContentText("Please ensure that the Minimum Field is less than the Maximum Field, and that the Inventory Field is between the two.");
                    alert.show();
                }
            }
        }
    }

    /**
     * This sends the user back to the MainScene.
     * @param event
     * @throws IOException
     */
    public void goBackToMainScene(javafx.event.ActionEvent event) throws IOException {
        Parent mainParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene mainScene = new Scene(mainParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(mainScene);
        window.show();
    }

    /**
     * This method is what determines the whether the user inputs Machine ID or Company Name.
     */
    public void onRdoBtnChange()
    {
        //NOTE since there are only two options, for the sake of optimization I only used
        //an if/else statement, instead of two if statements.  Either way would work,
        //Just figured I'd try and be efficient.
        if (this.inHouseOutSource.getSelectedToggle().equals(this.inHouseRdoBtn))
        {
            rdoLabel.setText("Machine ID");
        } else {
            rdoLabel.setText("Company Name");
        }

    }



}
