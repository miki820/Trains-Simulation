package Items;

public class Liquid extends Cargo {

    private String type;
    private int toxicityLevel;

    public Liquid(double mass, String serialNumber, String type, int toxicityLevel){
        super(mass, serialNumber);
        this.type = type;
        this.toxicityLevel = toxicityLevel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getToxicityLevel() {
        return toxicityLevel;
    }

    public void setToxicityLevel(int toxicityLevel) {
        this.toxicityLevel = toxicityLevel;
    }

    @Override
    public String toString() {
        return "Substance:" +
                " Type = " + type +
                ", Toxicity Level = " + toxicityLevel;
    }
}
