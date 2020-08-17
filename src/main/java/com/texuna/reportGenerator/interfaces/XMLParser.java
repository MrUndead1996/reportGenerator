package com.texuna.reportGenerator.interfaces;

import com.texuna.reportGenerator.model.PageTemplate;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public interface XMLParser {

    PageTemplate parseXML(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException, IllegalAccessException;
}
