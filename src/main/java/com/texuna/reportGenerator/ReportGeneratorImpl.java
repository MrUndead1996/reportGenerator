package com.texuna.reportGenerator;

import com.texuna.reportGenerator.interfaces.ReportGenerator;
import com.texuna.reportGenerator.model.PageTemplate;
import com.texuna.reportGenerator.utils.ReportGeneratorUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ReportGeneratorImpl implements ReportGenerator {

    private static final String ROWS_BREAK_CHAR = "-";
    private static final String PAGE_BREAK_CHAR = "~";
    private static final String COLUMN_BREAK_CHAR = "|";
    private static final String NEW_LINE_CHAR = "\n";
    private static final String SPLIT_REGEX = "\\s|[^а-яА-Я0-9_]";

    /**
     * Create report from data
     * @param template Report page template
     * @param data Provided data for report
     * @return List of report rows
     */
    public List<String> createReport(PageTemplate template, List<String[]> data) {
        final int PAGE_HEIGHT = Integer.parseInt(template.getHeight());
        List<String> headers = getHeaders(template);
        List<String> report = new ArrayList<>();
        AtomicInteger totalRowCounter = new AtomicInteger();
        report.add(createReportLine(template, headers, true));
        data.forEach(strings -> {
            int localRowCounter;
            String reportRow = createReportLine(template, Arrays.asList(strings), false);
            localRowCounter = (int) Arrays.stream(reportRow.split("")).filter(s -> s.toLowerCase().equals(NEW_LINE_CHAR)).count() - 1;
            if (totalRowCounter.get() + localRowCounter > PAGE_HEIGHT) {
                report.add(PAGE_BREAK_CHAR);
                report.add(NEW_LINE_CHAR);
                report.add(createReportLine(template, headers, true));
                totalRowCounter.set(0);
            } else
                totalRowCounter.addAndGet(localRowCounter);
            report.add(reportRow);
        });
        return report;
    }

    /**
     * Get report row
     * @param template Page template
     * @param data Provided data for row
     * @param isTitle If true print headers break line
     * @return Report row
     */
    private String createReportLine(PageTemplate template, List<String> data, boolean isTitle) {

        int cellHeight = ReportGeneratorUtils.getCellHeight(template, data);
        String[] columnsDataArray = data.toArray(String[]::new);
        StringBuilder rowBuilder = new StringBuilder("");

        // isTitle upper break line
        if (isTitle)
            rowBuilder
                    .append(ROWS_BREAK_CHAR.repeat(Integer.parseInt(template.getWidth())))
                    .append(NEW_LINE_CHAR);

        // create report row
        for (int i = 0; i < cellHeight; i++) {
            int pageWidth = Integer.parseInt(template.getWidth());
            // loop for having data
            for (int j = 0; j < columnsDataArray.length; j++) {
                String value = columnsDataArray[j];
                int cellWidth = Integer.parseInt(template.getColumns().get(j).getWidth());

                // if word larger cell width split of space, non-character or digit
                if (value.length() > cellWidth) {
                    String[] sub = value.split(SPLIT_REGEX);

                    //if part of word larger cell width split centered
                    if (sub[0].length() > cellWidth) {
                        value = sub[0].substring(0, sub[0].length() / 2 + 1);
                    }
                    // else take word + 1
                    else {
                        value = value.substring(0, sub[0].length() + 1);
                    }
                }

                // replace value from work basket
                columnsDataArray[j] = columnsDataArray[j].replace(value, "");

                int count = cellWidth - value.length() + 1;
                // manage page size, create row from value
                rowBuilder
                        .append(COLUMN_BREAK_CHAR)
                        .append(" ")
                        .append(value)
                        .append(" ".repeat(Math.max(0, count)));
            }
            if (rowBuilder.length() < pageWidth) {
                rowBuilder.append(" ".repeat(pageWidth - rowBuilder.length() - 1));
            }
            rowBuilder.append(COLUMN_BREAK_CHAR);
            rowBuilder.append(NEW_LINE_CHAR);
        }
        // footer break line
        rowBuilder
                .append(ROWS_BREAK_CHAR.repeat(Integer.parseInt(template.getWidth())))
                .append(NEW_LINE_CHAR);

        return rowBuilder.toString();
    }

    /**
     * Get headers from template
     * @param pageTemplate Page template
     * @return List of headers
     */
    private List<String> getHeaders(PageTemplate pageTemplate) {
        List<String> headers = new ArrayList<>();
        pageTemplate.getColumns().forEach(column -> headers.add(column.getTitle()));
        return headers;
    }
}
