package com.apothekeammarienplatz.impfzertifikate;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author asys
 */
public class patientFileReader {

    public static void main(String[] args) throws IOException, CsvException, Exception {
        String todoFilePath = "todo.csv";
        try (FileReader reader = new FileReader(todoFilePath)) {
            String[] patientLineStrings;

            List<String[]> patientLineStringsList;
            patientLineStringsList = getPatientLineList(reader);

            for (int lineNumber = 0; lineNumber < patientLineStringsList.size(); lineNumber++) {
                patientLineStrings = patientLineStringsList.get(lineNumber);
                Patient patient = new Patient(patientLineStrings);
                System.out.println("patient.getZweiteImpfungDatum()");
                System.out.println(patient.getZweiteImpfungDatum());
                String nullvarString = null;
                System.out.println(nullvarString);

                ZertifikatsSeite zertifikatsSeite = new ZertifikatsSeite();
                zertifikatsSeite.stelleZertifikatAus(patient, "1");
                if (!patient.getZweiteImpfungDatum().equals("")) {
                    zertifikatsSeite.stelleZertifikatAus(patient, "2");
                }
                /* */
                System.out.println(patient.getVorname() + " " + patient.getNachname() + " wurde am " + patient.getErsteImpfungDatum() + " mit " + patient.getErsteImpfungStoff() + " geimpft.");
                writePatientLineList(patientLineStrings);
                PatientFileLineRemover patientFileLineRemover = new PatientFileLineRemover();
                patientFileLineRemover.delete(todoFilePath, lineNumber, 1);
            }
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
