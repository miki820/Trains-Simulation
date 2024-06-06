package Game;

//Clas that represents connection of stations
public class Connection {
    private final RailwayStation sourceStation;
    private final RailwayStation targetStation;
    private final int distance;

    public Connection(RailwayStation sourceStation, RailwayStation targetStation, int distance) {
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
        this.distance = distance;
    }

    public RailwayStation getSourceStation() {
        return sourceStation;
    }

    public RailwayStation getTargetStation() {
        return targetStation;
    }

    @Override
    public String toString() {
        return "Connection: " + "Source Station = " + sourceStation +
                ", Target Station = " + targetStation + ", distance between them = " + distance;
    }
}
