package com.ds.EnergyUtilityPlatform.model.dto;

import com.ds.EnergyUtilityPlatform.model.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements IDto<AppUser>{
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String address;

    private Date dateOfBirth;

    private boolean isAdmin;

    public UserDto toDto(AppUser user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .dateOfBirth(user.getDateOfBirth())
                .isAdmin(user.isAdmin())
                .build();
    }
}
