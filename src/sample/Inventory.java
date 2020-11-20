package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Inventory Class.
 * This class handles most of the behind the scenes work the program does.
 * For example, it keeps the parts and products in their respective ObservableLists.
 * The methods this class contains are for adding, updating, getting, and removing parts/products.
 * As well as some helper methods that help with looking up parts and products.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds Part to the allParts ArrayList.
     * @param newPart the part being passed in.
     */
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);

    }

    /**
     * Adds Product to the allProducts ArrayList.
     * @param newProduct the product being passed in.
     */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /**
     * First lookupPart, this method pertains to integers.
     * @param partId the int being passed in/the one the program is looking to match.
     * @return Either returns the part if a match was found, or returns null.
     */
    public static Part lookupPart(int partId)
    {

        int size = allParts.size();

        for(int i = 0; i < size; i++)
        {
            if(allParts.get(i).getId() == partId)
            {
                return allParts.get(i);
            }

        }
        return null;
    }

    /**
     * First lookupProduct.  this method pertains to integers.
     * @param productId the int being passed in/the one the program is looking to match.
     * @return Either returns the product if a match was found, or returns null.
     */
    public static Product lookupProduct(int productId)
    {


        int size = allParts.size();

        for(int i = 0; i < size; i++)
        {
            if(allProducts.get(i).getId() == productId)
            {
                return allProducts.get(i);
            }

        }
        return null;
    }

    /**
     * The second lookupPart.  This one pertains to Strings.
     * @param partName the string being passed in/the one the program is looking to match.
     * @return the new ObservableList I created in the method.  This one only contains matches.
     */
    public static ObservableList<Part> lookupPart(String partName)
    {
        ObservableList<Part> partObservableList = FXCollections.observableArrayList();

        int size = allParts.size();
        int counter = 0;

        for(int i = 0; i < size; i++)
        {
            if(allParts.get(i).getName().toLowerCase().contains(partName.toLowerCase()))
            {
                partObservableList.add(counter, allParts.get(i));
                counter++;
            }

        }

        return partObservableList;


    }

    /**
     * The second lookupProduct.  This one pertains to Strings.
     * @param productName the string being passed in/the one the program is looking to match.
     * @return the new ObservableList I created in the method.  This one only contains matches.
     */
    public static ObservableList<Product> lookupProduct(String productName)
    {
        ObservableList<Product> productObservableList = FXCollections.observableArrayList();

        int size = allProducts.size();
        int counter = 0;

        for(int i = 0; i < size; i++)
        {
            if(allProducts.get(i).getName().toLowerCase().contains(productName.toLowerCase()))
            {
                productObservableList.add(counter, allProducts.get(i));
                counter++;
            }

        }

        return productObservableList;
    }

    /**
     * Method that updates the allParts ArrayList with an updated value.
     * @param index index being passed in.
     * @param selectedPart Part being passed in.
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }

    /**
     * Method that updates the allProducts ArrayList with an updated value.
     * @param index index being passed in.
     * @param selectedProduct Product being passed in.
     */
    public static void updateProduct(int index, Product selectedProduct)
    {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Method that removes a Part from the allParts ArrayList.
     * @param selectedPart the part to be removed.
     * @return true if the part gets removed.
     */
    public static boolean deletePart(Part selectedPart)
    {
        return allParts.remove(selectedPart);
    }

    /**
     * Method that removes a Product from the allProducts ArrayList.
     * @param selectedProduct the product to be removed.
     * @return true if the product gets removed.
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Simple getter method.
     * @return the entire ObservableList allParts.
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**
     * Simple getter method.
     * @return the entire ObservableList allProducts.
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }


}
