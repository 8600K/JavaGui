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

public class AddProductController implements Initializable {

    private Alert alert = new Alert(Alert.AlertType.NONE);

    @FXML private TableView initTableView;
    @FXML private TableView updatedTableView;

    @FXML private TextField searchBar;
    @FXML private Button cancelBtn;
    @FXML private Button saveBtn;

    /**
     * ObservableLists created here are for the TOP table view, BOTTOM table view, and one is for the searchable data.
     * Below this we have all the TableColumns, and TextFields being initialized.
     */
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
     * Initialize method.  This sets up the CellValueFactories, and grabs any and all data from the DataArray class / LinkedLists.
     * A simple for loop and dataArraySize getter method are what make this work.
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



        //Loop to get all data from DataArray.
        for(int i = 0; i < DataArray.getPartArraySize(); i++)
        {
            Part temp = DataArray.getPartData(i);
            partObList.add(temp);
            searchPartObList.add(temp);
        }



        initTableView.setItems(partObList);


    }


    /**
     * This method handles most of the logic within this controller.  It checks to make sure the Text Fields haven't been left empty, sets up the variables to be passed into the LinkedList,
     * and uses Regex for more advanced input validation.  It also ensures that logic errors like min is greater than max don't exist, and so forth.
     * Lastly, and unlike the other saveButton methods, this one also gets data from the bottom Table View within the page.
     *
     * Another Runtime Error Bug *
     * I originally had a system where the Product class took in a regular Array.  However I was having multiple problems with this implementation, especially when it came to
     * adding more parts after initially adding the Product.  So I gutted the system, and remade it using a LinkedList.  I left some of the old code.
     *
     */
    public void saveButton() {

        alert.setAlertType(Alert.AlertType.ERROR);

        if (nameTxt.getText().isEmpty() || invTxt.getText().isEmpty() || priceTxt.getText().isEmpty()
                || maxTxt.getText().isEmpty() || minTxt.getText().isEmpty() || updatedTableView.getItems().isEmpty()) {
            alert.setContentText("Please fill out all available fields, excluding ID.  And ensure that you've added Parts that make up the Product.");
            alert.show();
        } else {

            String name;
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
            } else { //Once the program finally reaches this stage, type data has been validated, however we still need to ensure min/max/inv is correct and there are no logic errors.
                name = nameTxt.getText();
                inv = Integer.parseInt(invTxt.getText());
                price = Double.parseDouble(priceTxt.getText());
                max = Integer.parseInt(maxTxt.getText());
                min = Integer.parseInt(minTxt.getText());

                if(min < max && inv >= min && inv <= max)
                {

                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Changes have been committed.");
                    alert.show();

                    //Part[] partArray = new Part[256];
                    LinkedList<Part> parts = new LinkedList<>();



                    for(int i = 0; i < addedPartObList.size(); i++)
                    {
                        //partArray[i] = (Part) updatedTableView.getItems().get(i);
                        parts.add(i, addedPartObList.get(i));
                        //System.out.println(addedPartObList.get(i));

                    }




                    Product product = new Product(tempID, name, price, inv, min, max, parts) {};

                    DataArray.addProductData(product);

                    cancelBtn.fire();



                } else {
                    alert.setContentText("Please ensure that the Minimum Field is less than the Maximum Field, and that the Inventory Field is between the two.");
                    alert.show();
                }
            }
        }
    }


    /**
     * This adds the Part data from the top Table View to the bottom Table View.
     * With this, the save button (See saveButton for more details) can meet the final parameter of thee Product class by getting whatever part data is on the
     * bottom Table View.
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
        }


    }

    /**
     * This method performs the task of removing Part data from the bottom Table View without deleting the data in the top Table View
     * EDIT * Added the confirmation button as per the project's requirements.  Had to make a change to the alert.show, and instead change it to alert.showAndWait.
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

            alert.showAndWait();
            ButtonType result = alert.getResult();
            if (result == ButtonType.OK) {
                addedPartObList.remove(index);
            }
        }

    }


    /**
     * Method for Text Field + Table View to create a search bar.
     * Uses the other objectiveList that I created above.
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
     * Simple method to return back to the Main scnene.
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
