package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.WaitingRoomDTO;
import com.example.dentalclinicmanagementsystem.entity.Patient;
import com.example.dentalclinicmanagementsystem.service.WaitingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/waiting_room")
public class WaitingRoomController {

    @Autowired
    private WaitingRoomService waitingRoomService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping("call-patient/{id}")
    public ResponseEntity<Void> callPatient(@PathVariable Long id) {
        waitingRoomService.callPatient(id);
        kafkaTemplate.send("waiting-room", id.toString());
        return ResponseEntity.ok().build();
    }

    @KafkaListener(
            topics = "waiting-room",
            groupId = "dental"
    )
    public void listen(String id) {
        System.out.println("sending via kafka listener..");
        template.convertAndSend("/topic/group", id);
    }

    @MessageMapping("/call-patient")
    @SendTo("/topic/group")
    public String broadcastGroupMessage(@Payload String id) {
        //Sending this message to all the subscribers
        return id;
    }

    @GetMapping("get-list-waiting")
    public ResponseEntity<Page<WaitingRoomDTO>> getListWaitingRoom(@RequestParam(required = false, defaultValue = "") String patientName,
                                                                   @RequestParam(required = false)LocalDate date,
                                                                   Pageable pageable) {

        return  ResponseEntity.ok().body(waitingRoomService.getListWaitingRoom(patientName,date, pageable));
    }

    @PostMapping("confirm-customer/{id}")
    public ResponseEntity<Void> confirmCustomer(@PathVariable Long id,
                                                @RequestParam Boolean isAttend) {

        waitingRoomService.confirmCustomer(id, isAttend);
        return ResponseEntity.ok().build();
    }

    @PostMapping("")
    public void addPatientToWaitingRoom(@RequestBody Patient patient) {

    }

    @GetMapping("get_list_confirm")
    public ResponseEntity<List<WaitingRoomDTO>> getListConfirm() {
        return ResponseEntity.ok().body(waitingRoomService.getListConfirm());
    }

}
