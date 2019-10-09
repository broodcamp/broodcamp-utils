/**
 * (C) Copyright 2019 Edward P. Legaspi (https://github.com/czetsuya).
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.broodcamp.util;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Collection of utility methods for working with xml.
 * 
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public class XmlUtil {

    private XmlUtil() {

    }

    /**
     * Validates if a given string is a valid xml format.
     * 
     * @param xml XML string
     * @return true if valid, false otherwise
     * @throws ParserConfigurationException if the factory cannot create this feature
     */
    public static boolean validate(String xml) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            return false;
        }

        builder.setErrorHandler(new SimpleErrorHandler());

        try {
            builder.parse(new InputSource(new StringReader(xml)));
        } catch (SAXException | IOException e) {
            return false;
        }

        return true;
    }
}
