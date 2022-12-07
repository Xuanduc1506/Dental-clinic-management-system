package com.example.dentalclinicmanagementsystem.repository;

import com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO;
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

    @Query(value = "select new com.example.dentalclinicmanagementsystem.dto.UserDTO(u.userId, u.fullName, u.userName, " +
            "u.birthdate, u.phone, u.role.roleId, r.roleName, u.salary, u.email)" +
            "from User u left join Role r on u.role.roleId = r.roleId " +
            "where u.enable = true " +
            "AND (:username is null or u.userName like %:username%) " +
            "AND (:phone is null or u.phone like %:phone%) " +
            "AND (:roleName is null or r.roleName like %:roleName%) " +
            "ORDER BY u.userId DESC ",
            countQuery = "select count(u.userId)" +
                    "from User u left join Role r on u.role.roleId = r.roleId " +
                    "where u.enable = true " +
                    "AND (:username is null or u.userName like %:username%) " +
                    "AND (:phone is null or u.phone like %:phone%)" +
                    "AND (:roleName is null or r.roleName like %:roleName%)")
    Page<UserDTO> getListUser(@Param("username") String username,
                              @Param("phone") String phone,
                              @Param("roleName") String roleName,
                              Pageable pageable);

    User findByUserIdAndEnable(Long id, Boolean enable);

    User findByEmailAndEnable(String email, Boolean enable);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.UserDTO(u.userId, u.fullName, u.userName," +
            " u.birthdate, u.phone, u.role.roleId, r.roleName, u.salary, u.email)" +
            " FROM User u JOIN Role r ON u.role.roleId = r.roleId WHERE u.userId = :id AND u.enable = TRUE")
    UserDTO getDetailUser(Long id);

    List<User> findAllByUserNameContaining(String code);

    @Query(value = "SELECT sum(COUNT(t.user_id) * u.salary) OVER() FROM timekeeping t join users u ON t.user_id = u.user_id WHERE " +
            "MONTH(t.time_checkin) = :month " +
            "AND MONTH(t.time_checkout) = :month " +
            "GROUP BY t.user_id limit 1", nativeQuery = true)
    Integer totalMoneyOfUserInMonth(Integer month);

    @Query(value = "SELECT sum(COUNT(t.user_id) * u.salary) OVER() FROM timekeeping t join users u ON t.user_id = u.user_id WHERE " +
            "YEAR(t.time_checkin) = :year " +
            "AND YEAR(t.time_checkout) = :year " +
            "GROUP BY t.user_id limit 1", nativeQuery = true)
    Integer totalMoneyOfUserInYear(Integer year);


    User findByUserNameAndAndEnable(String username, Boolean enable);

    @Query("SELECT new com.example.dentalclinicmanagementsystem.dto.IncomeDetailDTO(u.fullName, u.salary * count(t.timekeepingId)) " +
            "FROM User u JOIN Timekeeping t ON u.userId = t.userId " +
            "WHERE MONTH(t.timeCheckin) = :month AND YEAR(t.timeCheckin) = :year " +
            "AND MONTH(t.timeCheckout) = :month AND YEAR(t.timeCheckout) = :year " +
            "GROUP BY u.userId")
    List<IncomeDetailDTO> findTotalSalary(@Param("month") Integer month,
                                          @Param("year") Integer year);
}
