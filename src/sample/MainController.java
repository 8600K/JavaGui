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
 * MainController.  This class handles the initial screen the user sees, and acts as a home or a 'main' scene.
 */
public class MainController implements Initializable {

    /**
     * Alert variable used throughout many different methods.
     * NOTE:  No @FXML tag here on purpose since "alert" becomes initialized with the "new" keyword.
     */
    private Alert alert = new Alert(Alert.AlertType.NONE);


    /**
     * TableView for Parts.  (Left side)
     */
    @FXML private TableView<Part> partTableView;
    /**
     * ID Column. (For Parts)
     */
    @FXML private TableColumn<Part, Integer> idColPart;
    /**
     * Name Column. (For Parts)
     */
    @FXML private TableColumn<Part, String> nameColPart;
    /**
     * Stock Column. (For Parts)
     */
    @FXML private TableColumn<Part, Integer> invColPart;
    /**
     * Price Column. (For Parts)
     */
    @FXML private TableColumn<Part, Double> priceColPart;

    /**
     * TableView for Products.  (Right side)
     */
    @FXML private TableView<Product> productTableView;

    /**
     * ID Column. (For Products).
     */
    @FXML private TableColumn<Product, Integer> idColProduct;

    /**
     * Name Column (For Products).
     */
    @FXML private TableColumn<Product, String> nameColProduct;

    /**
     * Stock Column. (For Products).
     */
    @FXML private TableColumn<Product, Integer> invColProduct;

    /**
     * Price Column. (For Products).
     */
    @FXML private TableColumn<Product, Double> priceColProduct;

    /**
     * Search Bar used by the Part TableView.
     */
    @FXML private TextField searchBar0;

    /**
     * Search Bar used by the Products TableView.
     */
    @FXML private TextField searchBar1;

    /**
     * Integer that has getter and setter methods.  Used by ModifyPartController and ModifyProductController.
     */
    private static int focusedIndex;



    /**
     * Very simple method, simply exits the program.
     * Runs when the exit button is pressed.
     */
    public void exitTheProgram(){
        System.exit(0);
    }



    /**
     * The initialize method here sets the CellValueFactories, and gets all relevant data from DataArray (See those methods in that class for more information)
     * And sets all that data into the TableViews.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idColPart.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColPart.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColPart.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColPart.setCellValueFactory(new PropertyValueFactory<>("price"));

        idColProduct.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColProduct.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColProduct.setCellValueFactory(new PropertyValueFactory<>("price"));


        partTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());





    }

    /**
     * Uses the first SearchBar (searchBar0) to check the partTableView for Part IDs and or Part Names.
     * Initially checks to make sure the searchbar isn't empty, otherwise it displays all Parts.
     * Otherwise, the method uses Regex to check for numbers that match the Part ID, it accomplishes this with the help
     * of lookupPart from the Inventory Class.
     * If a string is being inputted instead, it uses the other lookupPart method from Inventory, and this one
     * finds Parts that contain the String that is being inputted into the TextField.
     * This method runs on key lift and activation of the searchBar0 TextField.
     */
    public void searchParts()
    {
        if(!searchBar0.getText().equals(""))
        {
            if(searchBar0.getText().matches("[0-9]*"))
            {
                int number = Integer.parseInt(searchBar0.getText());
                ObservableList<Part> newPartList = FXCollections.observableArrayList();
                Part part = Inventory.lookupPart(number);
                newPartList.add(part);
                partTableView.setItems(newPartList);
                if(newPartList.contains(null)) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("No Part with that Name or ID found.");
                    alert.show();
                }
            } else {
                ObservableList<Part> newPartList = FXCollections.observableArrayList();
                newPartList = Inventory.lookupPart(searchBar0.getText());
                partTableView.setItems(newPartList);
                if(newPartList.isEmpty()) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("No Part with that Name or ID found.");
                    alert.show();
                }
            }
        } else {
            partTableView.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Uses the second SearchBar (searchBar1) to check the productTableView for Product IDs and or Product Names.
     * Initially checks to make sure the searchbar isn't empty, otherwise it displays all Products.
     * Otherwise, the method uses Regex to check for numbers that match the Product ID, it accomplishes this with the help
     * of lookupProduct from the Inventory Class.
     * If a string is being inputted instead, it uses the other lookupProduct method from Inventory, and this one
     * finds Products that contain the String that is being inputted into the TextField.
     * This method runs on key lift and activation of the searchBar1 TextField.
     */
    public void searchProducts()
    {
        if(!searchBar1.getText().equals(""))
        {
            if(searchBar1.getText().matches("[0-9]*"))
            {
                int number = Integer.parseInt(searchBar1.getText());
                ObservableList<Product> newProductList = FXCollections.observableArrayList();
                Product product = Inventory.lookupProduct(number);
                newProductList.add(product);
                productTableView.setItems(newProductList);
                if(newProductList.contains(null)) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("No Product with that Name or ID found.");
                    alert.show();
                }
            } else {
                ObservableList<Product> newProductList = FXCollections.observableArrayList();
                newProductList = Inventory.lookupProduct(searchBar1.getText());
                productTableView.setItems(newProductList);
                if(newProductList.isEmpty()) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("No Product with that Name or ID found.");
                    alert.show();
                }
            }
        } else {
            productTableView.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * Deletes selected Part.
     * Uses deletePart method from Inventory.
     * Has an alert that double checks the user wants to delete the part.
     */
    public void deletePart()
    {
        ObservableList<Part> partRowSelected;
        partRowSelected = partTableView.getSelectionModel().getSelectedItems();
        if(partRowSelected.isEmpty())
        {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please select the Part you want to delete.");
            alert.show();
        } else {
            int index = partTableView.getSelectionModel().getFocusedIndex();
            Part part = Inventory.getAllParts().get(index);
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete this Part?");
            alert.showAndWait();
            ButtonType result = alert.getResult();
            if (result == ButtonType.OK) {
                Inventory.deletePart(part);
            }
        }
    }

    /**
     * Deletes selected Product.
     * Ensures the Product being deleted has no associated parts.
     * If associated parts are found, an alert is sent to the user.
     * Uses deletePart method from Inventory.
     * Has an alert that double checks the user wants to delete the part.
     */
    public void deleteProduct()
    {
        ObservableList<Product> productRowSelected;
        productRowSelected = productTableView.getSelectionModel().getSelectedItems();
        int index = productTableView.getSelectionModel().getFocusedIndex();
        Product product = productTableView.getItems().get(index);
        if(productRowSelected.isEmpty())
        {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please select the Product you want to delete.");
            alert.show();
        } else if (!product.getAllAssociatedParts().isEmpty())
        {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("This Product still has a Part associated with it. \n" +
                    "Please modify the Product and remove the Part.");
            alert.show();
        } else {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete this Product?");
            alert.showAndWait();
            ButtonType result = alert.getResult();
            if(result == ButtonType.OK)
            {
                Inventory.deleteProduct(product);
            }
        }
    }

    /**
     * A simple setter.
     * Helps modify Parts/Products know exactly which data to pull.
     * @param i i = Index
     */
    public void setFocusedIndex(int i)
    {
        focusedIndex = i;
    }

    /**
     * A simple getter
     *
     * Had a bug with this, but I found the bug was not here, but instead happened with when I called this method.
     * See goToModifyProducts Javadoc for more information.
     *
     * @return focusedIndex
     */
    public int getFocusedIndex()
    {
        return focusedIndex;
    }

    /**
     * Sends the user to the AddPartScene.
     * @param event
     * @throws IOException
     */
    public void goToAddPartScene(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();
    }

    /**
     * Sends the user to the ModifyPartScene.
     * Makes sure a Part is focused, otherwise an alert is issued and the user
     * stays on the main scene.
     * @param event
     * @throws IOException
     */
    public void goToModifyPartScene(ActionEvent event) throws IOException {

        if(partTableView.getSelectionModel().isEmpty())
        {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please make sure you've added a Part and selected the Part you want to modify.");
            alert.show();
        } else {
            int i = partTableView.getSelectionModel().getFocusedIndex();
            setFocusedIndex(i);
            Parent addPartParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            Scene modifyPartScene = new Scene(addPartParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(modifyPartScene);
            window.show();
        }


    }

    /**
     * Sends the user to the AddProductScene.
     * @param event
     * @throws IOException
     */
    public void goToAddProductScene(ActionEvent event) throws IOException {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addProductScene);
        window.show();
    }

    /**
     * Sends the user to the ModifyProductScene.
     * Makes sure a Product is focused, otherwise an alert is issued and the user
     * stays on the main scene.
     * @param event
     * @throws IOException
     */
    public void goToModifyProductScene(ActionEvent event) throws IOException {

        if(productTableView.getSelectionModel().isEmpty())
        {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Please make sure you've added a Product and selected the Product you want to modify.");
            alert.show();
        } else {
            int i = productTableView.getSelectionModel().getFocusedIndex();
            setFocusedIndex(i);
            Parent addProductParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
            Scene modifyProductScene = new Scene(addProductParent);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(modifyProductScene);
            window.show();
        }


    }


}
