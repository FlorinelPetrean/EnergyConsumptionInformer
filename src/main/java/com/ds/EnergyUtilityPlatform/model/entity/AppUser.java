package com.ds.EnergyUtilityPlatform.model.entity;


import com.ds.EnergyUtilityPlatform.model.dto.IDto;
import com.ds.EnergyUtilityPlatform.model.dto.UserDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser implements IEntity<AppUser>{
    @Id
    @Column(nullable = false)
    private Long id;

    @Column
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
    private boolean isAdmin;


    @OneToMany(mappedBy = "user")
    private List<Device> devices;


    @Override
    public AppUser toEntity(IDto<AppUser> dto) {
        UserDto userDto = (UserDto) dto;
        return AppUser.builder()
                .id((userDto.getId()))
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .address(userDto.getAddress())
                .dateOfBirth((Date) userDto.getDateOfBirth())
                .isAdmin(userDto.isAdmin())
                .build();
    }
}
