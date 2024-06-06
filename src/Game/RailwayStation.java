package Game;

import Interfaces.Identifiable;

import java.util.ArrayList;
import java.util.List;

//Every station has connections to other stations
public class RailwayStation implements Identifiable {

    private static int counter = 1;
    private final int id;
    private final String name;
    private final List<Connection> connections;

    public RailwayStation(String name) {
        connections = new ArrayList<>();
        this.name = name;
        this.id = counter;
        counter++;
    }

    public void addConnection(Connection connection) {
        this.connections.add(connection);
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Station: " + "id = " + id + ", name = " + name;
    }
}