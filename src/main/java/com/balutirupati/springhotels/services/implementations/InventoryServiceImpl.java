package com.balutirupati.springhotels.services.implementations;

import com.balutirupati.springhotels.entity.Inventory;
import com.balutirupati.springhotels.entity.Room;
import com.balutirupati.springhotels.repository.InventoryRepository;
import com.balutirupati.springhotels.services.interfaces.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public void initializeRoomForAMonth(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusWeeks(1);
        for (; !today.isAfter(endDate); today=today.plusDays(1)) {
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteAllInventories(Room room) {
//        LocalDate today = LocalDate.now();
        inventoryRepository.deleteByRoom(room);
    }
}
