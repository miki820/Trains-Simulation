package Items;

//Abstract class for all transported materials

public abstract class Cargo {
    private double mass;
    private String serialNumber;

    public Cargo(double mass, String serialNumber) {
        this.mass = mass;
        this.serialNumber = serialNumber;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getMass() {
        return mass;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    @Override
    public String toString() {
        return "Cargo:" +
                " Mass = " + mass +
                ", Serial Number = " + serialNumber;
    }
}
