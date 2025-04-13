package com.syncstate.probase.bills.BillerService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.syncstate.probase.bills.BillerService.deserializers.DateDeserializer;
import com.syncstate.probase.bills.BillerService.deserializers.TimestampDeserializer;
import com.syncstate.probase.bills.BillerService.enums.UserRole;
import com.syncstate.probase.bills.BillerService.enums.UserStatus;
import com.syncstate.probase.bills.BillerService.serializer.JsonDateSerializer;
import com.syncstate.probase.bills.BillerService.serializer.JsonDateTimeSerializer;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "users")
@JsonIgnoreProperties(value = { "password", "hibernateLazyInitializer", "handler" })
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable= false)
    private String fullName;
    @Column(nullable= false)
    private String mobileNumber;
    @Column(nullable= false)
    private String emailAddress;
    @Column(nullable= true)
    private String username;
    @Column(nullable= true)
    private String password;
    @Column(nullable= false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(nullable= false)
    private LocalDateTime createdAt;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(nullable= true)
    private LocalDateTime deletedAt;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = TimestampDeserializer.class)
    @Column(nullable= false)
    private LocalDateTime updatedAt;
    @Column(nullable= true)
    private String otp;

    @Column(nullable= false)
    @Enumerated(EnumType.STRING)
//    @Convert(converter = UserStatusConverter.class)
    private UserStatus userStatus;

    @Column(nullable= true)
    private Long userTypeId;
    @Column(nullable= true)
    private String userUniqueId;
    @Column(nullable= true)
    private String nrcNumber;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

}
