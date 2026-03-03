package com.example.BackEnd.controllers;

import com.example.BackEnd.entities.Booking;
import com.example.BackEnd.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:4201"})
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/save")
    public ResponseEntity<Booking> createBooking(@RequestBody @Valid Booking booking) {
        return ResponseEntity.ok(bookingService.saveBooking(booking));
    }

    @GetMapping("/getAll")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable Long userId) {
        return bookingService.getBookingsByUserId(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody @Valid Booking booking) {
        return bookingService.updateBooking(id, booking)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        return bookingService.deleteBooking(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
