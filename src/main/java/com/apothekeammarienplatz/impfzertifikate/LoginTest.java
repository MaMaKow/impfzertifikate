package com.apothekeammarienplatz.impfzertifikate;
// Generated by Selenium IDE

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;

public class LoginTest {

    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        driver = Wrapper.getDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void login() {
        // Test name: Login
        // Step # | name | target | value
        // 1 | open | https://www.mein-apothekenportal.de/ |
        driver.get("https://www.mein-apothekenportal.de/");
        // 3 | click | linkText=Anmelden |
        driver.findElement(By.linkText("Anmelden")).click();
        // 4 | click | css=.justify-center:nth-child(1) |
        driver.findElement(By.cssSelector(".justify-center:nth-child(1)")).click();
    }
}
