package Train.Wagons;

public class BasicMaterialsWagon<BasicCargo> extends Wagon {

    private double height;
    private double width;
    private double length;
    private double load;

    public BasicMaterialsWagon(double netWeight, double grossWeight, String sender, boolean breakSystem, double height, double width, double length, double load) {
        super(netWeight, grossWeight, sender, breakSystem);
        this.height = height;
        this.width = width;
        this.length = length;
        this.load = load;
    }

    public double getVolume() {
        return height * width * length;
    }

    public double getRemainingCapacity() {
        double volume = length * width * height;
        double filled = volume * this.load;
        return volume - filled;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getLoad() {
        return load;
    }

    public void setLoad(double load) {
        this.load = load;
    }

    @Override
    public String toString() {
        return "has " + super.toString() + this.height + "m of height, " + this.width + "m of width and " + this.length + "m of length";
    }
}
