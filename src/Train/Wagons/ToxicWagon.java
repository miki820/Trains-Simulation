package Train.Wagons;

import Items.Liquid;
import Items.HeavyCargo;

public class ToxicWagon extends HeavyMaterialsWagon<HeavyCargo> {

    private int safeToxicityLevel;
    private int aerationLevel;
    private Liquid liquid;

    public ToxicWagon(double netWeight, double grossWeight, String sender, boolean breakSystem, int enginePower, int thickness,
                      int safeToxicityLevel, Liquid liquid, int aerationLevel) {
        super(netWeight, grossWeight, sender, breakSystem, enginePower, thickness);
        this.safeToxicityLevel = safeToxicityLevel;
        this.liquid = liquid;
        this.aerationLevel = aerationLevel;
    }

    public boolean checkToxicity() {
        if (liquid.getToxicityLevel() > safeToxicityLevel) {
            System.out.println("Toxicity level to high!!");
            return true;
        } else {
            System.out.println("Toxicity level is fine");
            return false;
        }
    }

    public void checkAeration() {
        if (checkToxicity()) {
            this.aerationLevel += (int) (aerationLevel * 0.05);
            System.out.println("Aeration level increased");
        } else {
            System.out.println("Aeration level is fine");
        }
    }

    public int getSafeToxicityLevel() {
        return safeToxicityLevel;
    }

    public void setSafeToxicityLevel(int safeToxicityLevel) {
        this.safeToxicityLevel = safeToxicityLevel;
    }

    public int getAerationLevel() {
        return aerationLevel;
    }

    public void setAerationLevel(int aerationLevel) {
        this.aerationLevel = aerationLevel;
    }

    public Liquid getLiquid() {
        return liquid;
    }

    public void setLiquid(Liquid liquid) {
        this.liquid = liquid;
    }

    @Override
    public String toString() {
        return "Toxic: " + super.toString() +
                "Safe Toxicity Level is: " + safeToxicityLevel +
                ", Aeration Level is : " + aerationLevel +
                ", Liquid: " + liquid;
    }
}


