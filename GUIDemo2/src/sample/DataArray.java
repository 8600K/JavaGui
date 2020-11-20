package sample;

/**
 * This class is what handles much of the behind the scenes work of storing and handling Part Data and Product Data.
 * There are 3 LinkedLists here (and man coming from C and C++ these LinkedLists are so nice) that contain Part Data, Product Data, and Swappable Data
 * The first two data types are self explanatory (See Part and Product class if you need more clarification) the last one, Swappable is tied with Part Data.
 * When a new Part is added, there is an extra field, Machine ID or Company name, I would have just made that a string in the Part class, and been done with it, however,
 * I am now allowed to edit the Part class, so I made this extra List to handle that data.  In all honesty, the data is not used, except for Add and Modify Product, but I still wanted
 * A system to handle the data properly.
 *
 */

import java.util.LinkedList;

public class DataArray {

    //Declarations
    private static LinkedList<Part> partLL = new LinkedList<Part>();
    private static LinkedList<Product> productLL = new LinkedList<Product>();
    private static LinkedList<String> partSwappable = new LinkedList<String>();
    private static int partCounter = 1;
    private static int productCounter = 1;

    /**
     * This adds a new Part to the Part Linked List.
     * This also sets the id of said part if the part id is -1 (which is what I have set to default for new parts.  See AddPart for more details.
     * @param part the part that is being passed in
     * @param str the swappable data of either Machine ID or Company name, as mentioned above.
     */
    public static void addPartData(Part part, String str)
    {
        if(part.getId() == -1)
        {
            part.setId(partCounter);
            partCounter++;
        }


        partLL.add(part);
        partSwappable.add(str);
    }

    /**
     * This method is for ModifyPart, you can see its utilization there.
     * @param part the part being modified
     * @param index the index so the Linked List knows where to go.
     * @param str for the Swappable Linked List, since it's tied to Part Data.
     */
    public static void replacePartData(Part part, int index, String str)
    {
        partLL.set(index, part);
        partSwappable.set(index, str);
    }

    /**
     * Does as its name implies.  Used by Main Controller.
     * @param index for the Linked Lists to know where to target.
     */
    public static void deletePartData(int index)
    {
        partLL.remove(index);
        partSwappable.remove(index);
    }

    /**
     * A simple getter method.
     * @param index for the Linked List to know where to target.
     * @return the part at the designated index.
     */
    public static Part getPartData(int index)
    {
        return partLL.get(index);
    }

    /**
     * A simple getter method.
     * @param index for the Linked List to know where to target.
     * @return the string containing either a Machine ID or Company Name, based upon Index.
     */
    public static String getSwappableData(int index)
    {
        return partSwappable.get(index);
    }

    /**
     * A simple getter method, used primarily for loops to ensure no out of bounds exceptions and such.
     * @return the size of the Part Array.
     */
    public static int getPartArraySize()
    {
        return partLL.size();
    }


    //------------------------------------------------------------------------------\\

    /**
     * This adds a new Product to the Product Linked List.
     * This also sets the id of said product if the product id is -1 (which is what I have set to default for new product.  See AddProduct for more details.
     * @param product the part that is being passed in
     */
    public static void addProductData(Product product)
    {
        if(product.getId() == -1)
        {
            product.setId(productCounter);
            productCounter++;
        }

        productLL.add(product);
    }

    /**
     * Does as its name implies.  Used by Main Controller.
     * @param index for the Linked Lists to know where to target.
     */
    public static void deleteProductData(int index)
    {
        productLL.remove(index);
    }

    /**
     * This method is for ModifyProduct, you can see its utilization there.
     * @param product the product being modified
     * @param index the index so the Linked List knows where to go.
     */
    public static void replaceProductData(Product product, int index)
    {
        productLL.set(index, product);
    }

    /**
     * A simple getter method.
     * @param index for the Linked List to know where to target.
     * @return the product at the designated index.
     */
    public static Product getProductData(int index)
    {
        return productLL.get(index);
    }

    /**
     * A simple getter method, used primarily for loops to ensure no out of bounds exceptions and such.
     * @return the size of the Product Linked List.
     */
    public static int getProductArraySize()
    {
        return productLL.size();
    }

    //------------------------End of file.
}
