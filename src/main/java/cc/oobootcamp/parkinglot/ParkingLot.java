package cc.oobootcamp.parkinglot;

import cc.oobootcamp.parkinglot.exception.ParkingLotFullException;
import cc.oobootcamp.parkinglot.exception.TicketInvalidException;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot implements ParkingAble {
    private int space;
    private Map<Ticket, Car> parkedCars = new HashMap<>();

    public ParkingLot(int space) {
        this.space = space;
    }

    public Ticket park(Car car) {
        if (isFull()) {
            throw new ParkingLotFullException();
        }
        Ticket ticket = new Ticket();
        parkedCars.put(ticket, car);
        return ticket;
    }

    public boolean isFull() {
        return parkedCars.size() >= space;
    }

    public Car pick(Ticket ticket) {
        if (parkedCars.containsKey(ticket)) {
            return parkedCars.remove(ticket);
        }
        throw new TicketInvalidException();
    }

    public boolean contains(Ticket ticket) {
        return parkedCars.containsKey(ticket);
    }

    public int availableSpace() {
        return space - parkedCars.size();
    }

    public double vacancyRate() {
        return availableSpace() / (double) space;
    }
}
