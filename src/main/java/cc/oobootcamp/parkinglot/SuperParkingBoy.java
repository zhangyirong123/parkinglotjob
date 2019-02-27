package cc.oobootcamp.parkinglot;

import cc.oobootcamp.parkinglot.exception.ParkingLotFullException;

import java.util.Comparator;

public class SuperParkingBoy extends ParkingBoy {

    public SuperParkingBoy(ParkingLot... parkingLot) {
        super(parkingLot);
    }

    @Override
    public Ticket park(Car car) {
        return parkingLots.stream()
                .max(Comparator.comparingDouble(ParkingLot::vacancyRate))
                .filter(parkingLot1 -> !parkingLot1.isFull())
                .orElseThrow(ParkingLotFullException::new).park(car);
    }

}
