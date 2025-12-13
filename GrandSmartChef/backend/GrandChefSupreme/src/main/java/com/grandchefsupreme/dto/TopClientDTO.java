package com.grandchefsupreme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopClientDTO {

    private Long clientId;
    private String fullName;
    private String email;
}
