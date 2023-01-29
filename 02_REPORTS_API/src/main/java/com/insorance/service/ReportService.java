package com.insorance.service;

import com.insorance.dto.SearchRequest;
import com.insorance.dto.SearchResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface ReportService {

    public List<String > getUniquePlanName();
    public  List<String > getUniquePlanStatus();
    public List<SearchResponse> search(SearchRequest  request);
    public void generateExcel(HttpServletResponse response) throws Exception;
    public void generatePdf(HttpServletResponse response) throws Exception;
}
