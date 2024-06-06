package Train.Wagons;

import Interfaces.Identifiable;

//Abstract class for all transported wagons
public abstract class Wagon implements Identifiable {

    private static int counter = 1;
    private int id = 0;
    private final double netWeight;
    private final double grossWeight;
    private final String sender;
    protected boolean breakSystem;

    public Wagon(double netWeight, double grossWeight, String sender, boolean breakSystem) {
        this.netWeight = netWeight;
        this.grossWeight = grossWeight;
        this.sender = sender;
        this.breakSystem = breakSystem;
        this.id = counter;
        counter++;
    }

    public void turnOnBreaks() {
        if (!breakSystem) {
            System.out.println("Breaks turned on.");
            breakSystem = true;
        }
    }

    public void repairBreakSystem(){
        System.out.println("Break system was repaired.");
        breakSystem = true;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Wagon" +
                " id = " + id +
                ", net weight = " + netWeight +
                ", gross weight = " + grossWeight +
                ", sender = " + sender +
                ", break system = " + breakSystem;
    }

}
