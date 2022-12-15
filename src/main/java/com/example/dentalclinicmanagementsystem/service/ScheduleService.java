package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.constant.StatusConstant;
import com.example.dentalclinicmanagementsystem.dto.WaitingRoomDTO;
import com.example.dentalclinicmanagementsystem.entity.Patient;
import com.example.dentalclinicmanagementsystem.entity.WaitingRoom;
import com.example.dentalclinicmanagementsystem.exception.AccessDenyException;
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
import java.util.Arrays;
import java.util.Objects;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private WaitingRoomRepository waitingRoomRepository;

    @Autowired
    private WaitingRoomMapper waitingRoomMapper;

    public WaitingRoomDTO addSchedule(WaitingRoomDTO waitingRoomDTO) {

        waitingRoomDTO.setWaitingRoomId(null);

        Patient patient = patientRepository.findByPatientIdAndIsDeleted(waitingRoomDTO.getPatientId(), Boolean.FALSE);

        if (Objects.isNull(patient)) {
            throw new EntityNotFoundException(MessageConstant.Patient.PATIENT_NOT_FOUND,
                    EntityName.Patient.PATIENT, EntityName.Patient.PATIENT_ID);
        }

        if (waitingRoomDTO.getDate().isBefore(LocalDate.now())) {
            throw new AccessDenyException(MessageConstant.WaitingRoom.DATE_MUST_BE_AFTER_CURRENT_DAY, EntityName.WaitingRoom.WAITING_ROOM);
        }

        waitingRoomDTO.setIsBooked(Boolean.TRUE);
        waitingRoomDTO.setStatus(StatusConstant.NOT_COMING);
        waitingRoomDTO.setIsDeleted(Boolean.FALSE);

        WaitingRoom waitingRoom = waitingRoomMapper.toEntity(waitingRoomDTO);
        waitingRoomRepository.save(waitingRoom);

        return waitingRoomMapper.toDto(waitingRoom);
    }

    public WaitingRoomDTO updateSchedule(Long id, WaitingRoomDTO waitingRoomDTO) {

        waitingRoomDTO.setWaitingRoomId(id);
        WaitingRoom waitingRoomDb = waitingRoomRepository.findByWaitingRoomIdAndStatusInAndAndIsDeleted(id,
                Arrays.asList(StatusConstant.NOT_COMING, StatusConstant.CANCEL), Boolean.FALSE);

        if (Objects.isNull(waitingRoomDb)) {
            throw new AccessDenyException(MessageConstant.WaitingRoom.THIS_SCHEDULE_HAD_BEEN_CONFIRM, EntityName.WaitingRoom.WAITING_ROOM);
        }

        if (waitingRoomDTO.getDate().isBefore(LocalDate.now())) {
            throw new AccessDenyException(MessageConstant.WaitingRoom.DATE_MUST_BE_AFTER_CURRENT_DAY, EntityName.WaitingRoom.WAITING_ROOM);
        }

        if (waitingRoomDb.getStatus() == StatusConstant.CANCEL) {
            waitingRoomDTO.setStatus(StatusConstant.CONFIRM);
        }

        waitingRoomDTO.setIsBooked(Boolean.TRUE);
        waitingRoomDTO.setIsDeleted(Boolean.FALSE);

        WaitingRoom waitingRoom = waitingRoomMapper.toEntity(waitingRoomDTO);
        waitingRoomRepository.save(waitingRoom);

        return waitingRoomMapper.toDto(waitingRoom);
    }

    public Page<WaitingRoomDTO> getListSchedule(String patientName, LocalDate date, Pageable pageable) {

        if (Objects.isNull(date)) {
            date = LocalDate.now();
            return waitingRoomRepository.getListSchedule(patientName, date, pageable);
        } else {
            return waitingRoomRepository.getListScheduleInDay(patientName, date, pageable);
        }

    }

    public WaitingRoomDTO getDetail(Long id) {

        WaitingRoomDTO waitingRoomDTO = waitingRoomRepository.getDetailSchedule(id);

        if (Objects.isNull(waitingRoomDTO)) {
            throw new EntityNotFoundException(MessageConstant.WaitingRoom.WAITING_ROOM_NOT_FOUND,
                    EntityName.WaitingRoom.WAITING_ROOM, EntityName.WaitingRoom.WAITING_ROOM_ID);
        }

        return waitingRoomDTO;
    }
}
