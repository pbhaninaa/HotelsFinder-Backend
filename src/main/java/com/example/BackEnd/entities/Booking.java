package com.example.BackEnd.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BookingId")
    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Hotel ID is required")
    private Long hotelId;

    @NotBlank(message = "Hotel name is required")
    private String hotelName;

    @NotBlank(message = "Email is required")
    private String userEmail;

    @NotBlank(message = "Guest name is required")
    private String userName;

    private String userPhone;

    @NotBlank(message = "Check-in date is required")
    private String checkIn;

    @NotBlank(message = "Check-out date is required")
    private String checkOut;

    @NotNull(message = "Number of guests is required")
    @Min(value = 1, message = "At least 1 guest required")
    private Integer occupants;

    @NotBlank(message = "Room price is required")
    private String roomPrice;
}
