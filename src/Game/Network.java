package Game;

import java.util.*;


//Represents network of all connections
public class Network {

    private final List<RailwayStation> stations;
    private final List<Connection> connections;

    public Network() {
        connections = new ArrayList<>();
        stations = new ArrayList<>();
    }

    //Adds new connection to network
    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    //Add new stations to network
    public void addStation(RailwayStation railwayStation) {
        stations.add(railwayStation);
    }

    //Method that gets connection for a given station
    public List<Connection> getConnection(RailwayStation railwayStation) {
        List<Connection> connections = new ArrayList<>();
        for (Connection connection : railwayStation.getConnections()) {
            if (connection.getSourceStation() == railwayStation) {
                connections.add(connection);
            }
        }
        return connections;
    }

    //Breadth-First Search algorithm which search graphs and finds the shortest path between two stations
    public List<RailwayStation> pickRoute(RailwayStation sourceStation, RailwayStation targetStation) {
        //Map that stores all previous stations for each visited station in the found path
        Map<RailwayStation, RailwayStation> previousStation = new HashMap<>();

        //Queue for station to visit
        Queue<RailwayStation> queue = new LinkedList<>();

        //First station
        queue.add(sourceStation);

        while (!queue.isEmpty()) {
            //Taking first station from queue
            RailwayStation currentStation = queue.poll();

            //Checking if we are at a final destination
            if (currentStation == targetStation) {
                break;
            }

            //For every connection from source
            for (Connection connection : getConnection(currentStation)) {
                //Take targeted station for connection
                RailwayStation neighbour = connection.getTargetStation();

                //If neighbour wasn't visited yet
                if (!previousStation.containsKey(neighbour)) {

                    //Add current station and its predecessor
                    previousStation.put(neighbour, currentStation);

                    //Add neighbour to queue to visit
                    queue.add(neighbour);
                }
            }
        }

        //List that stores proper route
        List<RailwayStation> route = new ArrayList<>();
        RailwayStation currentStation = targetStation;

        //Creating route starting from the end
        while (currentStation != sourceStation) {
            route.add(currentStation);
            currentStation = previousStation.get(currentStation);
        }

        route.add(sourceStation);

        //Reversing route to have them from source station to target one
        Collections.reverse(route);
        return route;
    }

    @Override
    public String toString() {
        return "Network Of Railroads " + ", Stations = " + stations + "\n" + connections;
    }
}
