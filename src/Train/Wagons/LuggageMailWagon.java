package Train.Wagons;

import Exceptions.DuplicateItemException;

import java.util.ArrayList;
import java.util.List;

public class LuggageMailWagon<Luggage> extends Wagon {

    private int luggageCapacity;
    private final List<Luggage> luggages;

    public LuggageMailWagon(double netWeight, double grossWeight, String sender, boolean breakSystem, int luggageCapacity) {
        super(netWeight, grossWeight, sender, breakSystem);
        this.luggageCapacity = luggageCapacity;
        luggages = new ArrayList<>();
    }

    public void addLuggage(Luggage luggage) throws DuplicateItemException {
        if (luggages.contains(luggage) && !checkFull()) {
            throw new DuplicateItemException();
        } else {
            luggages.add(luggage);
            luggageCapacity--;
        }
    }

    public boolean checkFull() {
        return luggageCapacity == 0;
    }

    public int getRemainingLuggageCapacity() {
        return luggageCapacity;
    }

    public List<Luggage> getLuggageList() {
        return luggages;
    }

    @Override
    public String toString() {
        return "Luggage-Mail " + super.toString() +
                ", Luggage Capacity = " + luggageCapacity +
                ", Luggages = " + luggages;
    }
}



