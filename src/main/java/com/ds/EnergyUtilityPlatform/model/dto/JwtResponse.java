package com.ds.EnergyUtilityPlatform.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtResponse {
    private String token;
    private String role;
}
