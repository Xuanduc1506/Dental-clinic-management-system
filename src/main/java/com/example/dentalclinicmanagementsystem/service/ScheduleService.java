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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ScheduleService {

    public static final int SENT_NOTIFY_DATE = 2;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private WaitingRoomRepository waitingRoomRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sendFrom;

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

        WaitingRoom waitingRoomDb = waitingRoomRepository.findAllByPatientIdAndDateAndIsBooked(
                waitingRoomDTO.getPatientId(), waitingRoomDTO.getDate(), Boolean.TRUE);

        if (Objects.nonNull(waitingRoomDb)) {
            throw new AccessDenyException(MessageConstant.WaitingRoom.THIS_SCHEDULE_HAD_BOOKED, EntityName.WaitingRoom.WAITING_ROOM);
        }

        waitingRoomDTO.setIsBooked(Boolean.TRUE);
        waitingRoomDTO.setStatus(StatusConstant.NOT_COMING);
        waitingRoomDTO.setIsDeleted(Boolean.FALSE);

        WaitingRoom waitingRoom = waitingRoomMapper.toEntity(waitingRoomDTO);
        waitingRoomRepository.save(waitingRoom);

        return waitingRoomMapper.toDto(waitingRoom);
    }

    public WaitingRoomDTO updateSchedule(Long id, WaitingRoomDTO waitingRoomDTO) {


        WaitingRoom waitingRoomDb = waitingRoomRepository.findByWaitingRoomIdAndStatusAndIsDeleted(id,
                StatusConstant.NOT_COMING, Boolean.FALSE);

        if (Objects.isNull(waitingRoomDb)) {
            throw new AccessDenyException(MessageConstant.WaitingRoom.THIS_SCHEDULE_HAD_BEEN_CONFIRM, EntityName.WaitingRoom.WAITING_ROOM);
        }

        Patient patient = patientRepository.findByPatientIdAndIsDeleted(waitingRoomDTO.getPatientId(), Boolean.FALSE);

        if (Objects.isNull(patient)) {
            throw new EntityNotFoundException(MessageConstant.Patient.PATIENT_NOT_FOUND,
                    EntityName.Patient.PATIENT, EntityName.Patient.PATIENT_ID);
        }

        if (waitingRoomDTO.getDate().isBefore(LocalDate.now())) {
            throw new AccessDenyException(MessageConstant.WaitingRoom.DATE_MUST_BE_AFTER_CURRENT_DAY, EntityName.WaitingRoom.WAITING_ROOM);
        }

        waitingRoomDTO.setWaitingRoomId(id);
        waitingRoomDTO.setIsBooked(Boolean.TRUE);
        waitingRoomDTO.setIsDeleted(Boolean.FALSE);
        waitingRoomDTO.setStatus(StatusConstant.NOT_COMING);

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

    public void sentNotify() {

        LocalDate dateSent = LocalDate.now().plusDays(SENT_NOTIFY_DATE);

        List<Long> listPatientIdNeedToSent =  waitingRoomRepository.getListPatientIdInDay(dateSent);

        List<Patient> patients = patientRepository.findAllByPatientIdInAndIsDeleted(listPatientIdNeedToSent, Boolean.FALSE);

        patients.forEach(patient -> {

            String content = "Dear "+ patient.getPatientName() +",<br>"
                    + "<br>Bạn có lịch khám vào ngày " + dateSent + "</b> <br> "
                    + "<br>Vui long bạn có mặt vào hôm đấy.</b> <br> "
                    + "<br>Nếu bạn muốn thay đổi lịch hen vui lòng liên lạc qua số điện thoại: 0904912207 </b> <br> "
                    + "<br> Thank you! <br> "
                    + " <br> Ngọc Huyền Dental";

            MimeMessage message = mailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(sendFrom);
                helper.setTo(patient.getEmail());
                helper.setSubject("[NGỌC HUYỂN DENTAIL] Lịch hẹn");
                helper.setText(content, true);
                mailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }
}
