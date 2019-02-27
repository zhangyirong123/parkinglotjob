package cc.oobootcamp.parkinglot;

import cc.oobootcamp.parkinglot.exception.ParkingLotFullException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SuperParkingBoyTest {

    @Test
    void should_return_a_valid_ticket_when_park_a_car_given_a_parking_lot_with_a_space() {
        ParkingLot parkingLot = new ParkingLot(1);
        ParkingAble parkingBoy = new SuperParkingBoy(parkingLot);
        Car car = new Car();

        Ticket ticket = parkingBoy.park(car);

        assertNotNull(ticket);
        assertSame(car, parkingLot.pick(ticket));
    }

    @Test
    void should_park_into_first_when_park_a_car_given_two_parking_lots_which_first_with_more_vacancy_rate() {
        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.park(new Car());

        ParkingLot parkingLot2 = new ParkingLot(3);
        parkingLot2.park(new Car());
        parkingLot2.park(new Car());

        ParkingAble parkingBoy = new SuperParkingBoy(parkingLot1, parkingLot2);
        Car car1 = new Car();

        Ticket ticket1 = parkingBoy.park(car1);

        assertNotNull(ticket1);
        assertSame(car1, parkingLot1.pick(ticket1));
        assertThat(parkingLot2.contains(ticket1)).isFalse();
    }

    @Test
    void should_park_into_second_when_park_a_car_given_two_parking_lots_which_second_with_more_vacancy_rate() {
        ParkingLot parkingLot1 = new ParkingLot(3);
        parkingLot1.park(new Car());
        parkingLot1.park(new Car());

        ParkingLot parkingLot2 = new ParkingLot(2);
        parkingLot2.park(new Car());

        ParkingAble parkingBoy = new SuperParkingBoy(parkingLot1, parkingLot2);
        Car car1 = new Car();

        Ticket ticket1 = parkingBoy.park(car1);

        assertNotNull(ticket1);
        assertSame(car1, parkingLot2.pick(ticket1));
        assertThat(parkingLot1.contains(ticket1)).isFalse();
    }

    @Test
    void should_park_into_first_when_park_a_car_given_two_parking_lots_with_same_vacancy_rate() {
        ParkingLot parkingLot1 = new ParkingLot(3);
        parkingLot1.park(new Car());

        ParkingLot parkingLot2 = new ParkingLot(3);
        parkingLot2.park(new Car());

        ParkingAble parkingBoy = new SuperParkingBoy(parkingLot1, parkingLot2);
        Car car1 = new Car();

        Ticket ticket1 = parkingBoy.park(car1);

        assertNotNull(ticket1);
        assertSame(car1, parkingLot1.pick(ticket1));
        assertThat(parkingLot2.contains(ticket1)).isFalse();
    }

    @Test
    void should_fail_when_park_a_car_given_two_parking_lots_both_full() {
        ParkingLot parkingLot1 = new ParkingLot(1);
        parkingLot1.park(new Car());

        ParkingLot parkingLot2 = new ParkingLot(1);
        parkingLot2.park(new Car());

        ParkingAble parkingBoy = new SuperParkingBoy(parkingLot1, parkingLot2);

        assertThrows(ParkingLotFullException.class, () -> parkingBoy.park(new Car()));
    }


}
