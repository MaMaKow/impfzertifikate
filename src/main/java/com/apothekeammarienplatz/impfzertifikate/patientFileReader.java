package com.apothekeammarienplatz.impfzertifikate;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;

/*
 * Copyright (C) 2021 Mandelkow
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
/**
 *
 * @author Mandelkow
 */
public class patientFileReader {

    public static void main(String[] args) throws IOException, CsvException, Exception {
        String todoFilePath = "todo.csv";
        try {

            InputStreamReader reader = new InputStreamReader(new FileInputStream(todoFilePath), "UTF-8");
            String[] patientLineStrings;

            List<String[]> patientLineStringsList;
            patientLineStringsList = getPatientLineList(reader);
            LoginSeite loginSeite = new LoginSeite();
            loginSeite.login();
            for (int lineNumber = 0; lineNumber < patientLineStringsList.size(); lineNumber++) {
                patientLineStrings = patientLineStringsList.get(lineNumber);
                Patient patient = new Patient(patientLineStrings);

                ZertifikatsSeite zertifikatsSeite = new ZertifikatsSeite();
                zertifikatsSeite = zertifikatsSeite.stelleZertifikatAus(patient, "1");
                zertifikatsSeite = zertifikatsSeite.ladeZertifikateHerunter();
                Thread.sleep(1000);

                if (!patient.getZweiteImpfungDatum().equals("")) {
                    zertifikatsSeite = zertifikatsSeite.stelleZertifikatAus(patient, "2");
                    zertifikatsSeite = zertifikatsSeite.ladeZertifikateHerunter();
                }
                try {
                    //SendEmail sendEmail = new SendEmail(patient.getVorname(), patient.getNachname(), patient.getEmail());
                } catch (Exception e) {
                    System.out.println("Es wurde vermutlich keine Mail gesendet.");
                }
                System.out.println(patient.getVorname() + " " + patient.getNachname() + " wurde am " + patient.getErsteImpfungDatum() + " mit " + patient.getErsteImpfungStoff() + " geimpft.");
                writePatientLineList(patientLineStrings); //Schreibe zu done.csv
                //PatientFileLineRemover patientFileLineRemover = new PatientFileLineRemover();
                //patientFileLineRemover.delete(todoFilePath, lineNumber, 1);
            }
        } catch (Exception exception) {
            System.out.println(exception);
            System.out.println("Konnte das CSV nicht lesen.");
        }
    }

    public static List<String[]> getPatientLineList(Reader reader) throws IOException, CsvValidationException {
        List<String[]> list = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(reader)) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                list.add(line);
            }
            reader.close();
        }
        return list;
    }

    public static void writePatientLineList(String[] array) throws Exception {
        String doneFilePath = "done.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(doneFilePath, true));
        writer.writeNext(array);
        writer.close();
    }
}
