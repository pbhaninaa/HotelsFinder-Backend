package com.example.BackEnd.services;

import com.example.BackEnd.entities.Hotel;
import com.example.BackEnd.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    HotelRepository repository;

    public Hotel saveHotel(Hotel hotel) {
        try {
            return repository.save(hotel);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save hotel: " + e.getMessage());
        }
    }

    public Optional<Hotel> getHotelById(Long id) {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve hotel: " + e.getMessage());
        }
    }

    public List<Hotel> getAllHotels() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all hotels: " + e.getMessage());
        }
    }
    public String deleteHotelById(Long id){
        try {
            Optional<Hotel> optionalHotel = repository.findById(id);
            if (optionalHotel.isPresent()) {
                Hotel hotel = optionalHotel.get();
                String name = hotel.getName();
                repository.deleteById(id);
                return name + " is successfully deleted";
            } else {
                return "Hotel with ID " + id + " not found";
            }
        } catch (Exception e) {
            return "An error occurred while deleting the hotel: " + e.getMessage();
        }
    }
    public String deleteAllHotels(){
        try {
            repository.deleteAll();
            return "All hotels have been deleted";
        } catch (Exception e) {
            return "An error occurred while deleting all hotels: " + e.getMessage();
        }
    }
}
