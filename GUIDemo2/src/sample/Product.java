package sample;

import java.util.LinkedList;

/**
 * Product Class.  Copies much of the data from the supplied part class.
 * Does have some extra functionality, such as the array that's being used.
 */

public abstract class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private LinkedList<Part> list;
    public Product(int id, String name, double price, int stock, int min, int max, LinkedList<Part> list) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.list = list;
    }

    /**
     *
     * @return the LinkedList
     */
    public LinkedList<Part> getList() {
        return list;
    }

    /**
     *
     * @param list The LinkedList that is being set
     */
    public void setList(LinkedList<Part> list) {
        this.list = list;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
}
