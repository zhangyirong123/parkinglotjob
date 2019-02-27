package cc.oobootcamp.parkinglot;

public interface ParkingAble {
    Ticket park(Car car);

    Car pick(Ticket ticket);

    boolean isFull();

    boolean contains(Ticket ticket);
}
