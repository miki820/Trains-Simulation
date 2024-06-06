package Items;

import Interfaces.Identifiable;

//We can identify packages to assign them to mail wagons
public class Package extends Cargo implements Identifiable {

    private static int counter = 1;
    private int id;
    private boolean isPriority;
    private int isBox;

    public Package(double mass, String serialNumber, boolean isPriority, int isBox) {
        super(mass, serialNumber);
        this.isPriority = isPriority;
        this.isBox = isBox;
        this.id = counter;
        counter++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPriority() {
        return isPriority;
    }

    public void setPriority(boolean priority) {
        isPriority = priority;
    }

    public int getIsBox() {
        return isBox;
    }

    public void setIsBox(int isBox) {
        this.isBox = isBox;
    }

    @Override
    public String toString() {
        return "Package:" + " Id = " + id +
                ", Priority = " + isPriority + ", Box = " + isBox;
    }
}
