package cc.oobootcamp.parkinglot;

import cc.oobootcamp.parkinglot.exception.ParkingLotFullException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParkingManager {
    private List<ParkingAble> parkingAbles = new ArrayList<>();

    public ParkingManager(ParkingAble... parkingBoys) {
        this.parkingAbles.addAll(Arrays.asList(parkingBoys));
    }

    public Ticket park(Car car) {
        for (ParkingAble parkingAble : parkingAbles) {
            if (!parkingAble.isFull()) {
                return parkingAble.park(car);
            }
        }
        throw new ParkingLotFullException();
    }

    public Car pick(Ticket ticket) {
        return ParkingService.pick(parkingAbles, ticket);
    }
}
