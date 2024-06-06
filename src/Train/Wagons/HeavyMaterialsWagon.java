package Train.Wagons;

public class HeavyMaterialsWagon<HeavyCargo> extends Wagon {

    private int enginePower;
    private int thickness;

    public HeavyMaterialsWagon(double netWeight, double grossWeight, String sender, boolean breakSystem, int enginePower, int thickness) {
        super(netWeight, grossWeight, sender, breakSystem);
        this.enginePower = enginePower;
        this.thickness = thickness;
    }

    public void increaseEnginePower() {
        this.enginePower += 100;
        System.out.println("Engine power increased by 100");
    }

    public double spaceLeft() {
        return getGrossWeight() - getNetWeight();
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    @Override
    public String toString() {
        return "Heavy " + super.toString() +
                ", Engine Power = " + enginePower +
                ", Thickness = " + thickness;
    }
}
