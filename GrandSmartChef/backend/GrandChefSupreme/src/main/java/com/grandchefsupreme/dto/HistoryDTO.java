package com.grandchefsupreme.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class    HistoryDTO {

    private Long id;
    private Long clientId;
    private Long recipeId;
    private String date;

}
