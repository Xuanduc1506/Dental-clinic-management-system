package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.constant.StatusConstant;
import com.example.dentalclinicmanagementsystem.dto.WaitingRoomDTO;
import com.example.dentalclinicmanagementsystem.entity.Patient;
import com.example.dentalclinicmanagementsystem.entity.WaitingRoom;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.mapper.WaitingRoomMapper;
import com.example.dentalclinicmanagementsystem.repository.PatientRepository;
import com.example.dentalclinicmanagementsystem.repository.WaitingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class WaitingRoomService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private WaitingRoomRepository waitingRoomRepository;

    @Autowired
    private WaitingRoomMapper waitingRoomMapper;

    public void callPatient(Long id) {

        WaitingRoom waitingRoom = waitingRoomRepository.findByWaitingRoomIdAndStatus(id, StatusConstant.WAITING);
        if (Objects.isNull(waitingRoom)) {
            throw new EntityNotFoundException(MessageConstant.WaitingRoom.WAITING_ROOM_NOT_FOUND,
                    EntityName.WaitingRoom.WAITING_ROOM, EntityName.WaitingRoom.WAITING_ROOM_ID);
        }

        waitingRoom.setStatus(StatusConstant.CONFIRMING);
        waitingRoomRepository.save(waitingRoom);

    }

    public Page<WaitingRoomDTO> getListWaitingRoom(String patientName, LocalDate date, Pageable pageable) {

        if (Objects.isNull(date)) {
            date = LocalDate.now();
        }

        return waitingRoomRepository.getListWaitingRoom(patientName,date, pageable);
    }

    public void confirmCustomer(Long id, Boolean isAttend) {

        WaitingRoom waitingRoom = waitingRoomRepository.findByWaitingRoomIdAndStatus(id, StatusConstant.CONFIRMING);

        if (Objects.isNull(waitingRoom)) {
            throw new EntityNotFoundException(MessageConstant.WaitingRoom.WAITING_ROOM_NOT_FOUND,
                    EntityName.WaitingRoom.WAITING_ROOM, EntityName.WaitingRoom.WAITING_ROOM_ID);
        }

        if(Objects.equals(isAttend, Boolean.TRUE)) {
            waitingRoom.setStatus(StatusConstant.TREATING);
        } else {
            waitingRoom.setIsDeleted(Boolean.TRUE);
        }
        waitingRoomRepository.save(waitingRoom);

    }

    public List<WaitingRoomDTO> getListConfirm() {
        return waitingRoomRepository.findAllListConfirm();
    }
}
