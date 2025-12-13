package com.grandchefsupreme.model.Enums;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Tags {

    ECONOMICA("Económica"),
    VEGETARIANA("Vegetariana"),
    SIN_GLUTEN("Sin gluten"),
    RAPIDAS("Rápidas");

    private final String label;

    Tags(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
