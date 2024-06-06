package Items;

public class BasicCargo extends Cargo {

    private int size;
    private double value;

    public BasicCargo(double mass, String serialNumber, int size, double value) {
        super(mass, serialNumber);
        this.size = size;
        this.value = value;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Basic Cargo: " +
                " Size = " + size +
                ", Value = " + value;
    }
}
