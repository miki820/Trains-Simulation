package Train.Wagons;

import Items.BasicCargo;

public class GasWagon extends BasicMaterialsWagon<BasicCargo> {

    private double pressure;
    private double capacity;

    public GasWagon(double netWeight, double grossWeight, String sender, boolean breakSystem, double height, double width, double length, double load, double pressure, double capacity) {
        super(netWeight, grossWeight, sender, breakSystem, height, width, length, load);
        this.pressure = pressure;
        this.capacity = capacity;
    }

    public void ventilation() {
        if (isGasDangerous()) {
            System.out.println("Dangerous gas detected");
        } else {
            System.out.println("Wagon is fine");
        }
    }

    public boolean isGasDangerous() {
        return false;
    }

    public void setPressure() {
        if (this.pressure > 40) {
            this.pressure = 40;
        }
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Gas: " + super.toString() +
                "Pressure: " + pressure +
                " Capacity: " + capacity;
    }
}
