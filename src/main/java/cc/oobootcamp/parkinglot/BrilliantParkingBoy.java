package cc.oobootcamp.parkinglot;

import cc.oobootcamp.parkinglot.exception.ParkingLotFullException;

import java.util.Comparator;

public class BrilliantParkingBoy extends ParkingBoy {

    public BrilliantParkingBoy(ParkingLot... parkingLots) {
        super(parkingLots);
    }

    @Override
    public Ticket park(Car car) {
        return parkingLots.stream()
                .max(Comparator.comparingInt(ParkingLot::availableSpace))
                .filter(parkingLot1 -> !parkingLot1.isFull())
                .orElseThrow(ParkingLotFullException::new).park(car);
    }
}
