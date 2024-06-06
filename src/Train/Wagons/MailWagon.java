package Train.Wagons;

import Exceptions.DuplicateItemException;
import Interfaces.Electricable;

import java.util.ArrayList;
import java.util.List;

public class MailWagon<Packages> extends Wagon implements Electricable {

    private int numberOfFreeCompartments;
    private int numberOfListContainers;
    private final List<Packages> packages;
    private double electricityPower;

    public MailWagon(double netWeight, double grossWeight, String sender, boolean breakSystem, int numberOfFreeCompartments, int numberOfListContainers, double electricityPower) {
        super(netWeight, grossWeight, sender, breakSystem);
        this.numberOfFreeCompartments = numberOfFreeCompartments;
        this.numberOfListContainers = numberOfListContainers;
        this.electricityPower = electricityPower;
        packages = new ArrayList<>();
    }

    public void addPackage(Packages packages) throws DuplicateItemException {
        if (this.packages.contains(packages)) {
            throw new DuplicateItemException();
        } else {
            this.packages.add(packages);
            numberOfFreeCompartments--;
        }
    }

    public void movePackageToLists() {
        numberOfListContainers++;
        numberOfFreeCompartments--;
    }

    @Override
    public void charging() {
        System.out.println("Wagon is charging...");
    }

    @Override
    public double getElectricalPower() {
        return numberOfListContainers * 0.3 + electricityPower;
    }

    public int getNumberOfListContainers() {
        return numberOfListContainers;
    }

    public int getNumberOfFreeCompartments() {
        return numberOfFreeCompartments;
    }

    public List<Packages> getPackageList() {
        return packages;
    }

    public void setNumberOfFreeCompartments(int numberOfFreeCompartments) {
        this.numberOfFreeCompartments = numberOfFreeCompartments;
    }

    public void setNumberOfListContainers(int numberOfListContainers) {
        this.numberOfListContainers = numberOfListContainers;
    }

    public List<Packages> getPackages() {
        return packages;
    }

    public double getElectricityPower() {
        return electricityPower;
    }

    public void setElectricityPower(double electricityPower) {
        this.electricityPower = electricityPower;
    }

    @Override
    public String toString() {
        return "Mail " + super.toString() +
                ", Free Compartments =  " + numberOfFreeCompartments +
                ", Mail Containers =  " + numberOfListContainers +
                ", Packages = " + packages;
    }

}




