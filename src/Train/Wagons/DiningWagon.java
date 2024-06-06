package Train.Wagons;

import Interfaces.Electricable;

public class DiningWagon extends Wagon implements Electricable {

    private int numberOfSeats;
    private String menu;
    private double power;

    public DiningWagon(double netWeight, double grossWeight, String sender, boolean breakSystem, int numberOfSeats, String menu, double power) {
        super(netWeight, grossWeight, sender, breakSystem);
        this.numberOfSeats = numberOfSeats;
        this.menu = menu;
        this.power = power;
    }

    public void seatClient() {
        if (numberOfSeats == 0) {
            System.out.println("No seats left");
        } else {
            numberOfSeats--;
        }
    }

    public String getMenu() {
        return menu;
    }

    public void changeMenu(String newMenu) {
        this.menu = newMenu;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public void charging() {
        System.out.println("Dining wagon was connected to electricity");
    }

    @Override
    public double getElectricalPower() {
        return numberOfSeats * 0.7 + power;
    }

    @Override
    public String toString() {
        return "Dining " + super.toString() +
                ", Number of Seats = " + numberOfSeats +
                ", Menu = " + menu;
    }
}
