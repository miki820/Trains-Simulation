package Items;

public class HeavyCargo extends Cargo {

    //Is there crane or additional security systems needed to transport this cargo
    private boolean craneNeeded;
    private boolean securitySystem;

    public HeavyCargo(double mass, String serialNumber, boolean craneNeeded, boolean securitySystem) {
        super(mass, serialNumber);
        this.craneNeeded = craneNeeded;
        this.securitySystem = securitySystem;
    }

    public boolean isCraneNeeded() {
        return craneNeeded;
    }

    public void setCraneNeeded(boolean craneNeeded) {
        this.craneNeeded = craneNeeded;
    }

    public boolean isSecuritySystem() {
        return securitySystem;
    }

    public void setSecuritySystem(boolean securitySystem) {
        this.securitySystem = securitySystem;
    }

    @Override
    public String toString() {
        return "Heavy Cargo:" +
                " Crane Needed = " + craneNeeded +
                ", Security System = " + securitySystem;
    }
}
