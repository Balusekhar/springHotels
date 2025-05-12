package com.balutirupati.springhotels.repository;


import com.balutirupati.springhotels.entity.Inventory;
import com.balutirupati.springhotels.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    void deleteByRoom(Room room);
}
