package com.example.BackEnd.config;

import com.example.BackEnd.entities.Hotel;
import com.example.BackEnd.repositories.HotelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DemoDataLoader {

    @Bean
    CommandLineRunner loadDemoHotels(HotelRepository hotelRepository) {
        return args -> {
            if (hotelRepository.count() > 0) return;

            List<Hotel> demos = List.of(
                new Hotel(null, "Sunrise Resort", "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800", 4,
                    true, true, true,
                    "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800",
                    "Cape Town", "850", "https://images.unsplash.com/photo-1611892440504-42a792e24d32?w=800",
                    "Standard", "850"),
                new Hotel(null, "Mountain View Lodge", "https://images.unsplash.com/photo-1582719508461-905c673771fd?w=800", 3,
                    true, true, false,
                    "https://images.unsplash.com/photo-1591088398332-8a7791972843?w=800",
                    "Drakensberg", "620", "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=800",
                    "Standard", "620"),
                new Hotel(null, "Ocean Breeze Hotel", "https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?w=800", 5,
                    true, true, true,
                    "https://images.unsplash.com/photo-1596178065887-1198b6148b2b?w=800",
                    "Durban", "1200", "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=800",
                    "Deluxe", "1200")
            );
            hotelRepository.saveAll(demos);
        };
    }
}
