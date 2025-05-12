package com.balutirupati.springhotels.services.implementations;

import com.balutirupati.springhotels.dto.HotelDto;
import com.balutirupati.springhotels.entity.Hotel;
import com.balutirupati.springhotels.entity.Room;
import com.balutirupati.springhotels.exception.ResourceNotFoundException;
import com.balutirupati.springhotels.repository.HotelRepository;
import com.balutirupati.springhotels.repository.RoomRepository;
import com.balutirupati.springhotels.services.interfaces.HotelService;
import com.balutirupati.springhotels.services.interfaces.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

  private final HotelRepository hotelRepository;
  private final InventoryService inventoryService;
  private final RoomRepository roomRepository;
  private final ModelMapper modelMapper;

  @Override
  public HotelDto getHotelById(Long id) {
    Hotel hotel = hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Hotel not found for id: " + id));
    return modelMapper.map(hotel, HotelDto.class);
  }
  @Override
  public HotelDto createNewHotel(HotelDto hotelDto) {
    Hotel hotelEntity = modelMapper.map(hotelDto, Hotel.class);
    hotelEntity.setActive(false);
    Hotel createdHotel = hotelRepository.save(hotelEntity);
    return modelMapper.map(createdHotel, HotelDto.class);
  }
  @Override
  public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
    Hotel hotel = hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Hotel not found for id: " + id));
    modelMapper.map(hotelDto, hotel);
    hotelRepository.save(hotel);
    return modelMapper.map(hotel, HotelDto.class);
  }
  @Override
  @Transactional
  public Boolean deleteHotelById(Long id) {
    Hotel hotel = hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Hotel not found for id: " + id));
    for(Room room: hotel.getRooms()) {
      inventoryService.deleteAllInventories(room);
      roomRepository.deleteById(room.getId());
    }
    hotelRepository.deleteById(hotel.getId());
    return true;
  }
  @Override
  public void activateHotelById(Long id) {
    Hotel hotel = hotelRepository.findById(id)
      .orElseThrow(()->new ResourceNotFoundException("Hotel not found for id: " + id));
    hotel.setActive(true);
    for(Room room: hotel.getRooms()) {
      inventoryService.initializeRoomForAMonth(room);
    }
  }
  @Override
  public List<HotelDto> getAllHotels() {
    return hotelRepository.findAll().stream()
      .map(hotel -> modelMapper.map(hotel, HotelDto.class))
      .toList();
  }
}
