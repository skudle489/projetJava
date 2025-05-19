package test;

import business.ReservationManager;
import exceptions.CustomerCreationException;
import exceptions.IsRoomReservedException;
import exceptions.ReservationCreationException;
import model.Reservation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationManagerTest {
    private ReservationManager reservationManager;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        reservationManager = new ReservationManager();
    }

    @org.junit.jupiter.api.Test
    void isRoomReserved() throws ReservationCreationException, CustomerCreationException, IsRoomReservedException {
        Reservation reservation = new Reservation(LocalDate.parse("2025-06-01"), LocalDate.parse("2025-06-05"), "jean.dupont@email.fr", 101, 1);
        assertTrue(reservationManager.isRoomReserved(reservation.getBedroom(), reservation.getHotel(), reservation.getStartDate()));
    }


    @Test
    void totalPrice() {
        assertEquals(300, reservationManager.totalPrice(5, 60));
        assertEquals(100, reservationManager.totalPrice(1, 100));
    }
}