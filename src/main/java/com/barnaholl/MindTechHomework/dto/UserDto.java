package com.barnaholl.MindTechHomework.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDto {
    private String username;
    private String password;
}
