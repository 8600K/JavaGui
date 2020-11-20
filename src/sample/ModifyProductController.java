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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * ModifyProductController.
 * Handles changing data from existing Products and removing associated parts.
 */
public class ModifyProductController implements Initializable {

    /**
     * Declarations for Alert, TableViews, TextFields, Buttons, ObservableLists, and TableColumns.
     */
    /**
     * Alert used by methods within the Class.
     */
    private Alert alert = new Alert(Alert.AlertType.NONE);

    /**
     * ArrayList used by the bottom TableView.  (updatedTableView).
     */
    private ObservableList<Part> addedPartObList = FXCollections.observableArrayList();

    /**
     * Top TableView.  Contains all Parts.
     */
    @FXML private TableView initTableView;

    /**
     * Bottom TableView.  Contains only Parts added by User.
     */
    @FXML private TableView updatedTableView;

    /**
     * SearchBar used by Top TableView.  (initTableView).
     */
    @FXML private TextField searchBar;

    /**
     * Cancel Button.  Activates goToMainScene method.
     */
    @FXML private Button cancelBtn;

    /**
     * Save Button.  Activates addPartToTable method.
     */
    @FXML private Button saveBtn;

    /**
     * Top ID Column.
     */
    @FXML private TableColumn<Part, Integer> idColPart;

    /**
     * Top Name Column.
     */
    @FXML private TableColumn<Part, String> nameColPart;

    /**
     * Top Stock Column.
     */
    @FXML private TableColumn<Part, Integer> invColPart;

    /**
     * Top Price Column.
     */
    @FXML private TableColumn<Part, Double> priceColPart;

    /**
     * Bottom ID Column.
     */
    @FXML private TableColumn<Part, Integer> idColPart1;

    /**
     * Bottom Name Column.
     */
    @FXML private TableColumn<Part, String> nameColPart1;

    /**
     * Bottom Stock Column.
     */
    @FXML private TableColumn<Part, Integer> invColPart1;

    /**
     * Bottom Price Column.
     */
    @FXML private TableColumn<Part, Double> priceColPart1;

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
     * Min TextField.  Enabled.
     */
    @FXML private TextField minTxt;

    /**
     * Initialize method.  Sets CellValueFactories, calls the method setData() to fill out the TextFields.
     * Uses Inventory.getAllParts to fill in the initTableView.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idColPart.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColPart.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColPart.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColPart.setCellValueFactory(new PropertyValueFactory<>("price"));

        idColPart1.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColPart1.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColPart1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColPart1.setCellValueFactory(new PropertyValueFactory<>("price"));

        initTableView.setItems(Inventory.getAllParts());
        setData();

    }

    /**
     * This method runs when the Save button is pressed.
     * This method handles input data validation, such as making sure the text fields are all filled out and have
     * the proper data types associated with them.
     * It also checks that min is less than max and inventory is greater than or equal to min and inventory is less than or equal to max.
     * Once those conditions have been met, the existing product is updated, and a loop runs to add any and all Parts that are to be associated with the updated Product.
     * If everything has been met, and there are no errors, an alert is sent to the user, informing them that their change(s) have been committed.
     */
    public void addProduct()
    {

        if (nameTxt.getText().isEmpty() || invTxt.getText().isEmpty() || priceTxt.getText().isEmpty()
                || maxTxt.getText().isEmpty() || minTxt.getText().isEmpty() ) {
            alert.setContentText("Please fill out all available fields, excluding ID.");
            alert.show();
        } else {

            String name;
            int inv, max, min, id;
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
            } else { //Once the program finally reaches this stage, type data has been validated, however we still need to ensure min/max/inv is correct and there are no logic errors.

                name = nameTxt.getText();
                inv = Integer.parseInt(invTxt.getText());
                price = Double.parseDouble(priceTxt.getText());
                max = Integer.parseInt(maxTxt.getText());
                min = Integer.parseInt(minTxt.getText());
                id = Integer.parseInt(idTxt.getText());

                if(min < max && inv >= min && inv <= max)
                {
                    Product product = new Product(id, name, price, inv, min, max);
                    for(int i = 0; i < updatedTableView.getItems().size(); i++)
                    {
                        Part part = (Part) updatedTableView.getItems().get(i);
                        product.addAssociatedPart(part);
                    }
                    Inventory.updateProduct(id - 1, product);

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
     * This method takes the focused Part from the top TableView, and adds it
     * to the bottom TableView.  This is in turn used by the addProduct method.
     */
    public void addPartToTable()
    {
        ObservableList<Part> partRowSelected;
        partRowSelected = initTableView.getSelectionModel().getSelectedItems();
        int index = initTableView.getSelectionModel().getFocusedIndex();
        if(partRowSelected.isEmpty())
        {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please select a Part you want to add.");
            alert.show();
        } else {
            addedPartObList.add(Inventory.getAllParts().get(index));
            updatedTableView.setItems(addedPartObList);
        }

    }

    /**
     * This method removes the focused Part from the bottom TableView, without deleting it from the Inventory.
     * Uses validation and alerts to make sure a valid Part has been selected.
     * However, unlike the method from the AddProductController class, this one removes the associatedPart
     * using the deleteAssociatedPart method from the Products class.
     */
    public void removePartFromTable()
    {
        ObservableList<Part> partRowSelected;
        partRowSelected = updatedTableView.getSelectionModel().getSelectedItems();
        int index = updatedTableView.getSelectionModel().getFocusedIndex();
        if(partRowSelected.isEmpty())
        {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please select a Part you want to remove.");
            alert.show();
        } else {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to remove this Part?");
            alert.showAndWait();
            ButtonType result = alert.getResult();
            if(result == ButtonType.OK)
            {
                Product product = getData();
                //addedPartObList.remove(index);  <-- Line that was no longer needed!
                Part part = addedPartObList.get(index);
                product.deleteAssociatedPart(part);
                updatedTableView.refresh();
            }
        }
    }

    /**
     * Uses the TextField searchBar and the top TableView initTableView to allow the Parts within the TableView to be searched by the user.
     * If the TextField is empty, it shows everything, otherwise it checks to see if a number is being inputted in the TextField using Regex.
     * If true, then it checks the number against the partId field, otherwise it checks for a string and uses the Inventory.lookupPart method which
     * has logic used for checking if a string has any similarities with the string it holds using contains() logic.
     */
    public void searchParts()
    {
        if(!searchBar.getText().equals(""))
        {
            if(searchBar.getText().matches("[0-9]*"))
            {
                int number = Integer.parseInt(searchBar.getText());
                ObservableList<Part> newPartList = FXCollections.observableArrayList();
                Part part = Inventory.lookupPart(number);
                newPartList.add(part);
                initTableView.setItems(newPartList);
                if(newPartList.contains(null)) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("No Part with that Name or ID found.");
                    alert.show();
                }
            } else {
                ObservableList<Part> newPartList = FXCollections.observableArrayList();
                newPartList = Inventory.lookupPart(searchBar.getText());
                initTableView.setItems(newPartList);
                if(newPartList.isEmpty()) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("No Part with that Name or ID found.");
                    alert.show();
                }
            }
        } else {
            initTableView.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Uses a method from MainController to get the proper index for the Product being modified.
     * Then fills the bottom TableView with the correct Parts.
     * @return Product being modified.
     */
    public Product getData() {
        MainController mc = new MainController();

        int index = mc.getFocusedIndex();

        Product product = Inventory.getAllProducts().get(index);

        addedPartObList = product.getAllAssociatedParts();
        updatedTableView.setItems(addedPartObList);

        return product;
    }

    /**
     * Gets the Product from getData method
     * and uses that to fill all the TextFields.
     */
    public void setData()
    {
        Product product = getData();
        idTxt.setText(String.valueOf(product.getId()));
        nameTxt.setText(product.getName());
        invTxt.setText(String.valueOf(product.getStock()));
        priceTxt.setText(String.valueOf(product.getPrice()));
        maxTxt.setText(String.valueOf(product.getMax()));
        minTxt.setText(String.valueOf(product.getMin()));
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
