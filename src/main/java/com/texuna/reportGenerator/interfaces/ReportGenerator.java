package com.texuna.reportGenerator.interfaces;

import com.texuna.reportGenerator.model.PageTemplate;

import java.util.List;

public interface ReportGenerator {
    List<String> createReport(PageTemplate template, List<String[]> data);
}
