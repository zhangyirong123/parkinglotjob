package cc.oobootcamp.parkinglot;

import cc.oobootcamp.parkinglot.exception.ParkingLotFullException;
import cc.oobootcamp.parkinglot.exception.TicketInvalidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HighLevelParkingBoyTest {

    @Test
    void should_return_a_valid_ticket_when_park_a_car_given_a_parking_lot_with_a_space() {
        ParkingLot parkingLot = new ParkingLot(1);
        HighLevelParkingBoy parkingBoy = new HighLevelParkingBoy(parkingLot);
        Car car = new Car();

        Ticket ticket = parkingBoy.park(car);

        assertNotNull(ticket);
        assertSame(car, parkingLot.pick(ticket));
    }

    @Test
    void should_park_into_first_when_park_multiple_cars_given_two_parking_lots_which_first_with_enough_spaces() {
        ParkingLot parkingLot1 = new ParkingLot(5);
        ParkingLot parkingLot2 = new ParkingLot(2);
        HighLevelParkingBoy parkingBoy = new HighLevelParkingBoy(parkingLot1, parkingLot2);
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();

        Ticket ticket1 = parkingBoy.park(car1);
        Ticket ticket2 = parkingBoy.park(car2);
        Ticket ticket3 = parkingBoy.park(car3);

        assertNotNull(ticket1);
        assertNotNull(ticket2);
        assertNotNull(ticket3);
        assertSame(car1, parkingLot1.pick(ticket1));
        assertSame(car2, parkingLot1.pick(ticket2));
        assertSame(car3, parkingLot1.pick(ticket3));
    }

    @Test
    void should_park_into_both_when_park_multiple_cars_given_two_parking_lots_which_has_some_spaces() {
        ParkingLot parkingLot1 = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot(2);
        HighLevelParkingBoy parkingBoy = new HighLevelParkingBoy(parkingLot1, parkingLot2);
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();

        Ticket ticket1 = parkingBoy.park(car1);
        Ticket ticket2 = parkingBoy.park(car2);
        Ticket ticket3 = parkingBoy.park(car3);

        assertNotNull(ticket1);
        assertNotNull(ticket2);
        assertNotNull(ticket3);

        assertSame(car1, parkingLot1.pick(ticket1));
        assertSame(car2, parkingLot2.pick(ticket2));
        assertSame(car3, parkingLot2.pick(ticket3));
    }

    @Test
    void should_park_into_second_when_park_multiple_cars_given_first_is_full_and_second_has_enough_spaces() {
        ParkingLot parkingLot1 = new ParkingLot(1);
        parkingLot1.park(new Car());

        ParkingLot parkingLot2 = new ParkingLot(2);
        HighLevelParkingBoy parkingBoy = new HighLevelParkingBoy(parkingLot1, parkingLot2);
        Car car1 = new Car();
        Car car2 = new Car();

        Ticket ticket1 = parkingBoy.park(car1);
        Ticket ticket2 = parkingBoy.park(car2);

        assertNotNull(ticket1);
        assertNotNull(ticket2);

        assertSame(car1, parkingLot2.pick(ticket1));
        assertSame(car2, parkingLot2.pick(ticket2));
    }

    @Test
    void should_fail_when_park_car_given_both_are_full() {
        ParkingLot parkingLot1 = new ParkingLot(1);
        parkingLot1.park(new Car());

        ParkingLot parkingLot2 = new ParkingLot(1);
        parkingLot2.park(new Car());

        HighLevelParkingBoy parkingBoy = new HighLevelParkingBoy(parkingLot1, parkingLot2);

        assertThrows(ParkingLotFullException.class, () -> parkingBoy.park(new Car()));
    }

    @Test
    void should_pick_my_car_when_pick_with_my_ticket_given_a_parking_lot_with_my_car_parked() {
        ParkingLot parkingLot = new ParkingLot(1);
        HighLevelParkingBoy parkingBoy = new HighLevelParkingBoy(parkingLot);

        Car myCar = new Car();
        Ticket ticket = parkingBoy.park(myCar);

        assertSame(myCar, parkingBoy.pick(ticket));
    }

    @Test
    void should_pick_my_car_when_pick_with_my_ticket_given_multiple_parking_lot_with_cars_parked() {
        ParkingLot parkingLot1 = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot(2);
        HighLevelParkingBoy parkingBoy = new HighLevelParkingBoy(parkingLot1, parkingLot2);

        parkingBoy.park(new Car());
        Car myCar = new Car();
        Ticket ticket = parkingBoy.park(myCar);

        assertSame(myCar, parkingBoy.pick(ticket));
    }


    @Test
    void should_fail_when_pick_with_null_ticket_given_parking_lot_with_cars_parked_in() {
        ParkingLot parkingLot = new ParkingLot(2);
        HighLevelParkingBoy parkingBoy = new HighLevelParkingBoy(parkingLot);
        parkingBoy.park(new Car());
        assertThrows(TicketInvalidException.class, () -> parkingBoy.pick(null));
    }

    @Test
    void should_fail_when_pick_with_invalid_ticket_given_parking_lot_with_cars_parked_in() {
        ParkingLot parkingLot = new ParkingLot(2);
        HighLevelParkingBoy parkingBoy = new HighLevelParkingBoy(parkingLot);
        parkingBoy.park(new Car());

        assertThrows(TicketInvalidException.class, () -> parkingBoy.pick(new Ticket()));
    }

    @Test
    void should_fail_when_pick_twice_given_parking_lot_with_cars_parked_in() {
        ParkingLot parkingLot = new ParkingLot(2);
        HighLevelParkingBoy parkingBoy = new HighLevelParkingBoy(parkingLot);
        Ticket ticket = parkingBoy.park(new Car());

        parkingBoy.pick(ticket);

        assertThrows(TicketInvalidException.class, () -> parkingBoy.pick(new Ticket()));
    }
}
