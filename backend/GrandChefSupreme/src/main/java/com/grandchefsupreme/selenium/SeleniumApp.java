package com.grandchefsupreme.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;


public class SeleniumApp {

    public static void main(String[] args) {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));


        driver.get("https://www.saucedemo.com");
        System.out.println("Page title is: " + driver.getTitle());

        try {
            Thread.sleep(2000); // Pause to see the browser
        } catch (InterruptedException e) {
            throw  new RuntimeException(e);
        }

        driver.quit();

    }
}
