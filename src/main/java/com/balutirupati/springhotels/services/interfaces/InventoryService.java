package com.balutirupati.springhotels.services.interfaces;


import com.balutirupati.springhotels.entity.Room;

public interface InventoryService {

    void initializeRoomForAMonth(Room room);

    void deleteAllInventories(Room room);

}
