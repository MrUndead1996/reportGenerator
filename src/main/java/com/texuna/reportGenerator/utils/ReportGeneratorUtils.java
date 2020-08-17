package com.texuna.reportGenerator.utils;

import com.texuna.reportGenerator.model.Column;
import com.texuna.reportGenerator.model.PageTemplate;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class ReportGeneratorUtils {

    /**
     * Get integer cell high value
     * @param template Page template from settings
     * @param data Strings data for report
     * @return Report cell height
     */
    public static int getCellHeight(PageTemplate template, List<String> data) {
        int[] elementLengths = new int[data.size()];
        int[] columnWidths = new int[data.size()];
        for (String string : data) {
            elementLengths[data.indexOf(string)] = string.length();
        }
        for (Column column : template.getColumns()) {
            columnWidths[template.getColumns().indexOf(column)] = Integer.parseInt(column.getWidth());
        }
        Arrays.sort(elementLengths);
        Arrays.sort(columnWidths);
        int maxElementLengths = elementLengths[elementLengths.length - 1];
        int columnWidthForElement = columnWidths[columnWidths.length - 1];
        for (String elementValue : data) {
            if (elementValue.length() == maxElementLengths)
                columnWidthForElement = Integer.parseInt(template.getColumns().get(data.indexOf(elementValue)).getWidth());
        }
        double cellHeightDouble = (double) maxElementLengths / columnWidthForElement;
        if (maxElementLengths % columnWidthForElement > 0)
            return (int) (cellHeightDouble + 1);
        return (int) cellHeightDouble;
    }

    /**
     * Save with stream in hard drive
     *
     * @param report   Report to save in hard drive
     * @param fileName Name for file to save
     */
    public static void saveReportToFile(List<String> report, String fileName) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_16);
        for (String s : report) {
            writer.write(s);
        }
        writer.flush();
        writer.close();
    }

    /**
     * Get string data from TSV file
     * @param inputStream Stream from TSV file
     * @return List of data
     */
    public static List<String[]> getDataFromFile(InputStream inputStream) {
        TsvParserSettings settings = new TsvParserSettings();
        TsvParser parser = new TsvParser(settings);
        return parser.parseAll(inputStream, StandardCharsets.UTF_16);
    }
}
