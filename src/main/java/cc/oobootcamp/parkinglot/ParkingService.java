package cc.oobootcamp.parkinglot;

import cc.oobootcamp.parkinglot.exception.TicketInvalidException;

import java.util.List;

public class ParkingService {
    public static Car pick(List<? extends ParkingAble> parkingAbles, Ticket ticket) {
        for (ParkingAble parkingLot : parkingAbles) {
            if (parkingLot.contains(ticket)) {
                return parkingLot.pick(ticket);
            }
        }
        throw new TicketInvalidException();
    }
}
