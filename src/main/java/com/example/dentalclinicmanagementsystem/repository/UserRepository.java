package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.UserDTO;
import com.example.dentalclinicmanagementsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUsersByUserName(String username);

    @Query(value = "select new com.example.dentalclinicmanagementsystem.dto.UserDTO(u.userId, u.fullName, u.userName, " +
            "u.birthdate, u.phone, u.roleId, r.roleName)" +
            "from User u left join Role r on u.roleId = r.roleId " +
            "where u.enable = true " +
            "AND (:username is null or u.userName like %:username%) " +
            "AND (:phone is null or u.phone like %:phone%) " +
            "AND (:roleName is null or r.roleName like %:roleName%)",
            countQuery = "select count(u.userId)" +
                    "from User u left join Role r on u.roleId = r.roleId " +
                    "where u.enable = true " +
                    "AND (:username is null or u.userName like %:username%) " +
                    "AND (:phone is null or u.phone like %:phone%)" +
                    "AND (:roleName is null or r.roleName like %:roleName%)")
    Page<UserDTO> getListUser(@Param("username") String username,
                              @Param("phone") String phone,
                              @Param("roleName") String roleName,
                              Pageable pageable);

    User findByUserIdAndEnable(Long id, Boolean enable);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.UserDTO(u.userId, u.fullName, u.userName," +
            " u.birthdate, u.phone, u.roleId, r.roleName)" +
            " FROM User u JOIN Role r ON u.roleId = r.roleId WHERE u.userId = :id AND u.enable = TRUE")
    UserDTO getDetailUser(Long id);

    List<User> findAllByUserNameContaining(String code);
}
