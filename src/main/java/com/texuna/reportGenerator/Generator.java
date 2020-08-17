package com.texuna.reportGenerator;

import com.texuna.reportGenerator.interfaces.ReportGenerator;
import com.texuna.reportGenerator.model.PageTemplate;
import com.texuna.reportGenerator.utils.ReportGeneratorUtils;
import com.texuna.reportGenerator.utils.XMLParserImpl;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Generator {
    public static void main(String[] args) {

        XMLParserImpl xmlParser = new XMLParserImpl();
        ReportGenerator reportGenerator = new ReportGeneratorImpl();

        String settingFileName = args[0];
        String dataFileName = args[1];
        String reportOutputPath = args[2];

        File settingsFile = new File("./" + settingFileName);
        File dataFile = new File("./" + dataFileName);


        try( InputStream settingsStream = new FileInputStream(settingsFile.getAbsolutePath());
             InputStream dataStream = new FileInputStream(dataFile.getAbsolutePath());) {
            PageTemplate template = xmlParser.parseXML(settingsStream);
            List<String[]> data = ReportGeneratorUtils.getDataFromFile(dataStream);
            List<String> report = reportGenerator.createReport(template,data);

            ReportGeneratorUtils.saveReportToFile(report, reportOutputPath);

        } catch (IllegalAccessException | ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

    }
}
