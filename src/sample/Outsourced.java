package sample;

/**
 * Outsourced Class.
 * Inherits from the Part class, and has a getter and setter method.
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * Constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Setter method that sets the companyName.  Used by PartControllers.
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Getter method that gets the companyName.  Used by PartControllers.
     * @return companyName.
     */
    public String getCompanyName() {
        return companyName;
    }
}
