package com.balutirupati.springhotels.services.implementations;

import com.balutirupati.springhotels.dto.RoomDto;
import com.balutirupati.springhotels.entity.Hotel;
import com.balutirupati.springhotels.entity.Room;
import com.balutirupati.springhotels.exception.ResourceNotFoundException;
import com.balutirupati.springhotels.repository.HotelRepository;
import com.balutirupati.springhotels.repository.RoomRepository;
import com.balutirupati.springhotels.services.interfaces.InventoryService;
import com.balutirupati.springhotels.services.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;
  private final HotelRepository hotelRepository;
  private final InventoryService inventoryService;
  private final ModelMapper modelMapper;

  @Override
  public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
    log.info("Creating a new room in hotel with ID: {}", hotelId);
    Hotel hotel = hotelRepository
      .findById(hotelId)
      .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));
    Room room = modelMapper.map(roomDto, Room.class);
    room.setHotel(hotel);
    room = roomRepository.save(room);

    if (hotel.getActive()) {
      inventoryService.initializeRoomForAMonth(room);
    }

    return modelMapper.map(room, RoomDto.class);
  }

  @Override
  public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
    log.info("Getting all rooms in hotel with ID: {}", hotelId);
    Hotel hotel = hotelRepository
      .findById(hotelId)
      .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));

    return hotel.getRooms()
      .stream()
      .map((element) -> modelMapper.map(element, RoomDto.class))
      .collect(Collectors.toList());
  }

  @Override
  public RoomDto getRoomById(Long roomId) {
    log.info("Getting the room with ID: {}", roomId);
    Room room = roomRepository
      .findById(roomId)
      .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));
    return modelMapper.map(room, RoomDto.class);
  }

  @Transactional
  @Override
  public void deleteRoomById(Long roomId) {
    log.info("Deleting the room with ID: {}", roomId);
    Room room = roomRepository
      .findById(roomId)
      .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));
    inventoryService.deleteAllInventories(room);
    roomRepository.deleteById(roomId);
  }
}
