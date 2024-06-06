package Train.Wagons;

import Items.HeavyCargo;

public class ExplosivesWagon extends HeavyMaterialsWagon<HeavyCargo> {

    private int numberOfExtinguishers;
    private final int timeToExplode;

    public ExplosivesWagon(double netWeight, double grossWeight, String sender, boolean breakSystem, int enginePower, int thickness, int numberOfExtinguishers, int timeToExplode) {
        super(netWeight, grossWeight, sender, breakSystem, enginePower, thickness);
        this.numberOfExtinguishers = numberOfExtinguishers;
        this.timeToExplode = timeToExplode;
    }

    public boolean isThereEnoughFireExtinguishers() {
        if (numberOfExtinguishers >= 4) {
            return true;
        } else {
            this.numberOfExtinguishers++;
            return false;
        }
    }

    public void storageTimeLeft() {
        int TNTExpiryDate = 365;
        int C4ExpiryDate = 180;
        int NitroglycerinExpiryDate = 90;
        int days;
        if (this.timeToExplode > TNTExpiryDate) {
            System.out.println("You must reload explosives right now!");
        } else if (this.timeToExplode > C4ExpiryDate && this.timeToExplode < TNTExpiryDate) {
            days = TNTExpiryDate - this.timeToExplode;
            System.out.println("There is " + days + " days to reload explosives");
        } else if (this.timeToExplode > NitroglycerinExpiryDate && this.timeToExplode < C4ExpiryDate) {
            days = C4ExpiryDate - this.timeToExplode;
            System.out.println("There is " + days + " days to reload explosives");
        } else {
            days = NitroglycerinExpiryDate - this.timeToExplode;
            System.out.println("There is " + days + " days to reload explosives");
        }
    }

    public int getNumberOfExtinguishers() {
        return numberOfExtinguishers;
    }

    public void setNumberOfExtinguishers(int numberOfExtinguishers) {
        this.numberOfExtinguishers = numberOfExtinguishers;
    }

    public int getTimeToExplode() {
        return timeToExplode;
    }

    @Override
    public String toString() {
        return "Explosive has " + super.toString() +
                " Fire Extinguishers = " + numberOfExtinguishers +
                ", and there is " + timeToExplode + " days to reload them";
    }
}










