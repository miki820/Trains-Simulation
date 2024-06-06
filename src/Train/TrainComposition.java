package Train;

import Game.RailwayStation;
import Train.Wagons.Wagon;

import java.util.List;

//Every train compose of locomotive, wagons, start and end stations and it goes by designated route
public class TrainComposition implements Runnable {

    private Locomotive locomotive;
    private List<Wagon> wagons;
    private RailwayStation sourceStation;
    private RailwayStation targetStation;
    private double velocity;
    private List<RailwayStation> route;

    public TrainComposition(Locomotive locomotive, List<Wagon> wagons, RailwayStation sourceStation, RailwayStation targetStation) {
        this.locomotive = locomotive;
        this.wagons = wagons;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
        this.velocity = locomotive.getVelocity();
    }

    public RailwayStation getSourceStation() {
        return sourceStation;
    }

    public RailwayStation getTargetStation() {
        return targetStation;
    }

    public void setRoute(List<RailwayStation> route) {
        this.route = route;
    }

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public List<Wagon> getWagons() {
        return wagons;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < route.size(); i++) {
                RailwayStation railwayStation = route.get(i);
                //System.out.println("Train stopped at: " + railwayStation.getName());

                // Train stops at every station for 2 seconds and on the last one for 30
                if (i == route.size() - 1) {
                    Thread.sleep(30000);
                } else {
                    Thread.sleep(2000);
                }

                this.velocity = locomotive.getVelocity();
            }
        } catch (InterruptedException e) {
            System.out.println("Train journey interrupted.");
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String toString() {
        return "Train Composition: " +
                "locomotive: " + locomotive +
                ", wagons: " + wagons +
                ", source station: '" + sourceStation + '\'' +
                ", target station: '" + targetStation + '\'';
    }
}
