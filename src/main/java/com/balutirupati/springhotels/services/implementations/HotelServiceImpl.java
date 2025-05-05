package com.balutirupati.springhotels.services.implementations;

import com.balutirupati.springhotels.dto.HotelDto;
import com.balutirupati.springhotels.entity.Hotel;
import com.balutirupati.springhotels.exception.ResourceNotFound;
import com.balutirupati.springhotels.repository.HotelRepository;
import com.balutirupati.springhotels.services.interfaces.HotelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

  private final HotelRepository hotelRepository;
  private final ModelMapper modelMapper;

  @Override
  public HotelDto getHotelById(Long id) {
    Hotel hotel = hotelRepository.findById(id).orElseThrow(()->new ResourceNotFound("Hotel not found for id: " + id));
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
    Hotel hotel = hotelRepository.findById(id).orElseThrow(()->new ResourceNotFound("Hotel not found for id: " + id));
    modelMapper.map(hotelDto, hotel);
    hotelRepository.save(hotel);
    return modelMapper.map(hotel, HotelDto.class);
  }
  @Override
  public Boolean deleteHotelById(Long id) {
    Hotel hotel = hotelRepository.findById(id).orElseThrow(()->new ResourceNotFound("Hotel not found for id: " + id));
    hotelRepository.deleteById(hotel.getId());
    return true;
  }
}
