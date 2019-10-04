package com.broodcamp.util;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Simple error handler use by {@link XmlUtil}.
 * 
 * @author Edward P. Legaspi <czetsuya@gmail.com>
 **/
public class SimpleErrorHandler implements ErrorHandler {

	public void warning(SAXParseException e) throws SAXException {
		System.out.println(e.getMessage());
	}

	public void error(SAXParseException e) throws SAXException {
		System.out.println(e.getMessage());
	}

	public void fatalError(SAXParseException e) throws SAXException {
		System.out.println(e.getMessage());
	}

}
