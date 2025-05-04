package com.balutirupati.springhotels.repository;


import com.balutirupati.springhotels.entity.Inventory;
import com.balutirupati.springhotels.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    void deleteByDateAfterAndRoom(LocalDate date, Room room);
}
