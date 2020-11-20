package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product Class.
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id, stock, min, max;
    private String name;
    private double price;


    /**
     * Constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param id the id to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name the name to be set.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @param price the price to be set.
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * @param stock the inventory to be set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min the min to be set.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max the max to be set.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the inventory
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds associated part to ArrayList.
     * @param part the part being added.
     */
    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
    }

    /**
     * Removes associated part from ArrayList.
     * @param selectedAssociatedPart the part being removed.
     * @return true if the part is removed.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Gets all associated parts.
     * @return the ArrayList.
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }





}
