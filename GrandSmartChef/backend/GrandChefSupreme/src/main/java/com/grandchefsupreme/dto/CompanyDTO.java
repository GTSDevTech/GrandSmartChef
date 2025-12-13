package com.grandchefsupreme.dto;

import lombok.Data;

@Data
public class CompanyDTO {

    private Long id;
    private String companyName;
    private String cif;
    private String address;
    private String postalCode;
    private String googleMapsLocation;

}
