/*
 * Copyright (C) 2021 Mandelkow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.apothekeammarienplatz.impfzertifikate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mandelkow
 * @see
 * https://medium.com/@sonaldwivedi/how-to-read-config-properties-file-in-java-6a501dc96b25
 * https://www.toolsqa.com/selenium-cucumber-framework/read-configurations-from-property-file/
 */
public class ReadPropertyFile {

    private Properties properties;
    private final String propertyFilePath = "Configuration.properties";

    public ReadPropertyFile() {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(propertyFilePath);
            properties.load(fileInputStream);
        } catch (FileNotFoundException exception) {
            Logger.getLogger(ReadPropertyFile.class.getName()).log(Level.SEVERE, null, exception);
        } catch (IOException exception) {
            Logger.getLogger(ReadPropertyFile.class.getName()).log(Level.SEVERE, null, exception);
        }

    }

    public String getEmailFrom() {
        String emailFrom = properties.getProperty("emailFrom");
        if (null != emailFrom) {
            return emailFrom;
        }
        throw new RuntimeException("emailFrom not specified in the Configuration.properties file.");
    }

    public String getEmailUserName() {
        String emailUserName = properties.getProperty("emailUserName");
        if (null != emailUserName) {
            return emailUserName;
        }
        throw new RuntimeException("emailUserName not specified in the Configuration.properties file.");
    }

    public String getEmailUserPassword() {
        String emailUserPassword = properties.getProperty("emailUserPassword");
        if (null != emailUserPassword) {
            return emailUserPassword;
        }
        throw new RuntimeException("emailUserPassword not specified in the Configuration.properties file.");
    }

    public String getEmailHost() {
        String emailHost = properties.getProperty("emailHost");
        if (null != emailHost) {
            return emailHost;
        }
        throw new RuntimeException("emailHost not specified in the Configuration.properties file.");
    }

    public String getEmailPort() {
        String emailPort = properties.getProperty("emailPort");
        if (null != emailPort) {
            return emailPort;
        }
        return "587";
    }

}
