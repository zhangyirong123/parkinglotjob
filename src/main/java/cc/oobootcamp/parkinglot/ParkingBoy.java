package cc.oobootcamp.parkinglot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ParkingBoy implements ParkingAble {
    protected List<ParkingLot> parkingLots = new ArrayList<>();

    public ParkingBoy(ParkingLot... parkingLots) {
        this.parkingLots.addAll(Arrays.asList(parkingLots));
    }

    @Override
    public Car pick(Ticket ticket) {
        return ParkingService.pick(parkingLots, ticket);
    }

    @Override
    public boolean isFull() {
        return parkingLots.stream().allMatch(ParkingAble::isFull);
    }

    @Override
    public boolean contains(Ticket ticket) {
        return parkingLots.stream().anyMatch(parkingLot -> parkingLot.contains(ticket));
    }
}
