package cc.oobootcamp.parkinglot;

import cc.oobootcamp.parkinglot.exception.ParkingLotFullException;
import cc.oobootcamp.parkinglot.exception.TicketInvalidException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    @Test
    void should_return_a_valid_ticket_when_park_a_car_given_parking_lot_with_space() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car();

        Ticket ticket = parkingLot.park(car);

        assertThat(ticket).isNotNull();
    }

    @Test
    void should_return_multiple_valid_ticket_when_park_multiple_car_given_parking_lot_with_enough_space() {
        ParkingLot parkingLot = new ParkingLot(5);

        Ticket ticket = parkingLot.park(new Car());
        Ticket ticket1 = parkingLot.park(new Car());

        assertThat(ticket).isNotNull();
        assertThat(ticket1).isNotNull();
        assertNotSame(ticket, ticket1);
    }

    @Test
    void should_fail_when_park_car_given_parking_lot_full() {
        ParkingLot parkingLot = new ParkingLot(0);

        assertThrows(ParkingLotFullException.class, () -> parkingLot.park(new Car()));
    }

    @Test
    void should_pick_my_car_when_pick_given_parking_lot_with_only_my_car_parked_in() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car myCar = new Car();

        Ticket ticket = parkingLot.park(myCar);

        Car pickedCar = parkingLot.pick(ticket);

        assertSame(myCar, pickedCar);
    }

    @Test
    void should_pick_my_car_when_pick_given_parking_lot_with_multiple_cars_parked_in() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car myCar = new Car();
        Car otherCar = new Car();

        Ticket myTicket = parkingLot.park(myCar);
        parkingLot.park(otherCar);

        Car pickedCar = parkingLot.pick(myTicket);

        assertSame(myCar, pickedCar);
        assertNotSame(otherCar, pickedCar);
    }

    @Test
    void should_fail_when_pick_with_invalid_ticket_given_parking_lot_with_cars_parked_in() {
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.park(new Car());

        assertThrows(TicketInvalidException.class, () -> parkingLot.pick(new Ticket()));
    }

    @Test
    void should_fail_when_pick_with_null_ticket_given_parking_lot_with_cars_parked_in() {
        ParkingLot parkingLot = new ParkingLot(2);
        parkingLot.park(new Car());

        assertThrows(TicketInvalidException.class, () -> parkingLot.pick(null));
    }

    @Test
    void should_fail_when_pick_twice_given_parking_lot_with_cars_parked_in() {
        ParkingLot parkingLot = new ParkingLot(2);
        Ticket ticket = parkingLot.park(new Car());
        parkingLot.pick(ticket);

        assertThrows(TicketInvalidException.class, () -> parkingLot.pick(ticket));
    }

}
