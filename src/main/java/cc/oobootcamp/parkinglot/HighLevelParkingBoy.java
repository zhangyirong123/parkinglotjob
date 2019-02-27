package cc.oobootcamp.parkinglot;

import cc.oobootcamp.parkinglot.exception.ParkingLotFullException;

public class HighLevelParkingBoy extends ParkingBoy {

    public HighLevelParkingBoy(ParkingLot... parkingLots) {
        super(parkingLots);
    }

    @Override
    public Ticket park(Car car) {
        for (ParkingLot parkingLot : parkingLots) {
            if (!parkingLot.isFull()) {
                return parkingLot.park(car);
            }
        }
        throw new ParkingLotFullException();
    }

}
