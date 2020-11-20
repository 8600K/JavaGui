package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Alert alert = new Alert(Alert.AlertType.NONE);  //NOTE:  No @FXML tag here on purpose since "alert" becomes initialized with the "new" keyword.


    /**
     * Declaration of @FXML variables, along with an alert variable that will be used with different methods in this class.
     * Also declaring some Observable Lists and a private static focusedIndex that will be used for some getters and setters and essentially
     * ensure the right data is being passed for the modify classes.
     */
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> idColPart;
    @FXML private TableColumn<Part, String> nameColPart;
    @FXML private TableColumn<Part, Integer> invColPart;
    @FXML private TableColumn<Part, Double> priceColPart;



    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> idColProduct;
    @FXML private TableColumn<Product, String> nameColProduct;
    @FXML private TableColumn<Product, Integer> invColProduct;
    @FXML private TableColumn<Product, Double> priceColProduct;



    @FXML private TextField searchBar0;
    @FXML private TextField searchBar1;
    @FXML private Button partsBtn;
    @FXML private Button productsBtn;



    private ObservableList<Part> partObList = FXCollections.observableArrayList();
    private ObservableList<Product> productObList = FXCollections.observableArrayList();

    private ObservableList<Part> searchPartObList = FXCollections.observableArrayList();
    private ObservableList<Product> searchProductObList = FXCollections.observableArrayList();

    private static int focusedIndex;


    /**
     * Very simple method, simply exits the program.
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



        for(int i = 0; i < DataArray.getPartArraySize(); i++)
        {
            Part temp = DataArray.getPartData(i);
            partObList.add(temp);
            searchPartObList.add(temp);
        }

        for(int i = 0; i < DataArray.getProductArraySize(); i++)
        {
            Product temp = DataArray.getProductData(i);
            productObList.add(temp);
            searchProductObList.add(temp);
        }



        partTableView.setItems(partObList);
        productTableView.setItems(productObList);
    }


    /**
     * Deletes the Part/Product that is being selected.
     * There were a few bugs I had to iron out here, I've left some comments within the method itself for more information.
     *
     * This expands upon the errors I mentioned in AddProductController.  The large comment block with the old code was left to show
     * how the old system worked.
     *
     */
    public void deleteRow() {
        ObservableList<Part> partRowSelected;
        ObservableList<Product> productRowSelected;
        partRowSelected = partTableView.getSelectionModel().getSelectedItems();
        productRowSelected = productTableView.getSelectionModel().getSelectedItems();


        if (partsBtn.isFocused())  //This is kind of a cheap trick, but it allows for a single method, so I went with it.
        {
            if (partRowSelected.isEmpty()) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("No Part Rows are selected.");
                alert.show();
            } else { //I originally didn't have this else statement, however I found it made things a bit more consistent, and flow a little better, so I added it.
                int focus = partTableView.getSelectionModel().getFocusedIndex();
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure you wish to remove this part?");
                alert.showAndWait();
                ButtonType result = alert.getResult();
                if (result == ButtonType.OK) {
                    partObList.remove(focus);
                    searchPartObList.remove(focus);
                    DataArray.deletePartData(focus);
                }
            }
        } else if (productsBtn.isFocused()) {
            if (productRowSelected.isEmpty()) {

                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("No Product Rows are selected.");
                alert.show();
            } else {
                int focus = productTableView.getSelectionModel().getFocusedIndex();

                Product prod = productTableView.getItems().get(focus);

                if(prod.getList().isEmpty())
                {
                    alert.setAlertType(Alert.AlertType.CONFIRMATION);
                    alert.setContentText("Are you sure you wish to remove this part?");
                    alert.showAndWait();
                    ButtonType result = alert.getResult();
                    if (result == ButtonType.OK) {
                        productObList.remove(focus);
                        searchProductObList.remove(focus);
                        DataArray.deleteProductData(focus);
                    }
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("This Product still has an associated Part with it, please modify the Product and remove the Associated Part and then delete the Product.");
                    alert.show();
                }





               /*for (int i = 0; i < prod.getArray().length; i++) {
                    if (prod.getArray()[i] != null) {
                        //empty = false;
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.setContentText("This Product still has an associated Part with it, please modify the Product and remove the Associated Part and then delete the Product.");
                        alert.show();
                        break;
                    } else {
                        alert.setAlertType(Alert.AlertType.CONFIRMATION);
                        alert.setContentText("Are you sure you wish to remove this part?");
                        alert.showAndWait();
                        ButtonType result = alert.getResult();

                        if (result == ButtonType.OK) {
                            productObList.remove(focus);
                            searchProductObList.remove(focus);
                            DataArray.deleteProductData(focus);
                        }
                        break;
                    }
                }*/

            }
        }
    }


    /**
     * Getter method
     * @param i i = Index in this method.
     * @return the partTableView.
     */
    public Part getModifyPartData(int i)
    {
        return partTableView.getItems().get(i);
    }

    /**
     * Getter method
     * @param i i = Index in this method.
     * @return the productTableView.
     */
    public Product getModifyProductData(int i)
    {
        return productTableView.getItems().get(i);
    }

    /**
     * Could have probably made a single method for this, but decided to just make two.
     * This method uses the TextField and TableView to make a search bar.
     * Originally I had this only activate upon 'enter' key press, but after some experimentation I found that
     * activation upon any key press worked a lot more smoothly.
     * This particular method is used for searching parts.
     */
    public void searchParts()
    {

        FilteredList<Part> filteredData = new FilteredList<>(searchPartObList, m -> true);


        searchBar0.textProperty().addListener((observable, oldVal, newVal) -> {
            filteredData.setPredicate(part -> {
                // If filter text is found to be empty, displays all data.
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }

                // This adds onto the logic of comparing newVal to what's in the TableView.  Note I used lowerCase however upperCase would have also worked.
                //Or if you wanted it to be case dependent you could omit the .toLowerCase();
                String lowerCaseFilter = newVal.toLowerCase();
                String partId = String.valueOf(part.getId());
                if (partId.contains(newVal)) {
                    return true; // The filter found and matched the part ID.
                } else if (part.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // The filter found and matched the name.
                }
                return false; // No matches.

            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Part> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(partTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        partTableView.setItems(sortedData);

    }

    /**
     * Could have probably made a single method for this, but decided to just make two.
     * This method uses the TextField and TableView to make a search bar.
     * Originally I had this only activate upon 'enter' key press, but after some experimentation I found that
     * activation upon any key press worked a lot more smoothly.
     * This particular method is used for searching products.
     */
    public void searchProducts()
    {

        FilteredList<Product> filteredData = new FilteredList<>(searchProductObList, m -> true);


        searchBar1.textProperty().addListener((observable, oldVal, newVal) -> {
            filteredData.setPredicate(part -> {
                // If filter text is found to be empty, displays all data.
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }

                // This adds onto the logic of comparing newVal to what's in the TableView.  Note I used lowerCase however upperCase would have also worked.
                //Or if you wanted it to be case dependent you could omit the .toLowerCase();
                String lowerCaseFilter = newVal.toLowerCase();
                String partId = String.valueOf(part.getId());
                if (partId.contains(newVal)) {
                    return true; // The filter found and matched the part ID.
                } else if (part.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // The filter found and matched the name.
                }
                return false; // No matches.

            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Product> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(productTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        productTableView.setItems(sortedData);

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
     * This methods sends the user to the AddPartScene
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
     * This methods sends the user to the ModifyPartScene
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

            int i = partTableView.getSelectionModel().getSelectedIndex();


            setFocusedIndex(i);
            Parent modifyPartParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            Scene modifyPartScene = new Scene(modifyPartParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();


            window.setScene(modifyPartScene);
            window.show();
        }
    }

    /**
     * This methods sends the user to the AddProductsScene
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
     * This methods sends the user to the ModifyProductsScene
     *
     * **** Logical Runtime Error ****
     * I was using Product p = productTableView.getSelectionModel().getSelectedItem(); to use for the setFocusedIndex,
     * this would cause issues in certain scenarios.  For example, if you created two products, and then deleted product one,
     * and then attempted to modify product 2, the program would have a indexOutOfBoundsException.  After debugging, I found this line:
     * int i = productTableView.getSelectionModel().getFocusedIndex(); to fix the issue.  I had to do quite a bit of debugging, and I mainly used System Prints to print
     * different indexes, like both of the code above and compared them to see which one worked better.
     *
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
            Scene addProductScene = new Scene(addProductParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(addProductScene);
            window.show();
        }
    }


}
