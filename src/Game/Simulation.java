package Game;

import Train.*;
import Train.Wagons.*;
import java.util.ArrayList;
import java.util.List;

//Class that simulates random objects to make simulation run
public class Simulation {

    //Generates locomotives
    public List<Locomotive> simulateLocomotives() {
        List<Locomotive> locomotives = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            locomotives.add(new Locomotive("Locomotive" + (i + 1), (int) (Math.random() * 5 + 5), (int) (Math.random() * 1000), (int) (Math.random() * 3), "HomeStation" + (i + 1), (int) (Math.random() * 200 + 1)));
        }
        return locomotives;
    }

    //Generates Wagons
    public List<Wagon> simulateWagons() {
        List<Wagon> wagons = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            wagons.add(new RefrigeratedWagon((Math.random() * 100 + 10), (Math.random() * 100 + 10), "Sender" + (i + 1), false, (Math.random() * 100 + 10), (Math.random() * 100 + 1), (Math.random() * 100 + 10), (Math.random() * 100 + 1), (Math.random() * 50 - 100), (Math.random() * 200 + 1)));
            wagons.add(new HeavyMaterialsWagon<>((Math.random() * 100 + 10), (Math.random() * 100 + 10), "Sender" + (i + 1), true, (int) (Math.random() * 100 + 10), (int) (Math.random() * 1000 + 10)));
            wagons.add(new DiningWagon((Math.random() * 100 + 10), (Math.random() * 100 + 10), "Sender" + (i + 1), true, (int) (Math.random() * 100 + 10), "Menu" + (i + 1), (Math.random() * 1000 + 1)));
            wagons.add(new MailWagon<>((Math.random() * 100 + 10), (Math.random() * 100 + 10), "Sender" + (i + 1), true, (int) (Math.random() * 100 + 10), (int) (Math.random() * 100 + 1), (Math.random() * 1000 + 1)));
            wagons.add(new PassengerWagon<>((Math.random() * 100 + 10), (Math.random() * 100 + 10), "Sender" + (i + 1), true, (int) (Math.random() * 50 + 10), (int) (Math.random() * 200 + 1)));
            wagons.add(new ExplosivesWagon((Math.random() * 100 + 10), (Math.random() * 100 + 10), "Sender" + (i + 1), true, (int) (Math.random() * 100 + 10), (int) (Math.random() * 200 + 1), (int) (Math.random() * 200 + 1), (int) (Math.random() * 10 + 5)));
        }
        return wagons;
    }

    //Generates stations
    public List<RailwayStation> simulateStations() {
        List<RailwayStation> stations = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            stations.add(new RailwayStation("Station" + (i + 1)));
        }
        return stations;
    }

    //Generates random connections between stations
    public List<Connection> simulateConnections(List<RailwayStation> stations) {
        List<Connection> connections = new ArrayList<>();
        for (int i = 0; i < 99; i++) {
            connections.add(new Connection(stations.get(i), stations.get(i + 1), (int) (Math.random() * 100)));
        }
        return connections;
    }

    //Creates network based on stations and its connections
    public Network simulateConnectionNetwork(List<RailwayStation> stations, List<Connection> connections) {
        Network network = new Network();
        for (int i = 0; i < stations.size(); i++) {
            network.addStation(stations.get(i));
            if (i != 99) {
                stations.get(i).addConnection(connections.get(i));
                network.addConnection(connections.get(i));
            } else {
                stations.get(i).addConnection(connections.get(0));
                network.addConnection(connections.get(0));
            }
        }
        return network;
    }

    //Creates trains and its movement based on generated locomotives, wagons and stations
    public List<TrainComposition> simulateTrains(List<Locomotive> locomotives, List<Wagon> wagons, List<RailwayStation> stations, Network network) {
        List<TrainComposition> trainCompositions = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < 25; i++) {
            int numberOfWagons = (int) (Math.random() * 5 + 5);
            List<Wagon> trainWagons = new ArrayList<>();
            for (int j = 0; j < numberOfWagons; j++) {
                trainWagons.add(wagons.get(counter));
                counter++;
            }
            TrainComposition tempTrainComposition = new TrainComposition(locomotives.get(i), trainWagons, stations.get((int) (Math.random() * 50)), stations.get((int) (Math.random() * 50 + 50)));
            trainCompositions.add(tempTrainComposition);
            List<RailwayStation> route = network.pickRoute(tempTrainComposition.getSourceStation(), tempTrainComposition.getTargetStation());
            tempTrainComposition.setRoute(route);
            Thread t = new Thread(tempTrainComposition);
            t.start();
        }
        return trainCompositions;
    }
}
