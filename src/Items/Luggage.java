package Items;

import Interfaces.Identifiable;

//We can identify luggage's to assign them to people
public class Luggage extends Cargo implements Identifiable {

    private static int counter = 1;
    private int id;
    private Person owner;

    public Luggage(double mass, String serialNumber, Person owner) {
        super(mass, serialNumber);
        this.owner = owner;
        this.id = counter;
        counter++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Luggage" + " Id = " + id + ", Owner = " + owner;
    }
}
