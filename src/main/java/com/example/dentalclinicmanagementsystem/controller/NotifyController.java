package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.dto.NotifyDTO;
import com.example.dentalclinicmanagementsystem.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notifies")
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

    @GetMapping("get_list_notify")
    public ResponseEntity<List<NotifyDTO>> getListNotify() {
        return ResponseEntity.ok().body(notifyService.getListNotify());
    }

    @PutMapping("read_notify/{id}")
    public ResponseEntity<Void> readNotify(@PathVariable Long id) {

        notifyService.readNotify(id);
        return ResponseEntity.ok().build();
    }
}
