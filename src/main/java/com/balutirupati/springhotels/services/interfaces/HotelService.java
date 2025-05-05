package com.balutirupati.springhotels.services.interfaces;

import com.balutirupati.springhotels.dto.HotelDto;

public interface HotelService {

  HotelDto getHotelById(Long id);
  HotelDto createNewHotel(HotelDto hotelDto);
  HotelDto updateHotelById(Long id, HotelDto hotelDto);
  Boolean deleteHotelById(Long id);
}

