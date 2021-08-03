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

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author asys
 */
public class ZertifikatsSeite {

    WebDriver driver;

    public ZertifikatsSeite() {
        driver = Wrapper.getDriver();
    }

    public void stelleZertifikatAus(String vorname, String nachname, String geburtsdatum, String impfstoff, String datum, String iterator) throws Exception {
        throw new Exception("Not implemented yet!");

    }

    public void stelleZertifikatAus(Patient patient, String iterator) throws Exception {
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        String impfstoff;
        String datum;
        if (iterator.equals("1")) {
            impfstoff = patient.getErsteImpfungStoff();
            datum = patient.getErsteImpfungDatum();
        } else if (iterator.equals("2")) {
            impfstoff = patient.getZweiteImpfungStoff();
            datum = patient.getZweiteImpfungDatum();

        } else {
            throw new Exception("Es wurden erst die Erstimpfung oder Zweitimpfung implementiert.");
        }

        driver.get("https://dav.impfnachweis.info/login");
        try {
            //Das Cookie-Banner muss erst weggeklickt werden.
            driver.findElement(By.xpath("//*[@id=\"cn-save\"]")).click();
            System.out.println("Wir haben das Cookie-Banner geklickt.");
        } catch (Exception e) {
            //Wenn nicht, ist auch nicht schlimm.
            System.out.println("Es gab kein Cookie-Banner");
        }
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys(readPropertyFile.getImpfnachweisUser());
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).sendKeys(readPropertyFile.getImpfnachweisPassword());
        driver.findElement(By.cssSelector(".justify-center:nth-child(1)")).click();
        driver.findElement(By.linkText("Ausstellen")).click();
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys(patient.getVorname());
        driver.findElement(By.id("lastName")).sendKeys(patient.getNachname());
        driver.findElement(By.id("birthdate")).sendKeys(patient.getGeburtsDatum());
        driver.findElement(By.id("vaccine")).click();
        {
            WebElement dropdown = driver.findElement(By.id("vaccine"));
            dropdown.findElement(By.xpath("//*[text()[contains(.,'" + impfstoff + "')]]")).click();
        }
        Thread.sleep(500);
        driver.findElement(By.id("vaccinationDate")).click();
        driver.findElement(By.id("vaccinationDate")).sendKeys(datum);
        driver.findElement(By.id("doseNumber")).click();
        {
            WebElement dropdown = driver.findElement(By.id("doseNumber"));
            dropdown.findElement(By.xpath("//option[position()=" + iterator + "]")).click();
        }
        //driver.findElement(By.xpath("//*[@id=\"app\"]/button[text()[contains(.,'Hinzufügen')]]")).click();

        driver.findElement(By.cssSelector(".rounded-md > .bg-red-600")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".mt-4:nth-child(2) .inline-flex > .inline-flex"));
            WebElement element = driver.findElement(By.cssSelector("#app > div.flex-1.flex.flex-col.bg-gray-100 > div > div > div:nth-child(2) > div > div.max-w-2xl.mx-auto.px-4.sm\\:px-6.lg\\:px-8 > div > div > div:nth-child(2) > div > span > button"));
            WebElement element = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div[2]/div/div[1]/div/div/div[2]/div/span/button"));
            WebElement element = driver.findElement(By.xpath("//*[@id=\"app\"]/div[2]/div/div/div[2]/div/div[1]/div/div/div[2]/div/span/button"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            List<WebElement> elements = driver.findElements(By.cssSelector(".mt-4:nth-child(2) .inline-flex > .inline-flex"));

            assert (elements.size() > 0);
        }
        Thread.sleep(30000);
        /*
         */
    }
}
