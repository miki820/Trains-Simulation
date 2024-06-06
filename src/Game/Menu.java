package Game;

import Exceptions.CannotAddWagonException;
import Exceptions.DuplicateItemException;
import Interfaces.Identifiable;
import Items.*;
import Items.Package;
import Train.*;
import Train.Wagons.*;
import Threads.FileWriting;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private final List<Locomotive> locomotiveList;
    private final List<Wagon> wagons;
    private final List<RailwayStation> stations;
    private final List<Connection> connections;
    private final Network network;
    private final List<TrainComposition> trainCompositions;
    private final List<Person> people = new ArrayList<>();
    private final List<Luggage> luggages = new ArrayList<>();
    private final List<Package> packages = new ArrayList<>();
    private final List<Liquid> liquids = new ArrayList<>();
    private final List<BasicCargo> basicCargos = new ArrayList<>();
    private final List<HeavyCargo> heavyCargos = new ArrayList<>();

    public Menu() {
        Simulation simulation = new Simulation();
        locomotiveList = simulation.simulateLocomotives();
        wagons = simulation.simulateWagons();
        stations = simulation.simulateStations();
        connections = simulation.simulateConnections(stations);
        network = simulation.simulateConnectionNetwork(stations, connections);
        trainCompositions = simulation.simulateTrains(locomotiveList, wagons, stations, network);

        FileWriting fileWriting = new FileWriting(trainCompositions);
        fileWriting.start();

        displayMenu();
    }

    private void displayMenu() {
        while (true) {
            System.out.println("1. Add Vehicle or Station");
            System.out.println("2. Assign Wagon to Train");
            System.out.println("3. Add Item");
            System.out.println("4. Assign Item");
            System.out.println("5. Display");
            System.out.println("6. Remove");
            System.out.println("7. Exit Program");
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            switch (option) {
                case 1 -> addVehicleOrStation();
                case 2 -> {
                    try {
                        assignWagonToTrain();
                    } catch (CannotAddWagonException e) {
                        System.out.println("Cannot assign wagon to this train");
                    }
                }
                case 3 -> addItem();
                case 4 -> assignItem();
                case 5 -> display();
                case 6 -> remove();
                case 7 -> System.exit(0);
                default -> System.out.println("Invalid option, please provide another number");
            }
        }
    }

    private void addVehicleOrStation() {
        System.out.println("a) Add Train");
        System.out.println("b) Add Locomotive");
        System.out.println("c) Add Wagon");
        System.out.println("d) Add Railway Station");
        System.out.println("e) Add Connection between Stations");
        Scanner scanner = new Scanner(System.in);
        char option = scanner.next().charAt(0);
        switch (option) {
            case 'a' -> addTrain();
            case 'b' -> addLocomotive();
            case 'c' -> addWagon();
            case 'd' -> addStation();
            case 'e' -> addConnection();
            default -> System.out.println("Returning...");
        }
    }

    private void addTrain() {
        Scanner scanner = new Scanner(System.in);
        if (!locomotiveList.isEmpty()) {
            System.out.println("Select the locomotive to create a train: (Enter id)");
            displayList(locomotiveList);
            int id = scanner.nextInt();
            Locomotive selectedLocomotive = findById(locomotiveList, id);
            if (selectedLocomotive != null) {
                System.out.println("Select the start and end station: (Enter id after enter)");
                displayList(stations);
                int startId = scanner.nextInt();
                int endId = scanner.nextInt();
                RailwayStation startStation = findById(stations, startId);
                RailwayStation endStation = findById(stations, endId);
                if (startStation != null && endStation != null) {
                    TrainComposition tempTrain = new TrainComposition(selectedLocomotive, new ArrayList<>(), startStation, endStation);
                    trainCompositions.add(tempTrain);
                    System.out.println("Created Train: ");
                    System.out.println(tempTrain);
                    List<RailwayStation> route = network.pickRoute(startStation, endStation);
                    tempTrain.setRoute(route);
                    Thread thread = new Thread(tempTrain);
                    thread.start();
                } else {
                    handleInvalidSelection("station");
                }
            } else {
                handleInvalidSelection("locomotive");
            }
        } else {
            handleEmptyList("locomotive");
        }
    }

    private void addLocomotive() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter: locomotive name, max wagons, max pull, max electrical wagons, home station, initial speed");
        String name = scanner.next();
        int maxWagons = scanner.nextInt();
        double maxPull = scanner.nextDouble();
        int maxElectricalWagons = scanner.nextInt();
        String homeStation = scanner.next();
        double initialSpeed = scanner.nextDouble();
        Locomotive locomotive = new Locomotive(name, maxWagons, maxPull, maxElectricalWagons, homeStation, initialSpeed);
        locomotiveList.add(locomotive);
        System.out.println("Added locomotive: ");
        System.out.println(locomotive);
    }

    private void addWagon() {
        System.out.println("Select the wagon you want to add: ");
        String[] wagonTypes = {"Passenger Wagon", "Mail Wagon", "Luggage-Mail Wagon", "Dining Wagon", "Basic Cargo Wagon", "Heavy Cargo Wagon", "Refrigerated Wagon", "Liquid Materials Wagon", "Gas Materials Wagon", "Explosive Materials Wagon", "Toxic Materials Wagon", "Liquid Toxic Materials Wagon"};
        for (int i = 0; i < wagonTypes.length; i++) {
            System.out.println((i + 1) + ". " + wagonTypes[i]);
        }
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        Wagon wagon = createWagon(option);
        if (wagon != null) {
            wagons.add(wagon);
            System.out.println("Added Wagon: ");
            System.out.println(wagon);
        }
    }

    private Wagon createWagon(int option) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter: net weight, gross weight, sender, has braking system (yes/no), other specific attributes");
        double netWeight = scanner.nextDouble();
        double grossWeight = scanner.nextDouble();
        String sender = scanner.next();
        boolean hasBrakingSystem = scanner.next().equalsIgnoreCase("yes");
        switch (option) {
            case 1:
                int seats = scanner.nextInt();
                double electricPower = scanner.nextDouble();
                return new PassengerWagon<>(netWeight, grossWeight, sender, hasBrakingSystem, seats, electricPower);
            case 2:
                int freeLockers = scanner.nextInt();
                int letterContainers = scanner.nextInt();
                double electricPowerMail = scanner.nextDouble();
                return new MailWagon<>(netWeight, grossWeight, sender, hasBrakingSystem, freeLockers, letterContainers, electricPowerMail);
            case 3:
                int luggageCapacity = scanner.nextInt();
                return new LuggageMailWagon<>(netWeight, grossWeight, sender, hasBrakingSystem, luggageCapacity);
            case 4:
                int diningSeats = scanner.nextInt();
                String menu = scanner.next();
                double electricPowerDining = scanner.nextDouble();
                return new DiningWagon(netWeight, grossWeight, sender, hasBrakingSystem, diningSeats, menu, electricPowerDining);
            case 5:
                double height = scanner.nextDouble();
                double width = scanner.nextDouble();
                double length = scanner.nextDouble();
                double loadingPercentage = scanner.nextDouble();
                return new BasicMaterialsWagon<>(netWeight, grossWeight, sender, hasBrakingSystem, height, width, length, loadingPercentage);
            case 6:
                int enginePower = scanner.nextInt();
                int transportedCargo = scanner.nextInt();
                return new HeavyMaterialsWagon<>(netWeight, grossWeight, sender, hasBrakingSystem, enginePower, transportedCargo);
            case 7:
                height = scanner.nextDouble();
                width = scanner.nextDouble();
                length = scanner.nextDouble();
                loadingPercentage = scanner.nextDouble();
                String refrigeratedSender = scanner.next();
                boolean brakingSystem = scanner.next().equalsIgnoreCase("yes");
                double temperature = scanner.nextDouble();
                double electricPowerRefrigerated = scanner.nextDouble();
                return new RefrigeratedWagon(netWeight, grossWeight, refrigeratedSender, brakingSystem, height, width, length, loadingPercentage, temperature, electricPowerRefrigerated);
            case 8:
                height = scanner.nextDouble();
                width = scanner.nextDouble();
                length = scanner.nextDouble();
                loadingPercentage = scanner.nextDouble();
                double volume = scanner.nextDouble();
                boolean additionalMarkings = scanner.next().equalsIgnoreCase("yes");
                double density = scanner.nextDouble();
                return new FluidMaterialsWagon(netWeight, grossWeight, sender, hasBrakingSystem, height, width, length, loadingPercentage, volume, additionalMarkings, density);
            case 9:
                height = scanner.nextDouble();
                width = scanner.nextDouble();
                length = scanner.nextDouble();
                loadingPercentage = scanner.nextDouble();
                double gasPressure = scanner.nextDouble();
                volume = scanner.nextDouble();
                return new GasWagon(netWeight, grossWeight, sender, hasBrakingSystem, height, width, length, loadingPercentage, gasPressure, volume);
            case 10:
                enginePower = scanner.nextInt();
                transportedCargo = scanner.nextInt();
                int fireExtinguishers = scanner.nextInt();
                int shelfLife = scanner.nextInt();
                return new ExplosivesWagon(netWeight, grossWeight, sender, hasBrakingSystem, enginePower, transportedCargo, fireExtinguishers, shelfLife);
            case 11:
                enginePower = scanner.nextInt();
                int thickness = scanner.nextInt();
                int safeToxicityLevel = scanner.nextInt();
                Liquid liquid = new Liquid(scanner.nextDouble(), scanner.next(), scanner.next(), scanner.nextInt());
                int aerationLevel = scanner.nextInt();
                return new ToxicWagon(netWeight, grossWeight, sender, hasBrakingSystem, enginePower, thickness, safeToxicityLevel, liquid, aerationLevel);
            case 12:
                enginePower = scanner.nextInt();
                thickness = scanner.nextInt();
                safeToxicityLevel = scanner.nextInt();
                aerationLevel = scanner.nextInt();
                liquid = new Liquid(scanner.nextDouble(), scanner.next(), scanner.next(), scanner.nextInt());
                String identificationSystems = scanner.next();
                boolean isHermetic = scanner.next().equalsIgnoreCase("yes");
                return new FluidToxicWagon(netWeight, grossWeight, sender, hasBrakingSystem, enginePower, thickness, safeToxicityLevel, aerationLevel, liquid, identificationSystems, isHermetic);
            default:
                return null;
        }
    }

    private void addStation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter station name: ");
        String name = scanner.next();
        RailwayStation station = new RailwayStation(name);
        stations.add(station);
        System.out.println("Added Station: ");
        System.out.println(station);
    }

    private void addConnection() {
        if (!stations.isEmpty()) {
            System.out.println("Which stations do you want to connect? (Enter id)");
            displayList(stations);
            Scanner scanner = new Scanner(System.in);
            int startId = scanner.nextInt();
            int endId = scanner.nextInt();
            RailwayStation startStation = findById(stations, startId);
            RailwayStation endStation = findById(stations, endId);
            if (startStation != null && endStation != null) {
                Connection connection = new Connection(startStation, endStation, (int) (Math.random() * 100));
                connections.add(connection);
                System.out.println("Created new connection: ");
                System.out.println(connection);
            } else {
                handleInvalidSelection("station");
            }
        } else {
            System.out.println("No stations to connect");
        }
    }

    private void assignWagonToTrain() throws CannotAddWagonException {
        if (!trainCompositions.isEmpty()) {
            System.out.println("To which train do you want to add a wagon? (Enter locomotive Id)");
            displayList(trainCompositions);
            Scanner scanner = new Scanner(System.in);
            int id = scanner.nextInt();
            TrainComposition train = findById(trainCompositions, id);
            if (train != null) {
                if (train.getWagons().size() == train.getLocomotive().getMaxNumberOfWagons()) {
                    throw new CannotAddWagonException();
                }
                if (!wagons.isEmpty()) {
                    System.out.println("Which wagon do you want to assign to the selected train? (Enter Id)");
                    displayList(wagons);
                    id = scanner.nextInt();
                    Wagon wagon = findById(wagons, id);
                    if (wagon != null) {
                        double locomotivePull = train.getLocomotive().getMaxPull();
                        if (wagon.getGrossWeight() <= locomotivePull) {
                            train.getWagons().add(wagon);
                            train.getLocomotive().setMaxPull(locomotivePull - wagon.getGrossWeight());
                            System.out.println("Assigned Wagon: " + wagon + " to train " + train);
                        } else {
                            throw new CannotAddWagonException();
                        }
                    } else {
                        handleInvalidSelection("wagon");
                    }
                } else {
                    handleEmptyList("wagon");
                }
            } else {
                handleInvalidSelection("locomotive");
            }
        } else {
            handleEmptyList("train");
        }
    }

    private void addItem() {
        System.out.println("a) Add Person");
        System.out.println("b) Add Luggage");
        System.out.println("c) Add Package");
        System.out.println("d) Add Liquid");
        System.out.println("e) Add Basic Cargo");
        System.out.println("f) Add Heavy Cargo");
        Scanner scanner = new Scanner(System.in);
        char option = scanner.next().charAt(0);
        switch (option) {
            case 'a' -> addPerson();
            case 'b' -> addLuggage();
            case 'c' -> addPackage();
            case 'd' -> addLiquid();
            case 'e' -> addBasicCargo();
            case 'f' -> addHeavyCargo();
            default -> System.out.println("Returning...");
        }
    }

    private void addPerson() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter: person's name, surname, age");
        String name = scanner.next();
        String surname = scanner.next();
        int age = scanner.nextInt();
        Person person = new Person(name, surname, age);
        people.add(person);
        System.out.println("Added person: ");
        System.out.println(person);
    }

    private void addLuggage() {
        if (!people.isEmpty()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter: luggage weight, color, owner's name");
            double weight = scanner.nextDouble();
            String color = scanner.next();
            String ownerName = scanner.next();
            Person owner = findByName(people, ownerName);
            if (owner != null) {
                Luggage luggage = new Luggage(weight, color, owner);
                luggages.add(luggage);
                System.out.println("Added luggage: ");
                System.out.println(luggage);
            } else {
                handleInvalidSelection("person");
            }
        } else {
            handleEmptyList("person");
        }
    }

    private void addPackage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter: package weight, color, priority (yes/no), value");
        double weight = scanner.nextDouble();
        String color = scanner.next();
        boolean isPriority = scanner.next().equalsIgnoreCase("yes");
        int value = scanner.nextInt();
        Package pack = new Package(weight, color, isPriority, value);
        packages.add(pack);
        System.out.println("Added package: ");
        System.out.println(pack);
    }

    private void addLiquid() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter: liquid weight, color, name, toxicity level");
        double weight = scanner.nextDouble();
        String color = scanner.next();
        String name = scanner.next();
        int toxicityLevel = scanner.nextInt();
        Liquid liquid = new Liquid(weight, color, name, toxicityLevel);
        liquids.add(liquid);
        System.out.println("Added liquid: ");
        System.out.println(liquid);
    }

    private void addBasicCargo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter: basic cargo weight, color, number of pallets, value");
        double weight = scanner.nextDouble();
        String color = scanner.next();
        int numberOfPallets = scanner.nextInt();
        double value = scanner.nextDouble();
        BasicCargo basicCargo = new BasicCargo(weight, color, numberOfPallets, value);
        basicCargos.add(basicCargo);
        System.out.println("Added basic cargo: ");
        System.out.println(basicCargo);
    }

    private void addHeavyCargo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter: heavy cargo weight, color, has lifting mechanism (yes/no), has security system (yes/no)");
        double weight = scanner.nextDouble();
        String color = scanner.next();
        boolean hasLiftingMechanism = scanner.next().equalsIgnoreCase("yes");
        boolean hasSecuritySystem = scanner.next().equalsIgnoreCase("yes");
        HeavyCargo heavyCargo = new HeavyCargo(weight, color, hasLiftingMechanism, hasSecuritySystem);
        heavyCargos.add(heavyCargo);
        System.out.println("Added heavy cargo: ");
        System.out.println(heavyCargo);
    }

    private void assignItem() {
        System.out.println("a) Assign person");
        System.out.println("b) Assign luggage");
        System.out.println("c) Assign package");
        Scanner scanner = new Scanner(System.in);
        char option = scanner.next().charAt(0);
        switch (option) {
            case 'a' -> assignPerson();
            case 'b' -> assignLuggage();
            case 'c' -> assignPackage();
            default -> System.out.println("Returning...");
        }
    }

    private void assignPerson() {
        Scanner scanner = new Scanner(System.in);
        if (!wagons.isEmpty()) {
            List<Wagon> passengerWagons = filterWagonsByType(PassengerWagon.class);
            if (!passengerWagons.isEmpty()) {
                System.out.println("To which wagon do you want to add the person? (Enter wagon id)");
                displayList(passengerWagons);
                int wagonId = scanner.nextInt();
                PassengerWagon<Person> selectedWagon = (PassengerWagon<Person>) findById(passengerWagons, wagonId);
                if (selectedWagon != null) {
                    System.out.println("Which person do you want to assign to the wagon? (Enter name)");
                    displayList(people);
                    String name = scanner.next();
                    Person person = findByName(people, name);
                    if (person != null) {
                        try {
                            selectedWagon.addPassenger(person);
                            System.out.println("Assigned person: " + person + " to wagon " + selectedWagon);
                        } catch (DuplicateItemException e) {
                            System.out.println("Cannot assign the same person twice");
                        }
                    } else {
                        handleInvalidSelection("person");
                    }
                } else {
                    handleInvalidSelection("wagon");
                }
            } else {
                System.out.println("No passenger wagons, cannot assign person");
            }
        } else {
            handleEmptyList("wagon");
        }
    }

    private void assignLuggage() {
        Scanner scanner = new Scanner(System.in);
        if (!wagons.isEmpty()) {
            List<Wagon> luggageWagons = filterWagonsByType(LuggageMailWagon.class);
            if (!luggageWagons.isEmpty()) {
                System.out.println("To which wagon do you want to add the luggage? (Enter wagon id)");
                displayList(luggageWagons);
                int wagonId = scanner.nextInt();
                LuggageMailWagon<Luggage> selectedWagon = (LuggageMailWagon<Luggage>) findById(luggageWagons, wagonId);
                if (selectedWagon != null) {
                    System.out.println("Which luggage do you want to assign to the wagon? (Enter id)");
                    displayList(luggages);
                    int luggageId = scanner.nextInt();
                    Luggage luggage = findById(luggages, luggageId);
                    if (luggage != null) {
                        try {
                            selectedWagon.addLuggage(luggage);
                            System.out.println("Assigned luggage: " + luggage + " to wagon " + selectedWagon);
                        } catch (DuplicateItemException e) {
                            System.out.println("Cannot assign the same luggage twice");
                        }
                    } else {
                        handleInvalidSelection("luggage");
                    }
                } else {
                    handleInvalidSelection("wagon");
                }
            } else {
                System.out.println("No luggage wagons, cannot assign luggage");
            }
        } else {
            handleEmptyList("wagon");
        }
    }

    private void assignPackage() {
        Scanner scanner = new Scanner(System.in);
        if (!wagons.isEmpty()) {
            List<Wagon> mailWagons = filterWagonsByType(MailWagon.class);
            if (!mailWagons.isEmpty()) {
                System.out.println("To which wagon do you want to add the package? (Enter wagon id)");
                displayList(mailWagons);
                int wagonId = scanner.nextInt();
                MailWagon<Package> selectedWagon = (MailWagon<Package>) findById(mailWagons, wagonId);
                if (selectedWagon != null) {
                    System.out.println("Which package do you want to assign to the wagon? (Enter id)");
                    displayList(packages);
                    int packageId = scanner.nextInt();
                    Package pack = findById(packages, packageId);
                    if (pack != null) {
                        try {
                            selectedWagon.addPackage(pack);
                            System.out.println("Assigned package: " + pack + " to wagon " + selectedWagon);
                        } catch (DuplicateItemException e) {
                            System.out.println("Cannot assign the same package twice");
                        }
                    } else {
                        handleInvalidSelection("package");
                    }
                } else {
                    handleInvalidSelection("wagon");
                }
            } else {
                System.out.println("No mail wagons, cannot assign package");
            }
        } else {
            handleEmptyList("wagon");
        }
    }

    private void display() {
        System.out.println("a) Display Trains");
        System.out.println("b) Display Locomotives");
        System.out.println("c) Display Wagons");
        System.out.println("d) Display Stations");
        System.out.println("e) Display Connections");
        System.out.println("f) Display Items");
        Scanner scanner = new Scanner(System.in);
        char option = scanner.next().charAt(0);
        switch (option) {
            case 'a' -> displayList(trainCompositions);
            case 'b' -> displayList(locomotiveList);
            case 'c' -> displayList(wagons);
            case 'd' -> displayList(stations);
            case 'e' -> displayList(connections);
            case 'f' -> displayItems();
            default -> System.out.println("Returning...");
        }
    }

    private void displayItems() {
        System.out.println("1. Display People");
        System.out.println("2. Display Luggages");
        System.out.println("3. Display Packages");
        System.out.println("4. Display Liquids");
        System.out.println("5. Display Basic Cargos");
        System.out.println("6. Display Heavy Cargos");
        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        switch (option) {
            case 1 -> displayList(people);
            case 2 -> displayList(luggages);
            case 3 -> displayList(packages);
            case 4 -> displayList(liquids);
            case 5 -> displayList(basicCargos);
            case 6 -> displayList(heavyCargos);
            default -> System.out.println("Returning...");
        }
    }

    private void remove() {
        System.out.println("a) Remove Locomotive");
        System.out.println("b) Remove Wagon");
        System.out.println("c) Remove Person");
        System.out.println("d) Remove Item");
        Scanner scanner = new Scanner(System.in);
        char option = scanner.next().charAt(0);
        switch (option) {
            case 'a' -> removeLocomotive();
            case 'b' -> removeWagon();
            case 'c' -> removePerson();
            default -> System.out.println("Returning...");
        }
    }

    private void removeLocomotive() {
        if (!locomotiveList.isEmpty()) {
            System.out.println("Which locomotive do you want to remove? (Enter id)");
            displayList(locomotiveList);
            Scanner scanner = new Scanner(System.in);
            int id = scanner.nextInt();
            Locomotive locomotive = findById(locomotiveList, id);
            if (locomotive != null) {
                locomotiveList.remove(locomotive);
                System.out.println("Removed locomotive: ");
                System.out.println(locomotive);
            } else {
                handleInvalidSelection("locomotive");
            }
        } else {
            System.out.println("No locomotives to remove");
        }
    }

    private void removeWagon() {
        if (!wagons.isEmpty()) {
            System.out.println("Which wagon do you want to remove? (Enter id)");
            displayList(wagons);
            Scanner scanner = new Scanner(System.in);
            int id = scanner.nextInt();
            Wagon wagon = findById(wagons, id);
            if (wagon != null) {
                wagons.remove(wagon);
                System.out.println("Removed wagon: ");
                System.out.println(wagon);
            } else {
                handleInvalidSelection("wagon");
            }
        } else {
            System.out.println("No wagons to remove");
        }
    }

    private void removePerson() {
        if (!people.isEmpty()) {
            System.out.println("Which person do you want to remove? (Enter id)");
            displayList(people);
            Scanner scanner = new Scanner(System.in);
            int id = scanner.nextInt();
            Person person = findById(people, id);
            if (person != null) {
                people.remove(person);
                System.out.println("Removed person: ");
                System.out.println(person);
            } else {
                handleInvalidSelection("person");
            }
        } else {
            System.out.println("No people to remove");
        }
    }

    private <T> void displayList(List<T> list) {
        for (T item : list) {
            System.out.println(item);
        }
    }

    private <T> T findById(List<T> list, int id) {
        for (T item : list) {
            if (item instanceof Identifiable && ((Identifiable) item).getId() == id) {
                return item;
            }
        }
        return null;
    }

    private <T> T findByName(List<T> list, String name) {
        for (T item : list) {
            if (item instanceof Person && ((Person) item).getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    private <T> List<Wagon> filterWagonsByType(Class<T> type) {
        List<Wagon> filteredWagons = new ArrayList<>();
        for (Wagon wagon : wagons) {
            if (type.isInstance(wagon)) {
                filteredWagons.add(wagon);
            }
        }
        return filteredWagons;
    }

    private void handleInvalidSelection(String itemType) {
        System.out.println("Such " + itemType + " does not exist in the system");
        System.out.println("1. Select another " + itemType);
        System.out.println("2. Add " + itemType);
        System.out.println("   Press any key to exit");
        Scanner scanner = new Scanner(System.in);
        try {
            int option = scanner.nextInt();
            if (option == 1) {
                switch (itemType) {
                    case "locomotive" -> addTrain();
                    case "station" -> addConnection();
                    case "wagon" -> assignWagonToTrain();
                    case "person" -> addLuggage();
                    case "luggage" -> assignLuggage();
                    case "package" -> assignPackage();
                }
            } else if (option == 2) {
                switch (itemType) {
                    case "locomotive" -> addLocomotive();
                    case "station" -> addStation();
                    case "wagon" -> addWagon();
                    case "person" -> addPerson();
                    case "luggage" -> addLuggage();
                    case "package" -> addPackage();
                }
            }
        } catch (InputMismatchException | CannotAddWagonException ignored) {
        }
    }

    private void handleEmptyList(String itemType) {
        System.out.println("No " + itemType + "s in the system");
        System.out.println("Do you want to add a " + itemType + " to the system?");
        System.out.println("1. Yes");
        System.out.println("   Press any key to exit");
        Scanner scanner = new Scanner(System.in);
        try {
            int option = scanner.nextInt();
            if (option == 1) {
                switch (itemType) {
                    case "locomotive" -> addLocomotive();
                    case "station" -> addStation();
                    case "wagon" -> addWagon();
                    case "train" -> addTrain();
                    case "person" -> addPerson();
                    case "luggage" -> addLuggage();
                    case "package" -> addPackage();
                }
            }
        } catch (InputMismatchException ignored) {
        }
    }
}
