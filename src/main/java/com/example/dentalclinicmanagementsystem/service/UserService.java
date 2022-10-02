package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.dto.UsersDTO;
import com.example.dentalclinicmanagementsystem.entity.Users;
import com.example.dentalclinicmanagementsystem.repository.UserRepository;
import com.example.dentalclinicmanagementsystem.security.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepository.findUsersByUserName(username);
        if(user == null){
            throw new RuntimeException("user not found");
        }

        return new UserDetailImpl(user);
    }

    public List<UsersDTO> getListUsers() {
        
    }
}
