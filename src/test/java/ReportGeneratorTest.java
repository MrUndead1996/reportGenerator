import com.texuna.reportGenerator.ReportGeneratorImpl;
import com.texuna.reportGenerator.interfaces.ReportGenerator;
import com.texuna.reportGenerator.model.PageTemplate;
import com.texuna.reportGenerator.utils.XMLParserImpl;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReportGeneratorTest {
    private static final String SETTINGS_FILE_NAME = "settings.xml";
    private static final String DATA_FILE_NAME = "source-data.tsv";

    private static PageTemplate pageTemplate;
    private static List<String[]> data;
    String trueReport = "________________________________\n" +
            "| Номер    | Дата    | ФИО      |\n" +
            "________________________________\n" +
            "| 1        | 25/11   | Павлов   |\n" +
            "|          |         | Дмитрий  |\n" +
            "________________________________\n" +
            "| 2        | 26/11   | Павлов   |\n" +
            "|          |         | Конста   |\n" +
            "|          |         | нтин     |\n" +
            "________________________________\n" +
            "| 3        | 27/11   | Н/Д      |\n" +
            "________________________________\n" +
            "| 4        | 28/11   | Ким      |\n" +
            "|          |         | Чен Ир   |\n" +
            "________________________________\n" +
            "~\n" +
            "________________________________\n" +
            "| Номер    | Дата    | ФИО      |\n" +
            "________________________________\n" +
            "| 5        | 29/     | Юлианна- |\n" +
            "|          | 11/2009 | Оксана   |\n" +
            "|          |         | Сухово-  |\n" +
            "|          |         | Кобыл    |\n" +
            "|          |         | ина      |\n" +
            "________________________________\n";

    @BeforeClass
    public static void beforeClass() throws ParserConfigurationException, IllegalAccessException, SAXException, IOException {
        XMLParserImpl xmlParser = new XMLParserImpl();
        InputStream settingsStream = ReportGeneratorTest.class.getResourceAsStream(SETTINGS_FILE_NAME);
        InputStream dataStream = ReportGeneratorTest.class.getResourceAsStream(DATA_FILE_NAME);
        pageTemplate = xmlParser.parseXML(settingsStream);
        TsvParserSettings settings = new TsvParserSettings();
        TsvParser parser = new TsvParser(settings);
        data = parser.parseAll(dataStream, StandardCharsets.UTF_16);
    }


    @Test
    public void createReportTest() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        List<String> report = reportGenerator.createReport(pageTemplate, data);
        StringBuilder builder = new StringBuilder();
        report.forEach(builder::append);
        assertEquals(trueReport, builder.toString());
    }
}
