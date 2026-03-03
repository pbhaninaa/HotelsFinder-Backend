package com.example.BackEnd.services;

import com.example.BackEnd.entities.Booking;
import com.example.BackEnd.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository repository;
    @Autowired
    private WhatsAppService whatsAppService;

    public Booking saveBooking(Booking booking) {
        validateBookingDates(booking.getCheckIn(), booking.getCheckOut());
        Booking saved = repository.save(booking);
        whatsAppService.sendBookingConfirmation(saved);
        return saved;
    }

    public Optional<Booking> getBookingById(Long id) {
        return repository.findById(id);
    }

    public List<Booking> getAllBookings() {
        return repository.findAll();
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public Optional<Booking> updateBooking(Long id, Booking updates) {
        validateBookingDates(updates.getCheckIn(), updates.getCheckOut());
        return repository.findById(id)
                .map(existing -> {
                    existing.setHotelId(updates.getHotelId());
                    existing.setHotelName(updates.getHotelName());
                    existing.setUserEmail(updates.getUserEmail());
                    existing.setUserName(updates.getUserName());
                    existing.setUserPhone(updates.getUserPhone());
                    existing.setCheckIn(updates.getCheckIn());
                    existing.setCheckOut(updates.getCheckOut());
                    existing.setOccupants(updates.getOccupants());
                    existing.setRoomPrice(updates.getRoomPrice());
                    return repository.save(existing);
                });
    }

    public boolean deleteBooking(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    private void validateBookingDates(String checkIn, String checkOut) {
        if (checkIn != null && checkOut != null && checkIn.compareTo(checkOut) >= 0) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
    }
}
