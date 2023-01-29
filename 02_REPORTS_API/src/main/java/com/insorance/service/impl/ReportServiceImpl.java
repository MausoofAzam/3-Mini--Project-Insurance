package com.insorance.service.impl;
import com.insorance.dto.SearchRequest;
import com.insorance.dto.SearchResponse;
import com.insorance.entity.EligibilityDetails;
import com.insorance.repository.EligibilityDetailsRepo;
import com.insorance.service.ReportService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import com.lowagie.text.pdf.PdfPTable;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EligibilityDetailsRepo eligRepo;

    @Override
    public List<String> getUniquePlanName() {
        List<String> planNames = eligRepo.findPlanNames();
        return planNames;
    }

    @Override
    public List<String> getUniquePlanStatus() {
        return eligRepo.findPlanStatuses();
    }

    @Override
    public List<SearchResponse> search(SearchRequest request) {
        List<SearchResponse> response = new ArrayList<>();
        EligibilityDetails queryBuilder = new EligibilityDetails();
        String planName = request.getPlanName();
        if (planName != null && planName.equals("")) {
            //select * from Eligibility_Details where plan_Name = planName;
            queryBuilder.setPlanName(planName);
        }
        String planStatus = request.getPlanStatus();
        //select * from Eligibility_Details where plan_status = planStatus;

        if (planStatus != null && planStatus.equals("")) {
            queryBuilder.setPlanStatus(planStatus);
        }
        //select * from Eligibility_Details where plan_start_date = planStartDate;

        LocalDate planStartDate = request.getPlanStartDate();
        if (planStartDate != null) {
            queryBuilder.setPlanStartDate(planStartDate);
        }
        //select * from Eligibility_Details where plan_End_Date = planEndDate;

        LocalDate planEndDate = request.getPlanEndDate();
        if (planEndDate != null) {
            queryBuilder.setPlanEndDate(planEndDate);
        }
        //example is the predefined interface in the data jpa to create the query dynamically.
        //i.e select * from Eligibility_Details where plan_Name=planName;
        Example<EligibilityDetails> example = Example.of(queryBuilder);
        //will get All the record without selecting any parameter by hitting search.user is not selecting anything
        //user will get all the record
        List<EligibilityDetails> entities = eligRepo.findAll(example);
        for (EligibilityDetails entitiy : entities) {
            SearchResponse sr = new SearchResponse();
            BeanUtils.copyProperties(entitiy, sr);
            response.add(sr);
        }
        return response;
    }

    @Override
    public void generateExcel(HttpServletResponse response) throws Exception {
        List<EligibilityDetails> entities = eligRepo.findAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow headerRow = sheet.createRow(0);
        //five cell will be in Excel row
        headerRow.createCell(0).setCellValue("Elig-ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("Mobile");
        headerRow.createCell(4).setCellValue("Gender");
        headerRow.createCell(5).setCellValue("SSN");

        int i = 1;
        for(EligibilityDetails entity : entities) {

            HSSFRow dataRow = sheet.createRow(i++);
            dataRow.createCell(0).setCellValue(entity.getEligId());
            dataRow.createCell(1).setCellValue(entity.getName());
            dataRow.createCell(2).setCellValue(entity.getEmail());
            dataRow.createCell(3).setCellValue(entity.getMobile());
            dataRow.createCell(4).setCellValue(String.valueOf(entity.getGender()));
            dataRow.createCell(5).setCellValue(entity.getSsn());
        }

        // attaching workbook to response
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


    @Override
    public void generatePdf(HttpServletResponse response) throws Exception {
        // creating document pdf
        List<EligibilityDetails> entities = eligRepo.findAll();
        Document document = new Document(PageSize.A4);
        // to write the data in the pdf we use pdf writer
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        font.setSize(18);
        font.setColor(Color.darkGray);

        Paragraph p = new Paragraph("Search Report", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);
//five cells in the pdf row
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 1.5f, 3.0f });
        table.setSpacingBefore(10);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);


        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Phno", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Gender", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Ssn", font));
        table.addCell(cell);

        // when the loop is executed next row will generate
        for (EligibilityDetails entity : entities) {
            table.addCell(entity.getName());
            table.addCell(entity.getEmail());
            table.addCell(String.valueOf(entity.getMobile()));
            table.addCell(String.valueOf(entity.getGender()));
            table.addCell(String.valueOf(entity.getSsn()));

        }
        document.add(table);
        document.close();
    }
}