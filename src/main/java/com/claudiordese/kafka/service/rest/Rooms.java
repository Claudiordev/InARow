package com.claudiordese.kafka.service.rest;

import com.claudiordese.kafka.model.Room;
import com.claudiordese.kafka.model.RoomsStatus;
import com.claudiordese.kafka.service.kafka.producer.RoomProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/kafka")
public class Rooms {

    private final RoomProducer roomProducer;

    public Rooms(RoomProducer roomProducer) {
        this.roomProducer = roomProducer;
    }

    @PostMapping("/send")
    public ResponseEntity<String> addRoom(@RequestBody Room room) {
        RoomsStatus roomStatus = new RoomsStatus();
        List<Room> list = new ArrayList<>();
        list.add(room);

        roomStatus.setRooms(list);






        roomStatus.setTimestamp(Instant.now().toString());
        roomProducer.sendMessage(roomStatus);

        return ResponseEntity.ok(roomStatus.toString());
    }
}
