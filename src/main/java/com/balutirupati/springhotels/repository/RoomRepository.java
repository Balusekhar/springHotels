package com.balutirupati.springhotels.repository;

import com.balutirupati.springhotels.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
