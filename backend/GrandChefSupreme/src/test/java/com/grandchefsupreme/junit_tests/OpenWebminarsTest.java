package com.grandchefsupreme.junit_tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class OpenWebminarsTest {

    @Test
    @Tag("Login")
    @DisplayName("Login 1")
    void pruebaLogin1() {
        System.out.println("Login 1 Successful!");
    }
    @Test
    @Tag("Login")
    @DisplayName("Login 2")
    void pruebaLogin2() {
        System.out.println("Login 2 Successful!");
    }
    @Test
    @Tag("Login")
    @DisplayName("Login 3")
    void pruebaLogin3() {
        System.out.println("Login 3 Successful!");
    }

    @Test
    @Tag("Login")
    @Tag("Logout")
    @DisplayName("Logout 1")
    void pruebaLogout1() {
        System.out.println("Logout 1 Successful!");
    }
    @Test
    @Tag("Login")
    @Tag("Logout")
    @DisplayName("Logout 2")
    void pruebaLogout2() {
        System.out.println("Logout 2 Successful!");
    }
    @Test
    @Tag("Login")
    @Tag("Logout")
    @DisplayName("Logout 3")
    void pruebaLogout3() {
        System.out.println("Logout 3 Successful!");
    }



}
