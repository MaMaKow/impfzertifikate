package com.apothekeammarienplatz.impfzertifikate;

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
/**
 *
 * @author asys
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class PatientFileLineRemover {

    void delete(String filename, int startline, int numlines) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

            //String buffer to store contents of the file
            StringBuffer stringBuffer = new StringBuffer("");

            //Keep track of the line number
            int linenumber = 0;
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                //Store each valid line in the string buffer
                if (linenumber < startline || linenumber >= startline + numlines) {
                    stringBuffer.append(line + "\n");
                }
                linenumber++;
            }
            if (startline + numlines > linenumber) {
                System.out.println("End of file reached.");
            }
            bufferedReader.close();

            FileWriter fileWriter = new FileWriter(new File(filename));
            //Write entire string buffer into the file
            fileWriter.write(stringBuffer.toString());
            fileWriter.close();
        } catch (Exception exception) {
            System.out.println("Something went horribly wrong: " + exception.getMessage());
        }
    }
}
