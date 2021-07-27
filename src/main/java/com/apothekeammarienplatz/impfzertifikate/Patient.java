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

/**
 *
 * @author asys
 */
public class Patient {

    String vorname;
    String nachname;
    String geburtsDatum;
    String email;

    String ersteImpfungDatum;
    String ersteImpfungStoff;
    String ersteImpfungErste;

    String zweiteImpfungDatum;
    String zweiteImpfungStoff;
    String zweiteImpfungZweite;

    public Patient(String[] patientLineStrings) {

        vorname = patientLineStrings[0];
        nachname = patientLineStrings[1];
        geburtsDatum = patientLineStrings[2];
        email = patientLineStrings[9];

        ersteImpfungDatum = patientLineStrings[3];
        ersteImpfungStoff = patientLineStrings[4];
        ersteImpfungErste = patientLineStrings[5];

        try {
            zweiteImpfungDatum = patientLineStrings[6];
            zweiteImpfungStoff = patientLineStrings[7];
            zweiteImpfungZweite = patientLineStrings[8];
        } catch (Exception e) {
            System.out.println("Es gab nur eine Impfung");
        }
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getGeburtsDatum() {
        return geburtsDatum;
    }

    public String getEmail() {
        return email;
    }

    public String getErsteImpfungDatum() {
        return ersteImpfungDatum;
    }

    public String getErsteImpfungStoff() {
        return ersteImpfungStoff;
    }

    public String getErsteImpfungErste() {
        return ersteImpfungErste;
    }

    public String getZweiteImpfungDatum() {
        return zweiteImpfungDatum;
    }

    public String getZweiteImpfungStoff() {
        return zweiteImpfungStoff;
    }

    public String getZweiteImpfungZweite() {
        return zweiteImpfungZweite;
    }

}
