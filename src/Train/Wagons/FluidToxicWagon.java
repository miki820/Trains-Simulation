package Train.Wagons;

import Interfaces.Fluidable;
import Items.Liquid;
import java.util.Objects;

public class FluidToxicWagon extends ToxicWagon implements Fluidable {

    private String identificationSystems;

    private boolean isHermetic;

    public FluidToxicWagon(double netWeight, double grossWeight, String sender, boolean breakSystem, int enginePower, int thickness, int safeToxicityLevel,
                           int aerationLevel, Liquid liquid, String identificationSystems, boolean isHermetic) {
        super(netWeight, grossWeight, sender, breakSystem, enginePower, thickness, safeToxicityLevel, liquid, aerationLevel);
        this.identificationSystems = identificationSystems;
        this.isHermetic = isHermetic;
    }

    public String getIdentificationSystems() {
        return identificationSystems;
    }

    public void setIdentificationSystems(String identificationSystems) {
        this.identificationSystems = identificationSystems;
    }

    public boolean getHermetic() {
        return isHermetic;
    }

    public void setHermetic(boolean hermetic) {
        isHermetic = hermetic;
    }

    @Override
    public void isFlammable() {
        System.out.println(this.identificationSystems);
    }

    @Override
    public int getNumberOfBarrels() {
        return Objects.equals(identificationSystems, "On") ? 3 : 4;
    }

    @Override
    public String toString() {
        return "Fluid-Toxic " + super.toString() +
                ", Identification Systems: " + identificationSystems +
                ", Hermetic : " + isHermetic;
    }
}
