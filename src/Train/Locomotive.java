package Train;

import Exceptions.RailroadHazardException;
import Interfaces.Identifiable;
import Threads.VelocityChange;

public class Locomotive implements Identifiable {

    private static int counter = 1;
    private int id = 0;
    private final String name;
    private final int maxNumberOfWagons;
    private double maxPull;
    private final int maxNumberOfWagonsNeedingElectricity;
    private final String homeStation;
    private double velocity;
    private final VelocityChange velocityChange;

    public Locomotive(String name, int maxNumberOfWagons, double maxPull, int maxNumberOfWagonsNeedingElectricity,
                      String homeStation, double velocity) {
        this.name = name;
        this.maxNumberOfWagons = maxNumberOfWagons;
        this.maxPull = maxPull;
        this.maxNumberOfWagonsNeedingElectricity = maxNumberOfWagonsNeedingElectricity;
        this.homeStation = homeStation;
        this.velocity = velocity;
        this.id = counter;
        counter++;

        //With creation of locomotive its velocity is changed
        this.velocityChange = new VelocityChange(this);
        velocityChange.start();
    }

    public double getVelocity() {
        return velocity;
    }

    public int getId() {
        return id;
    }

    public double getMaxPull() {
        return maxPull;
    }

    public int getMaxNumberOfWagons() {
        return maxNumberOfWagons;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setMaxPull(double maxPull) {
        this.maxPull = maxPull;
    }

    public void tooFast() throws RailroadHazardException {
        if (this.velocity > 200) {
            throw new RailroadHazardException();
        }
    }

    @Override
    public String toString() {
        return "Locomotive: " + "Id = " + id + ", name = " + name + ", max number of Wagons = " + maxNumberOfWagons + ", max pull = " + maxPull +
                ", max number of wagons needing electricity = " + maxNumberOfWagonsNeedingElectricity + ", home station = '" + homeStation + '\'' + ", velocity = " + velocity;
    }

    //After removing locomotive from simulation we have to stop the Thread
    public void cleanUp() {
        this.velocityChange.interrupt();
    }
}
