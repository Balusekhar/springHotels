package com.balutirupati.springhotels.controllers;

import com.balutirupati.springhotels.dto.HotelDto;
import com.balutirupati.springhotels.services.interfaces.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/hotel")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

  private final HotelService hotelService;

  @PostMapping
  public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto){
    log.info("HotelDto: {}", hotelDto);
    HotelDto createdHotel = hotelService.createNewHotel(hotelDto);
    log.info("HotelDto created: {}", createdHotel);
    return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);
  }

  @GetMapping("/{hotelId}")
  public ResponseEntity<HotelDto> getHotelById(@PathVariable  Long hotelId){
    return new ResponseEntity<>(hotelService.getHotelById(hotelId), HttpStatus.OK);
  }

  @PutMapping("/{hotelId}")
  public ResponseEntity<HotelDto> updateHotelById(@PathVariable  Long hotelId, @RequestBody HotelDto hotelDto){
    HotelDto updatedHotel = hotelService.updateHotelById(hotelId, hotelDto);
    return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
  }

  @DeleteMapping("/{hotelId}")
  public ResponseEntity<Boolean> deleteHotelById(@PathVariable  Long hotelId){
    Boolean deleted = hotelService.deleteHotelById(hotelId);
    return new ResponseEntity<>(deleted, HttpStatus.OK);
  }
}
