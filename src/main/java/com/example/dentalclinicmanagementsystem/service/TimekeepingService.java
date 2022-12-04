package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.dto.TimekeepingDTO;
import com.example.dentalclinicmanagementsystem.dto.TimekeepingWithButtonDTO;
import com.example.dentalclinicmanagementsystem.entity.Timekeeping;
import com.example.dentalclinicmanagementsystem.entity.User;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.exception.AccessDenyException;
import com.example.dentalclinicmanagementsystem.mapper.TimekeepingMapper;
import com.example.dentalclinicmanagementsystem.repository.TimekeepingRepository;
import com.example.dentalclinicmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@Transactional
public class TimekeepingService extends AbstractService {

    public static final Long ADMIN = 1L;
    public static final int TIME_POINT = 3;

    @Autowired
    private TimekeepingMapper timekeepingMapper;

    @Autowired
    private TimekeepingRepository timekeepingRepository;

    @Autowired
    private UserRepository userRepository;

    public void checkin(String token) {

        Timekeeping timekeeping = new Timekeeping();
        timekeeping.setUserId(getUserId(token));

        Timekeeping lastTimekeeping = timekeepingRepository.findFirstByUserIdOrderByTimekeepingIdDesc(timekeeping.getUserId());
        if (Objects.isNull(lastTimekeeping) || Objects.nonNull(lastTimekeeping.getTimeCheckout())) {
            timekeeping.setTimeCheckin(LocalDateTime.now());
            timekeepingRepository.save(timekeeping);
        } else {
            throw new AccessDenyException(MessageConstant.Timekeeping.CHECKOUT_NOTABLE,
                    EntityName.Timekeeping.TIMEKEEPING);
        }
    }

    public void checkout(String token) {

        Long userId = getUserId(token);
        Timekeeping lastTimekeeping = timekeepingRepository.findFirstByUserIdOrderByTimekeepingIdDesc(userId);

        if (Objects.nonNull(lastTimekeeping)
                && Objects.nonNull(lastTimekeeping.getTimeCheckin())
                && lastTimekeeping.getTimeCheckin().plusHours(TIME_POINT).isBefore(LocalDateTime.now())) {
            lastTimekeeping.setTimeCheckout(LocalDateTime.now());
            timekeepingRepository.save(lastTimekeeping);
        } else {
            throw new AccessDenyException(MessageConstant.Timekeeping.CHECKIN_NOTABLE,
                    EntityName.Timekeeping.TIMEKEEPING);
        }
    }

    public TimekeepingWithButtonDTO getListTimekeeping(String token, String fullName,
                                                       LocalDate startTime, LocalDate endTime,
                                                       Integer month,
                                                       Pageable pageable) {

        TimekeepingWithButtonDTO timekeepingWithButtonDTO = new TimekeepingWithButtonDTO();

        Long userId = getUserId(token);
        User user = userRepository.findByUserIdAndEnable(userId, Boolean.TRUE);
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException(MessageConstant.User.USER_NOT_FOUND,
                    EntityName.User.USER, EntityName.User.USER_ID);
        }
        if (Objects.equals(user.getRoleId(), ADMIN)) {
            timekeepingWithButtonDTO.setTimekeepingDTOS(timekeepingRepository.
                    getListTimeKeepingOfAdmin(startTime, endTime, fullName, pageable));
            timekeepingWithButtonDTO.setCheckinEnable(Boolean.FALSE);
            timekeepingWithButtonDTO.setCheckoutEnable(Boolean.FALSE);

            return timekeepingWithButtonDTO;
        }

        if (Objects.isNull(month)) {
            month = LocalDate.now().getMonth().getValue();
        }

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "timekeepingId"));
        Page<TimekeepingDTO> timekeepingDTOS = timekeepingRepository.
                getListTimeKeepingOfUser(month, userId, pageable);

        if (CollectionUtils.isEmpty(timekeepingDTOS.getContent())) {
            timekeepingWithButtonDTO.setCheckinEnable(Boolean.TRUE);
            timekeepingWithButtonDTO.setCheckoutEnable(Boolean.FALSE);
        } else {
            TimekeepingDTO lastTimeKeeping = timekeepingMapper.toDto(timekeepingRepository
                    .findFirstByUserIdOrderByTimekeepingIdDesc(userId));
            if (Objects.nonNull(lastTimeKeeping.getTimeCheckin())
                    && Objects.isNull(lastTimeKeeping.getTimeCheckout())
                    && lastTimeKeeping.getTimeCheckin().plusHours(TIME_POINT).isBefore(LocalDateTime.now())) {
                timekeepingWithButtonDTO.setCheckinEnable(Boolean.FALSE);
                timekeepingWithButtonDTO.setCheckoutEnable(Boolean.TRUE);
            } else if (Objects.nonNull(lastTimeKeeping.getTimeCheckin())
                    && Objects.isNull(lastTimeKeeping.getTimeCheckout())
                    && lastTimeKeeping.getTimeCheckin().plusHours(TIME_POINT).isAfter(LocalDateTime.now())) {
                timekeepingWithButtonDTO.setCheckinEnable(Boolean.FALSE);
                timekeepingWithButtonDTO.setCheckoutEnable(Boolean.FALSE);
            } else {
                timekeepingWithButtonDTO.setCheckinEnable(Boolean.TRUE);
                timekeepingWithButtonDTO.setCheckoutEnable(Boolean.TRUE);
            }
        }
        timekeepingWithButtonDTO.setTimekeepingDTOS(timekeepingDTOS);
        timekeepingWithButtonDTO.setWorkDay(timekeepingRepository.findWorkDatByUserIdAndMonth(userId, month));

        return timekeepingWithButtonDTO;

    }
}
