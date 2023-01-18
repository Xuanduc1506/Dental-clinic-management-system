package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.dto.NotifyDTO;
import com.example.dentalclinicmanagementsystem.entity.Notify;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.mapper.NotifyMapper;
import com.example.dentalclinicmanagementsystem.repository.NotifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NotifyService {

    @Autowired
    private NotifyRepository notifyRepository;

    @Autowired
    private NotifyMapper notifyMapper;

    public List<NotifyDTO> getListNotify() {
        return notifyMapper.toDto(notifyRepository.getListNotify());
    }

    public void readNotify(Long id) {
        Notify notify = notifyRepository.findByNotifyId(id);

        if (Objects.isNull(notify)) {
            throw new EntityNotFoundException(MessageConstant.Notify.Notify_NOT_FOUND, EntityName.Notify.Notify,
                    EntityName.Notify.Notify_ID);
        }

        notify.setIsRead(Boolean.TRUE);
        notifyRepository.save(notify);
    }
}
