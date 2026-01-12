package com.grandchefsupreme.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UpdatePreferencesDTO {

    @NotNull
    List<PreferenceDTO> preferences;
}
