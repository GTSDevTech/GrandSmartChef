package com.grandchefsupreme.model.Enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Difficulty {
    FACIL(0),
    MEDIA(1),
    DIFICIL(2),
    CHEF(3);

    private final int code;

    Difficulty(int code) {
        this.code = code;
    }


    public static Difficulty getDifficulty(int code) {
        for (Difficulty d : Difficulty.values()) {
            if (d.getCode() == code) {
                return d;
            }
        }
        throw new IllegalArgumentException("Invalid Difficulty code: " + code);
    }

}
