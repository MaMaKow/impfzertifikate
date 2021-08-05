/*
 * Copyright (C) 2021 asys
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.apothekeammarienplatz.impfzertifikate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author asys
 */
public class LoginSeite {

    WebDriver driver;

    public LoginSeite() {
        driver = Wrapper.getDriver();
    }

    public LoginSeite login() {
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();

        driver.get("https://dav.impfnachweis.info/login");
        try {
            //Das Cookie-Banner muss erst weggeklickt werden.
            driver.findElement(By.xpath("//*[@id=\"cn-save\"]")).click();
            System.out.println("Wir haben das Cookie-Banner geklickt.");
        } catch (Exception e) {
            //Wenn nicht, ist auch nicht schlimm.
            System.out.println("Es gab kein Cookie-Banner");
        }
        try {

            driver.findElement(By.id("email")).click();
            driver.findElement(By.id("email")).sendKeys(readPropertyFile.getImpfnachweisUser());
            driver.findElement(By.id("password")).click();
            driver.findElement(By.id("password")).sendKeys(readPropertyFile.getImpfnachweisPassword());
            driver.findElement(By.cssSelector(".justify-center:nth-child(1)")).click();
        } catch (Exception e) {
            System.out.println("Wir haben uns nicht eingeloggt.");
            System.out.println("Vielleicht waren wir schon drin?");
        }

        return new LoginSeite();
    }

}
