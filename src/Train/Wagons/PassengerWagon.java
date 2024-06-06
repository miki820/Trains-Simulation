package Train.Wagons;

import Exceptions.DuplicateItemException;
import Interfaces.Electricable;

import java.util.ArrayList;
import java.util.List;

public class PassengerWagon<Person> extends Wagon implements Electricable {

    private int numberOfSeats;
    private final List<Person> passengers;
    private double electricityPower;

    public PassengerWagon(double netWeight, double grossWeight, String sender, boolean breakSystem, int numberOfSeats, double electricityPower) {
        super(netWeight, grossWeight, sender, breakSystem);
        this.numberOfSeats = numberOfSeats;
        this.electricityPower = electricityPower;
        passengers = new ArrayList<>();
    }

    public void addPassenger(Person person) throws DuplicateItemException {
        if (passengers.contains(person) && !checkFull()) {
            throw new DuplicateItemException();
        } else {
            passengers.add(person);
            numberOfSeats--;
        }
    }

    @Override
    public void charging() {
        System.out.println("Passenger wagon was connected to power");
    }

    @Override
    public double getElectricalPower() {
        return numberOfSeats * 0.7 + electricityPower;
    }

    public boolean checkFull() {
        return numberOfSeats == 0;
    }

    public int getRemainingNumberOfSeats() {
        return numberOfSeats;
    }

    public List<Person> getPassengerList() {
        return passengers;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public List<Person> getPassengers() {
        return passengers;
    }

    public double getElectricityPower() {
        return electricityPower;
    }

    public void setElectricityPower(double electricityPower) {
        this.electricityPower = electricityPower;
    }

    @Override
    public String toString() {
        return "Passenger: " + super.toString() +
                ", Number Of Seats = " + numberOfSeats +
                ", Passengers = " + passengers;
    }
}
