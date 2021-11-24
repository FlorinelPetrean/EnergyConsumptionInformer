package com.ds.EnergyUtilityPlatform.model.dto.entitydto;

import com.ds.EnergyUtilityPlatform.model.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements IDto<AppUser> {
    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String address;

    private String dateOfBirth;

    private String role;

    public UserDto toDto(AppUser user) {
        String dateOfBirth = user.getDateOfBirth().toString();
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .dateOfBirth(dateOfBirth)
                .role(user.getRole())
                .build();
    }
}
