package sample;

/**
 * InHouse Class.
 * Inherits from the Part class, and has a getter and setter method.
 */
public class InHouse extends Part {

    private int machineId;

    /**
     * Constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Setter method that sets the machineId.  Used by PartControllers.
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Getter method that gets the machineId.  Used by PartControllers.
     * @return machineId.
     */
    public int getMachineId() {
        return machineId;
    }
}
