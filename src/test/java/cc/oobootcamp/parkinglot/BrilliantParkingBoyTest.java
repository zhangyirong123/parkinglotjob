package cc.oobootcamp.parkinglot;

import cc.oobootcamp.parkinglot.exception.ParkingLotFullException;
import cc.oobootcamp.parkinglot.exception.TicketInvalidException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BrilliantParkingBoyTest {

    @Test
    void should_return_a_valid_ticket_when_park_a_car_given_a_parking_lot_with_a_space() {
        ParkingLot parkingLot = new ParkingLot(1);
        ParkingAble parkingBoy = new BrilliantParkingBoy(parkingLot);
        Car car = new Car();

        Ticket ticket = parkingBoy.park(car);

        assertNotNull(ticket);
        assertSame(car, parkingLot.pick(ticket));
    }

    @Test
    void should_park_into_first_when_park_a_car_given_two_parking_lots_which_first_with_more_spaces() {
        ParkingLot parkingLot1 = new ParkingLot(5);
        ParkingLot parkingLot2 = new ParkingLot(5);
        parkingLot2.park(new Car());

        ParkingAble parkingBoy = new BrilliantParkingBoy(parkingLot1, parkingLot2);
        Car car1 = new Car();

        Ticket ticket1 = parkingBoy.park(car1);

        assertNotNull(ticket1);
        assertSame(car1, parkingLot1.pick(ticket1));
        assertThat(parkingLot2.contains(ticket1)).isFalse();
    }

    @Test
    void should_park_into_second_when_park_a_car_given_two_parking_lots_which_second_with_more_spaces() {
        ParkingLot parkingLot1 = new ParkingLot(5);
        parkingLot1.park(new Car());

        ParkingLot parkingLot2 = new ParkingLot(5);

        ParkingAble parkingBoy = new BrilliantParkingBoy(parkingLot1, parkingLot2);
        Car car = new Car();

        Ticket ticket = parkingBoy.park(car);

        assertNotNull(ticket);
        assertSame(car, parkingLot2.pick(ticket));
        assertThat(parkingLot1.contains(ticket)).isFalse();
    }

    @Test
    void should_park_into_both_exchanged_when_park_multiple_cars_given_two_parking_lots_with_same_spaces() {
        ParkingLot parkingLot1 = new ParkingLot(3);
        ParkingLot parkingLot2 = new ParkingLot(3);

        ParkingAble parkingBoy = new BrilliantParkingBoy(parkingLot1, parkingLot2);
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        Car car4 = new Car();

        Ticket ticket1 = parkingBoy.park(car1);
        Ticket ticket2 = parkingBoy.park(car2);
        Ticket ticket3 = parkingBoy.park(car3);
        Ticket ticket4 = parkingBoy.park(car4);

        assertSame(car1, parkingLot1.pick(ticket1));
        assertSame(car2, parkingLot2.pick(ticket2));
        assertSame(car3, parkingLot1.pick(ticket3));
        assertSame(car4, parkingLot2.pick(ticket4));
    }

    @Test
    void should_fail_when_park_a_car_given_two_parking_lots_both_full() {
        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.park(new Car());
        parkingLot1.park(new Car());

        ParkingLot parkingLot2 = new ParkingLot(2);
        parkingLot2.park(new Car());
        parkingLot2.park(new Car());

        ParkingAble parkingBoy = new BrilliantParkingBoy(parkingLot1, parkingLot2);

        assertThrows(ParkingLotFullException.class, () -> parkingBoy.park(new Car()));
    }

    @Test
    void should_pick_my_car_when_pick_with_my_ticket_given_a_parking_lot_with_my_car_parked() {
        ParkingLot parkingLot = new ParkingLot(1);
        ParkingAble parkingBoy = new BrilliantParkingBoy(parkingLot);

        Car myCar = new Car();
        Ticket ticket = parkingBoy.park(myCar);

        assertSame(myCar, parkingBoy.pick(ticket));
    }

    @Test
    void should_pick_my_car_from_first_when_pick_with_my_ticket_given_multiple_parking_lot_with_cars_parked() {
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        ParkingAble parkingBoy = new BrilliantParkingBoy(parkingLot1, parkingLot2);

        Car myCar = new Car();
        Ticket ticket = parkingBoy.park(myCar);

        parkingBoy.park(new Car());

        assertSame(myCar, parkingBoy.pick(ticket));
    }

    @Test
    void should_pick_my_car_from_second_when_pick_with_my_ticket_given_multiple_parking_lot_with_cars_parked() {
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        ParkingAble parkingBoy = new BrilliantParkingBoy(parkingLot1, parkingLot2);
        parkingBoy.park(new Car());

        Car myCar = new Car();
        Ticket ticket = parkingBoy.park(myCar);

        assertSame(myCar, parkingBoy.pick(ticket));
    }


    @Test
    void should_fail_when_pick_with_null_ticket_given_parking_lot_with_cars_parked_in() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingAble parkingBoy = new BrilliantParkingBoy(parkingLot);
        parkingBoy.park(new Car());
        assertThrows(TicketInvalidException.class, () -> parkingBoy.pick(null));
    }

    @Test
    void should_fail_when_pick_with_invalid_ticket_given_parking_lot_with_cars_parked_in() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingAble parkingBoy = new BrilliantParkingBoy(parkingLot);
        parkingBoy.park(new Car());

        assertThrows(TicketInvalidException.class, () -> parkingBoy.pick(new Ticket()));
    }

    @Test
    void should_fail_when_pick_twice_given_parking_lot_with_cars_parked_in() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingAble parkingBoy = new BrilliantParkingBoy(parkingLot);
        Ticket ticket = parkingBoy.park(new Car());

        parkingBoy.pick(ticket);

        assertThrows(TicketInvalidException.class, () -> parkingBoy.pick(ticket));
    }


}
