package com.barnaholl.MindTechHomework.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JwtTokenDto {
    private String token;
}


