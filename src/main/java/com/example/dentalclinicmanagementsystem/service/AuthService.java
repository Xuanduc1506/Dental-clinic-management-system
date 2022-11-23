package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.constant.EntityName;
import com.example.dentalclinicmanagementsystem.constant.MessageConstant;
import com.example.dentalclinicmanagementsystem.dto.UserDTO;
import com.example.dentalclinicmanagementsystem.dto.exception.TokenExceptionDTO;
import com.example.dentalclinicmanagementsystem.entity.User;
import com.example.dentalclinicmanagementsystem.exception.EntityNotFoundException;
import com.example.dentalclinicmanagementsystem.repository.UserRepository;
import com.example.dentalclinicmanagementsystem.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.Random;

@Service
public class AuthService {

    public static final int LETTER_A = 97;

    public static final int LETTER_Z = 122;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sendFrom;

    public ResponseEntity<Object> login(UserDTO userDTO) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getUserName(),
                            userDTO.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok().body(tokenProvider.createToken(authentication));
        } catch (AuthenticationException authenticationException) {
            TokenExceptionDTO tokenExceptionDTO = new TokenExceptionDTO();
            tokenExceptionDTO.setMessage("Wrong username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(tokenExceptionDTO);
        }
    }

    public void resetPassword(UserDTO userDTO) {

        User user = userRepository.findByUserNameAndAndEnable(userDTO.getUserName(), Boolean.TRUE);
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException(MessageConstant.User.USER_NOT_FOUND,
                    EntityName.User.USER, EntityName.User.USER_ID);
        }

        int leftLimit = LETTER_A;
        int rightLimit = LETTER_Z;
        int targetStringLength = 8;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String randomPassword = buffer.toString();

        user.setPassword(passwordEncoder.encode(randomPassword));
        userRepository.save(user);

        String content = "Dear "+ user.getFullName() +",<br>"
                + "<br>Đây là password mới của ban: <b>" + randomPassword + " </b> <br> "
                + "<br> Thank you! <br> "
                + " <br> Ngọc Huyền Dental";

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sendFrom);
            helper.setTo(user.getEmail());
            helper.setSubject("[NGỌC HUYỂN DENTAIL] Reset password");
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
