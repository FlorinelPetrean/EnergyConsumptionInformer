package com.ds.EnergyUtilityPlatform.model.entity;


import com.ds.EnergyUtilityPlatform.model.dto.IDto;
import com.ds.EnergyUtilityPlatform.model.dto.UserDto;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class AppUser implements IEntity<AppUser>{
//    @Id
//    @Column(name ="id", columnDefinition = "serial")
//    @GeneratedValue(strategy=GenerationType.SEQUENCE)
//    private Long id;
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String address;

    @Column
    private Date dateOfBirth;

    @Column
    private String role;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices;


    @Override
    public AppUser toEntity(IDto<AppUser> dto) {
        UserDto userDto = (UserDto) dto;
        LocalDate dateOfBirth = LocalDate.parse(userDto.getDateOfBirth());
        return AppUser.builder()
                .id((userDto.getId()))
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .address(userDto.getAddress())
                .dateOfBirth(Date.valueOf(dateOfBirth))
                .role(userDto.getRole())
                .devices(new ArrayList<>(50))
                .build();
    }
}
