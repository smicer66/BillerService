package com.syncstate.probase.bills.BillerService.services;

import com.syncstate.probase.bills.BillerService.enums.UserRole;
import com.syncstate.probase.bills.BillerService.enums.UserStatus;
import com.syncstate.probase.bills.BillerService.models.User;
import com.syncstate.probase.bills.BillerService.models.UserRolePermission;
import com.syncstate.probase.bills.BillerService.models.requests.DataTablesRequest;
import com.syncstate.probase.bills.BillerService.models.requests.ValidateOTPRequest;
import com.syncstate.probase.bills.BillerService.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public User save(User user){
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user;
    }


    public List<UserRolePermission> getPermissionsByRole(UserRole roleName, Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<UserRolePermission> userRolePermissionList = userRepository.findRolePermissionByRole(roleName, pageable);
        return userRolePermissionList;
    }

    public User getUserById(Long userId) {
        User user = userRepository.getById(userId);
        return  user;
    }

    public User getUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        return  user;
    }



    public Map getAllUsers(DataTablesRequest dataTableRequest, Integer pageSize, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<User> userList = new ArrayList<User>();


//        logger.info("xxx {}", dataTableRequest.getDraw());
        if(dataTableRequest!=null && dataTableRequest.getSearch()!=null && dataTableRequest.getSearch().containsKey("value")) {
            String searchStringLike = "%".concat(dataTableRequest.getSearch().get("value")).concat("%");
            userList = userRepository.filterUsers(searchStringLike, searchStringLike, searchStringLike, searchStringLike, searchStringLike, searchStringLike, pageable);
        }
        else {
            userList = userRepository.findUsers(pageable);
        }


        List<Integer> count = new ArrayList<Integer>();
        if(dataTableRequest!=null && dataTableRequest.getSearch()!=null && dataTableRequest.getSearch().containsKey("value")) {

            String searchStringLike = "%".concat(dataTableRequest.getSearch().get("value")).concat("%");
            count = userRepository.filterUsersCount(searchStringLike, searchStringLike, searchStringLike, searchStringLike, searchStringLike, searchStringLike);
        }
        else {
            count = userRepository.findUsersCount();
        }


        logger.info("{}", count);

        Map map = new HashMap<>();
        map.put("userList", userList);
        map.put("count", count);

        return map;
    }

    public User validateOtp(ValidateOTPRequest validateOTPRequest) {

        User user = userRepository.getById(validateOTPRequest.getUserId());

        if(user==null)
            return null;


        String otp = validateOTPRequest.getOtp();

        if(user.getOtp().equals(otp))
        {
            user.setOtp(null);
            user.setUpdatedAt(LocalDateTime.now());
            user.setUserStatus(UserStatus.ACTIVE);
            user = userRepository.save(user);
            return user;
        }

        return null;
    }

    public void importFarmers(MultipartFile farmersListFile) {

    }
}
