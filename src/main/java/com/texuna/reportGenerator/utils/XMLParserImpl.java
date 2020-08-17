package com.texuna.reportGenerator.utils;

import com.texuna.reportGenerator.interfaces.XMLParser;
import com.texuna.reportGenerator.model.Column;
import com.texuna.reportGenerator.model.PageTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class XMLParserImpl implements XMLParser {
    /**
     * DOM XML parsing file to page template
     * @param inputStream File input stream for parsing
     * @return Page template
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws IllegalAccessException
     */
    @Override
    public PageTemplate parseXML(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException, IllegalAccessException {
        PageTemplate result = new PageTemplate();
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
        NodeList pageSettings = document.getElementsByTagName("page").item(0).getChildNodes();
        NodeList columnsSettings = document.getElementsByTagName("column");
        for (int i = 0; i < pageSettings.getLength(); i++) {
            Node node = pageSettings.item(i);
            Field[] fields = result.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (node.getNodeName().equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(result, node.getChildNodes().item(0).getNodeValue());
                    field.setAccessible(false);
                }
            }
        }
        for (int i = 0; i < columnsSettings.getLength(); i++) {
            NodeList columns = columnsSettings.item(i).getChildNodes();
            Column column = new Column();
            Field[] fields = column.getClass().getDeclaredFields();
            for (int j = 0; j < columns.getLength(); j++) {
                Node node = columns.item(j);
                for (Field field : fields) {
                    if (node.getNodeName().equals(field.getName())) {
                        field.setAccessible(true);
                        field.set(column, node.getChildNodes().item(0).getNodeValue());
                        field.setAccessible(false);
                    }
                }
            }
            result.getColumns().add(column);
        }
        return result;
    }

}
