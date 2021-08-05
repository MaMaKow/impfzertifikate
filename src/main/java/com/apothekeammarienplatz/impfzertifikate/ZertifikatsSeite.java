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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public ZertifikatsSeite stelleZertifikatAus(Patient patient, String iterator) throws Exception {
        ReadPropertyFile readPropertyFile = new ReadPropertyFile();
        String impfstoff;
        String datum;
        int selectIndex;
        if (iterator.equals("1")) {
            impfstoff = patient.getErsteImpfungStoff();
            datum = patient.getErsteImpfungDatum();
            selectIndex = 0;
        } else if (iterator.equals("2")) {
            impfstoff = patient.getZweiteImpfungStoff();
            datum = patient.getZweiteImpfungDatum();
            selectIndex = 1;
        } else {
            throw new Exception("Es wurden erst die Erstimpfung oder Zweitimpfung implementiert.");
        }

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
        Thread.sleep(500);
        driver.findElement(By.id("doseNumber")).click();
        {
            WebElement dropdown = driver.findElement(By.id("doseNumber"));
            //dropdown.findElement(By.xpath("//option[position()=" + iterator + "]")).click();
            Select dropdownSelect = new Select(dropdown);
            dropdownSelect.selectByIndex(selectIndex);
        }
        //driver.findElement(By.xpath("//*[@id=\"app\"]/button[text()[contains(.,'Hinzufügen')]]")).click();
        driver.findElement(By.cssSelector(".rounded-md > .bg-red-600")).click();
        return new ZertifikatsSeite();
    }

    public ZertifikatsSeite ladeZertifikateHerunter() throws InterruptedException {
        //Thread.sleep(5000);
        WebDriverWait wait = new WebDriverWait(driver, 20);

        By downloadButtonBy = By.xpath("//*[contains (text(), 'PDF herunterladen')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(downloadButtonBy));
        WebElement element = driver.findElement(downloadButtonBy);
        element.click();

        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(downloadButtonBy)); //Jetzt ist der Download fertig
        driver.findElement(By.linkText("Zurück zur Übersicht")).click();
        /*
         */
        return new ZertifikatsSeite();
    }
}
