package com.grandchefsupreme.nested_test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class NestedTest {


    @Test
    @DisplayName("Logout 1")
    void pruebaLogout1() {
        System.out.println("Logout 1 Successful!");
    }
    @Test
    @DisplayName("Logout 2")
    void pruebaLogout2() {
        System.out.println("Logout 2 Successful!");
    }
    @Test
    @DisplayName("Logout 3")
    void pruebaLogout3() {
        System.out.println("Logout 3 Successful!");
    }


    @Nested
    @DisplayName("Nested CONTACT METHODS")
    class contact_methods {

        @Test
        @DisplayName("Test Contact Method 1")
        void pruebaContactMethod1() {
            System.out.println("Logout 1 Contact Successful!");
        }
        @Test
        @DisplayName("Test Contact Method 2")
        void pruebaContactMethod2() {
            System.out.println("Logout 2 Contact Successful!");
        }
        @Test
        @DisplayName("Test Contact Method 3")
        void pruebaContactMethod3() {
            System.out.println("Logout 3 Contact Successful!");
        }


    }

}
