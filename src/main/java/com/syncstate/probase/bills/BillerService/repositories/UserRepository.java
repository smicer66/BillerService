package com.syncstate.probase.bills.BillerService.repositories;

import com.syncstate.probase.bills.BillerService.enums.UserRole;
import com.syncstate.probase.bills.BillerService.models.User;
import com.syncstate.probase.bills.BillerService.models.UserRolePermission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM UserRolePermission u WHERE u.userRole = :roleName ORDER BY id ASC")
    List<UserRolePermission> findRolePermissionByRole(UserRole roleName, Pageable pageable);



    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL AND u.username = :username")
    User getUserByUsername(String username);


    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL AND " +
            "(u.fullName LIKE :searchStringLike OR u.userRole LIKE :searchStringLike3 OR " +
            "u.userStatus LIKE :searchStringLike4 OR u.mobileNumber LIKE :searchStringLike5) ORDER BY id ASC")
    List<User> filterUsers(String searchStringLike, String searchStringLike1, String searchStringLike2, String searchStringLike3, String searchStringLike4, String searchStringLike5, Pageable pageable);

    @Query("SELECT COUNT(u.id) as count FROM User u WHERE u.deletedAt IS NULL AND " +
            "(u.fullName LIKE :searchStringLike2 OR u.userRole LIKE :searchStringLike3 OR " +
            "u.userStatus LIKE :searchStringLike4 OR u.mobileNumber LIKE :searchStringLike5)")
    List<Integer> filterUsersCount(String searchStringLike, String searchStringLike1, String searchStringLike2, String searchStringLike3, String searchStringLike4, String searchStringLike5);



    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL")
    List<User> findUsers(Pageable pageable);

    @Query("SELECT COUNT(u.id) as count FROM User u WHERE u.deletedAt IS NULL")
    List<Integer> findUsersCount();

}
