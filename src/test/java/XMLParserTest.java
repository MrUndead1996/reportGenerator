import com.texuna.reportGenerator.model.PageTemplate;
import com.texuna.reportGenerator.utils.XMLParserImpl;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

import static org.junit.Assert.assertEquals;

public class XMLParserTest {

    @Test
    public void parseXMLTest() throws IOException, IllegalAccessException, SAXException, ParserConfigurationException {
        XMLParserImpl parser = new XMLParserImpl();
        InputStream inputStream = getClass().getResourceAsStream("settings.xml");

        PageTemplate page = parser.parseXML(inputStream);
        assertEquals("32", page.getWidth());
        assertEquals("12", page.getHeight());
        assertEquals(3,page.getColumns().size());
        assertEquals("Номер", page.getColumns().get(0).getTitle());
        assertEquals("8", page.getColumns().get(0).getWidth());
        assertEquals("7", page.getColumns().get(1).getWidth());
        assertEquals("7", page.getColumns().get(2).getWidth());
    }
}
