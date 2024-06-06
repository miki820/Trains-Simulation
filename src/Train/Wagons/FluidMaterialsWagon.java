package Train.Wagons;

import Interfaces.Fluidable;
import Items.BasicCargo;

public class FluidMaterialsWagon extends BasicMaterialsWagon<BasicCargo> implements Fluidable {

    private double volume;
    private boolean markings;
    private double density;

    public FluidMaterialsWagon(double netWeight, double grossWeight, String sender, boolean breakSystem, double height, double width, double length, double load, double volume, boolean markings, double density) {
        super(netWeight, grossWeight, sender, breakSystem, height, width, length, load);
        this.volume = volume;
        this.markings = markings;
        this.density = density;
    }

    @Override
    public void isFlammable() {
        System.out.println(this.markings);
    }

    @Override
    public int getNumberOfBarrels() {
        return markings ? 3 : 4;
    }


    @Override
    public String toString() {
        return "Fluid: " + super.toString() +
                ", Volume = " + volume +
                ", Markings = " + markings + '\'' +
                ", Density = " + density;
    }

}

