package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    /**
     * Declarations for Alert, TableViews, TextFields, Buttons, ObservableLists, and TableColumns.
     */
    private Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML
    private TableView initTableView;
    @FXML private TableView updatedTableView;

    @FXML private TextField searchBar;
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;

    private ObservableList<Part> partObList = FXCollections.observableArrayList();
    private ObservableList<Part> addedPartObList = FXCollections.observableArrayList();
    private ObservableList<Part> searchPartObList = FXCollections.observableArrayList();

    @FXML private TableColumn<Part, Integer> idColPart;
    @FXML private TableColumn<Part, String> nameColPart;
    @FXML private TableColumn<Part, Integer> invColPart;
    @FXML private TableColumn<Part, Double> priceColPart;

    @FXML private TableColumn<Part, Integer> idColPart1;
    @FXML private TableColumn<Part, String> nameColPart1;
    @FXML private TableColumn<Part, Integer> invColPart1;
    @FXML private TableColumn<Part, Double> priceColPart1;

    @FXML private TextField idTxt;
    @FXML private TextField nameTxt;
    @FXML private TextField invTxt;
    @FXML private TextField priceTxt;
    @FXML private TextField maxTxt;
    @FXML private TextField minTxt;

    /**
     * Initialize method.  Sets CellValueFactories, calls the method getDataFromMainController (See that method below).
     * The loop adds all Part Data to the top TableView.
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



        try {
            getDataFromMainController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < DataArray.getPartArraySize(); i++)
        {
            Part temp = DataArray.getPartData(i);
            partObList.add(temp);
            searchPartObList.add(temp);
        }



        initTableView.setItems(partObList);

    }

    /**
     * Gets and sets all the data for the different TextFields and outputs the Parts that are tied with the Product being modified to the bottom Table View.
     * @throws IOException
     */
    public void getDataFromMainController() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();

        MainController mc = loader.getController();
        int i = mc.getFocusedIndex();

        Product product = mc.getModifyProductData(i);

        idTxt.setText(String.valueOf(product.getId()));
        nameTxt.setText(product.getName());
        invTxt.setText(String.valueOf(product.getStock()));
        priceTxt.setText(String.valueOf(product.getPrice()));
        maxTxt.setText(String.valueOf(product.getMax()));
        minTxt.setText(String.valueOf(product.getMin()));

        for(int j = 0; j < product.getList().size(); j++)
        {
            //Part[] temp = product.getArray();
            addedPartObList.add(product.getList().get(j));

        }
        updatedTableView.setItems(addedPartObList);
    }


    /**
     * Handles input validation, logic errors, and replaces the old product with the new data.  (See DataArray method replaceProductData() for more information).
     */
    public void saveButton() {

        alert.setAlertType(Alert.AlertType.ERROR);

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
                id = Integer.parseInt((idTxt.getText()));

                if(min < max && inv >= min && inv <= max)
                {

                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Changes have been committed.");
                    alert.show();

                    /**
                     *
                     */
                    //Part[] partArray = new Part[256];

                    LinkedList<Part> parts = new LinkedList<>();

                    for(int i = 0; i < addedPartObList.size(); i++)
                    {
                        //partArray[i] = addedPartObList.get(i);
                        parts.add(i, addedPartObList.get(i));
                    }




                    Product product = new Product(id, name, price, inv, min, max, parts) {};
                    //System.out.println("ID: " + id);
                    DataArray.replaceProductData(product, id - 1);

                    cancelBtn.fire();



                } else {
                    alert.setContentText("Please ensure that the Minimum Field is less than the Maximum Field, and that the Inventory Field is between the two.");
                    alert.show();
                }


            }

        }

    }


    /**
     * Synced with the Add button.
     * Takes selected Part from top TableView and adds it to the bottom TableView.
     */
    public void onAdd()
    {
        ObservableList<Part> partRowSelected;
        partRowSelected = initTableView.getSelectionModel().getSelectedItems();


        if(partRowSelected.isEmpty())
        {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("No Part Rows are selected.");
            alert.show();
        } else { //I originally didn't have this else statement, however I found it made things a bit more consistent, and flow a little better, so I added it.
            int index = initTableView.getSelectionModel().getSelectedIndex();
            addedPartObList.add(DataArray.getPartData(index));
            updatedTableView.setItems(addedPartObList);
            updatedTableView.sort();
        }


    }

    /**
     * Removes the selected Part from the bottom TableView.
     * EDIT * Added confirmation alert and functionality as per project requirement.
     */
    public void onRemove()
    {
        ObservableList<Part> partRowSelected;
        partRowSelected = updatedTableView.getSelectionModel().getSelectedItems();

        if(partRowSelected.isEmpty())
        {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("No Part Rows are selected.");
            alert.show();
        } else { //I originally didn't have this else statement, however I found it made things a bit more consistent, and flow a little better, so I added it.
            int index = updatedTableView.getSelectionModel().getSelectedIndex();
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you wish to remove this part?");
            /**
             * Little logic error that I ran into here.  Had the result come before the showAndWait(),
             * Causes the user to have to delete the data twice, instead of just confirming.
             */
            alert.showAndWait();
            ButtonType result = alert.getResult();
            if (result == ButtonType.OK) {
                addedPartObList.remove(index);
            }
        }

    }

    /**
     * Method that allows the user to search for specific Parts on the top Table View.
     */
    public void searchParts()
    {

        FilteredList<Part> filteredData = new FilteredList<>(searchPartObList, m -> true);


        searchBar.textProperty().addListener((observable, oldVal, newVal) -> {
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
        sortedData.comparatorProperty().bind(initTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        initTableView.setItems(sortedData);

    }


    /**
     * Sends the user back to the MainScene.
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



}
