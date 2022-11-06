package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.dto.UserDTO;
import com.example.dentalclinicmanagementsystem.entity.User;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.exception.WrongPasswordException;
import com.example.dentalclinicmanagementsystem.mapper.UserMapper;
import com.example.dentalclinicmanagementsystem.repository.UserRepository;
import com.example.dentalclinicmanagementsystem.security.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService extends AbstractService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User user = userRepository.findUsersByUserName(username);
//        if (user == null) {
//            throw new RuntimeException("user not found");
//        }
//
//        return new UserDetailImpl(user);
//    }

    public Page<UserDTO> getListUsers(String username,
                                      String phone,
                                      String roleName,
                                      Pageable pageable) {
        return userRepository.getListUser(username, phone, roleName, pageable);
    }

    public UserDTO getDetailUser(Long id) {
        UserDTO userDTO = userRepository.getDetailUser(id);
        if (Objects.isNull(userDTO)) {
            throw new EntityNotFoundException(MessageConstant.User.USER_NOT_FOUND,
                    EntityName.User.USER, EntityName.User.USER_ID);
        }
        return userDTO;
    }

    public UserDTO registerUser(UserDTO userDTO) {

        userDTO.setUserId(null);
        userDTO.setEnable(Boolean.TRUE);
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return saveUser(userDTO);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User userDb = userRepository.findByUserIdAndEnable(id, Boolean.TRUE);
        if (Objects.isNull(userDb)) {
            throw new EntityNotFoundException(MessageConstant.User.USER_NOT_FOUND,
                    EntityName.User.USER, EntityName.User.USER_ID);
        }
        userDTO.setUserId(id);
        userDTO.setEnable(Boolean.TRUE);
        userDTO.setPassword(userDb.getPassword());

        if(Objects.equals(userDb.getFullName(), userDTO.getFullName())){
            userDTO.setEnable(Boolean.TRUE);

            User user = userMapper.toEntity(userDTO);
            user.setPermissions(null);
            return userMapper.toDto(userRepository.save(user));
        } else {
            return saveUser(userDTO);
        }
    }

    private UserDTO saveUser(UserDTO userDTO) {
        String code = genCode(userDTO.getFullName());

        List<String> usernames = userRepository.findAllByUserNameContaining(code)
                .stream().map(User::getUserName).collect(Collectors.toList());
        userDTO.setUserName(setUsername(code, usernames));
        userDTO.setEnable(Boolean.TRUE);

        User user = userMapper.toEntity(userDTO);
        user.setPermissions(null);
        return userMapper.toDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findByUserIdAndEnable(id, Boolean.TRUE);
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException(MessageConstant.User.USER_NOT_FOUND,
                    EntityName.User.USER, EntityName.User.USER_ID);
        }

        user.setEnable(Boolean.FALSE);
        userRepository.save(user);
    }

    public void changePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findByUserIdAndEnable(id, Boolean.TRUE);
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException(MessageConstant.User.USERNAME_NOT_FOUND,
                    EntityName.User.USER, EntityName.User.USERNAME);
        }
        if(passwordEncoder.matches(oldPassword, user.getPassword())){
           user.setPassword(passwordEncoder.encode(newPassword));
        }else{
            throw new WrongPasswordException(MessageConstant.User.WRONG_PASSWORD);
        }
        userRepository.save(user);
    }


}
