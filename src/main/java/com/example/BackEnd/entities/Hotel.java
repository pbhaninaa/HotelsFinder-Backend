package com.example.BackEnd.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "HotelId")
    private Long id;

    @NotBlank(message = "Hotel name is required")
    @Size(max = 200)
    private String name;
    private String hotelView;
    private int stars;
    private boolean parking;
    private boolean wifi;
    private boolean pool;
    private String inside1;
    private String type;
    private String price;
    private String inside2;
    private String roomType;
    private String roomPrice;

    

}
