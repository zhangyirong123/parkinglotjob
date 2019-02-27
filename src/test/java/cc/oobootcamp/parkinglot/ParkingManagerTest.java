package cc.oobootcamp.parkinglot;

import cc.oobootcamp.parkinglot.exception.ParkingLotFullException;
import cc.oobootcamp.parkinglot.exception.TicketInvalidException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParkingManagerTest {

    @Test
    void should_return_ticket_when_park_given_parking_lot_has_space_and_no_parking_boy() {
        ParkingAble parkingLot = new ParkingLot(1);
        ParkingManager parkingManager = new ParkingManager(parkingLot);
        Car car = new Car();
        Ticket ticket = parkingManager.park(car);

        assertSame(car, parkingLot.pick(ticket));
    }

    @Test
    void should_throw_exception_when_park_given_parking_lot_is_full_and_no_parking_boy() {
        ParkingAble parkingLot = new ParkingLot(1);
        parkingLot.park(new Car());

        ParkingManager parkingManager = new ParkingManager(parkingLot);

        assertThrows(ParkingLotFullException.class, () -> parkingManager.park(new Car()));
    }

    @Test
    void should_park_successfully_when_park_given_parking_boy_managed_parking_lot_has_space() {
        ParkingLot parkingLot = new ParkingLot(1);
        ParkingAble parkingBoy = new SuperParkingBoy(parkingLot);

        ParkingManager parkingManager = new ParkingManager(parkingBoy);
        Car car = new Car();
        Ticket ticket = parkingManager.park(car);
        assertSame(car, parkingLot.pick(ticket));
    }

    @Test
    void should_park_successfully_when_park_given_second_parking_boy_managed_parking_lot_has_space_and_first_is_full() {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park(new Car());

        ParkingLot parkingLot2 = new ParkingLot(1);

        ParkingAble superParkingBoy = new SuperParkingBoy(parkingLot);
        ParkingAble smartParkingBoy = new BrilliantParkingBoy(parkingLot2);

        ParkingManager parkingManager = new ParkingManager(superParkingBoy, smartParkingBoy);
        Car car = new Car();
        Ticket ticket = parkingManager.park(car);
        assertSame(car, parkingLot2.pick(ticket));
    }

    @Test
    void should_throw_exception_when_park_given_both_parking_lots_are_full_managed_by_parking_boy() {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park(new Car());

        ParkingLot parkingLot2 = new ParkingLot(1);
        parkingLot2.park(new Car());

        ParkingAble superParkingBoy = new SuperParkingBoy(parkingLot);
        ParkingAble smartParkingBoy = new BrilliantParkingBoy(parkingLot2);

        ParkingManager parkingManager = new ParkingManager(superParkingBoy, smartParkingBoy);

        assertThrows(ParkingLotFullException.class, () -> parkingManager.park(new Car()));
    }

    @Test
    void should_pick_my_car_when_pick_given_a_parking_lot_contains_my_car() {
        ParkingAble parkingLot = new ParkingLot(2);
        parkingLot.park(new Car());
        Car myCar = new Car();
        Ticket ticket = parkingLot.park(myCar);

        ParkingManager parkingManager = new ParkingManager(parkingLot);

        assertSame(myCar, parkingManager.pick(ticket));
    }

    @Test
    void should_pick_my_car_when_pick_given_multiple_parking_lots_contains_my_car() {
        ParkingAble parkingLot = new ParkingLot(2);
        ParkingAble parkingLot2 = new ParkingLot(2);
        parkingLot.park(new Car());
        parkingLot2.park(new Car());

        Car myCar = new Car();

        Ticket ticket = parkingLot2.park(myCar);

        ParkingManager parkingManager = new ParkingManager(parkingLot, parkingLot2);

        assertSame(myCar, parkingManager.pick(ticket));
    }

    @Test
    void should_pick_my_car_when_pick_given_a_parking_boy_whose_one_parking_lot_contains_my_car() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car myCar = new Car();
        parkingLot.park(new Car());
        Ticket ticket = parkingLot.park(myCar);

        ParkingAble superParkingBoy = new SuperParkingBoy(parkingLot);

        ParkingManager parkingManager = new ParkingManager(superParkingBoy);

        assertSame(myCar, parkingManager.pick(ticket));
    }

    @Test
    void should_pick_my_car_when_pick_given_multiple_parking_boys_whose_parking_lots_contains_my_car() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        parkingLot.park(new Car());

        Car myCar = new Car();
        Ticket ticket = parkingLot2.park(myCar);

        ParkingAble superParkingBoy = new SuperParkingBoy(parkingLot);
        ParkingAble smartParkingBoy = new BrilliantParkingBoy(parkingLot2);


        ParkingManager parkingManager = new ParkingManager(superParkingBoy, smartParkingBoy);

        assertSame(myCar, parkingManager.pick(ticket));
    }

    @Test
    void should_throw_exception_when_pick_with_invalid_ticket_given_parking_lot_contains_my_car() {
        ParkingAble parkingLot = new ParkingLot(1);
        parkingLot.park(new Car());

        ParkingManager parkingManager = new ParkingManager(parkingLot);

        assertThrows(TicketInvalidException.class, () -> parkingManager.pick(new Ticket()));
        assertThrows(TicketInvalidException.class, () -> parkingManager.pick(null));
    }

    @Test
    void should_throw_exception_when_pick_with_invalid_ticket_given_parking_boy_whose_parking_lot_contains_my_car() {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.park(new Car());
        ParkingAble superParkingBoy = new SuperParkingBoy(parkingLot);

        ParkingManager parkingManager = new ParkingManager(superParkingBoy);

        assertThrows(TicketInvalidException.class, () -> parkingManager.pick(new Ticket()));
        assertThrows(TicketInvalidException.class, () -> parkingManager.pick(null));
    }

}
