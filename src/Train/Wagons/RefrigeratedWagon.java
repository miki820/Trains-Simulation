package Train.Wagons;

import Items.BasicCargo;
import Interfaces.Electricable;
import Items.Cargo;

import java.util.Arrays;

public class RefrigeratedWagon extends BasicMaterialsWagon<BasicCargo> implements Electricable {

    private double temperature;
    private Cargo[] cargos;
    private double electricityPower;


    public RefrigeratedWagon(double netWeight, double grossWeight, String sender, boolean breakSystem, double height, double width, double length, double load, double temperature, double electricityPower) {
        super(netWeight, grossWeight, sender, breakSystem, height, width, length, load);
        this.temperature = temperature;
        this.electricityPower = electricityPower;
    }

    public boolean czyLadunekZnajdujeSiewWagonie(Cargo cargo) {
        for (Cargo c : cargos) {
            if (c.equals(cargo)) {
                return true;
            }
        }
        return false;
    }

    public void lowerTemperature() {
        if (temperature > 2) {
            temperature = 2;
        } else {
            System.out.println("Temperature is good and equals: " + this.temperature);
        }
    }

    @Override
    public void charging() {
        System.out.println("Refrigerator Wagon was connected to electricity");
    }

    @Override
    public double getElectricalPower() {
        return 0.5 + electricityPower;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Cargo[] getCargos() {
        return cargos;
    }

    public void setCargos(Cargo[] cargos) {
        this.cargos = cargos;
    }

    public double getElectricityPower() {
        return electricityPower;
    }

    public void setElectricityPower(double electricityPower) {
        this.electricityPower = electricityPower;
    }

    @Override
    public String toString() {
        return "Refrigerator: " + super.toString() +
                ", Temperature = " + temperature +
                ", Cargos = " + Arrays.toString(cargos);
    }
}
