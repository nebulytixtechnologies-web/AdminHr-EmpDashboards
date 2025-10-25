package com.neb.util;

import java.io.ByteArrayOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Image;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.neb.entity.Employee;
import com.neb.entity.Payslip;

public class PdfGeneratorUtil {

    public static byte[] createPayslipPdf(Employee emp, Payslip p) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        // Logo + Company Name
        Image logo = Image.getInstance("E:/NEBULYTIX TECHNOLOGIES/files/nebTechLogo.jpg");
        logo.scaleToFit(200f, 200f);
        logo.setAlignment(Element.ALIGN_RIGHT);
        document.add(logo);

        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        document.add(new Paragraph("NEBULYTIX TECHNOLOGIES PVT LTD", boldFont));
        document.add(new Paragraph("Payslip for the month: " + p.getPayslipMonth(), normalFont));
        document.add(new Paragraph("Dear " + emp.getFirstName() + " " + emp.getLastName()
                + " : " + emp.getId(), normalFont));
        document.add(new Paragraph("\n"));

        // ========================= Table 1 – Employee Info =========================
        PdfPTable table1 = new PdfPTable(2);
        table1.setWidthPercentage(100f);

        table1.addCell(createCellOuterColumnBorders("Location: " + "p.getLocation()", normalFont, true, true, true, false));
        table1.addCell(createCellOuterColumnBorders("P.F.No: " + emp.getPfNumber(), normalFont, true, false, true, false));

        table1.addCell(createCellOuterColumnBorders("Bank A/C No: " + emp.getBankAccountNumber()
                + "   Bank: " + emp.getBankName(), normalFont, false, true, true, false));
        table1.addCell(createCellOuterColumnBorders("E.P.S No: " + emp.getEpsNumber(), normalFont, false, false, true, false));

        table1.addCell(createCellOuterColumnBorders("No. of days paid: " + "p.getDaysPaid()", normalFont, false, true, true, false));
        table1.addCell(createCellOuterColumnBorders("PAN: " + emp.getPanNumber(), normalFont, false, false, true, false));

        table1.addCell(createCellOuterColumnBorders("", normalFont, false, true, true, false));
        table1.addCell(createCellOuterColumnBorders("UAN: " + emp.getUanNumber(), normalFont, false, false, true, false));

        table1.addCell(createCellOuterColumnBorders("", normalFont, false, true, true, false));
        table1.addCell(createCellOuterColumnBorders("ESI No.: " + emp.getEsiNumber(), normalFont, false, false, true, false));

        table1.addCell(createCellOuterColumnBorders("", normalFont, false, true, true, true));
        table1.addCell(createCellOuterColumnBorders("DOJ: " + emp.getJoiningDate(), normalFont, false, false, true, true));

        document.add(table1);
        document.add(new Paragraph("\n"));

        // ========================= Table 2 – Earnings / Deductions =========================
        PdfPTable table2 = new PdfPTable(3);
        table2.setWidthPercentage(100f);

        // Header row//last changed from false to true
        table2.addCell(createCellOuterColumnBorders("Earnings", boldFont, true, true, true, true));
        table2.addCell(createCellOuterColumnBorders("Statutory Deductions", boldFont, true, false, true, true));
        table2.addCell(createCellOuterColumnBorders("Scheme Deductions", boldFont, true, false, true, true));

        // Data rows
        table2.addCell(createCellOuterColumnBorders("Basic: " + p.getBasic(), normalFont, false, true, true, false));
        table2.addCell(createCellOuterColumnBorders("PF: " + p.getPfDeduction(), normalFont, false, false, true, false));
        table2.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));

        table2.addCell(createCellOuterColumnBorders("HRA: " + p.getHra(), normalFont, false, true, true, false));
        table2.addCell(createCellOuterColumnBorders("PROFTAX: " + p.getProfTaxDeduction(), normalFont, false, false, true, false));
        table2.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));

        table2.addCell(createCellOuterColumnBorders("Flexi: " + p.getFlexi(), normalFont, false, true, true, false));
        table2.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));
        table2.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));

        // Total row
        table2.addCell(createCellOuterColumnBorders("Earnings (Total): " + p.getGrossSalary(), normalFont, true, true, true, true));
        table2.addCell(createCellOuterColumnBorders("Deductions (Total): " + p.getTotalDeductions(), normalFont, true, false, true, true));
        table2.addCell(createCellOuterColumnBorders("Net Pay: " + p.getNetSalary(), normalFont, true, false, true, true));

        document.add(table2);
        document.add(new Paragraph("\n"));

        // ========================= Table 3 – Tax / Perks =========================
        PdfPTable table3 = new PdfPTable(4);
        table3.setWidthPercentage(100f);

        // Header row
        table3.addCell(createCellOuterColumnBorders("Perk Details", boldFont, true, true, true, true));
        table3.addCell(createCellOuterColumnBorders("Any other Income", boldFont, true, false, true, true));
        table3.addCell(createCellOuterColumnBorders("Annual exemption", boldFont, true, false, true, true));
        table3.addCell(createCellOuterColumnBorders("Form 16 Summary", boldFont, true, false, true, true));

        // Data rows – only last column contains data
        table3.addCell(createCellOuterColumnBorders("", normalFont, false, true, true, false));
        table3.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));
        table3.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));
        table3.addCell(createCellOuterColumnBorders("Gross Salary: " + p.getGrossSalary(), normalFont, false, false, true, false));

        table3.addCell(createCellOuterColumnBorders("", normalFont, false, true, true, false));
        table3.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));
        table3.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));
        table3.addCell(createCellOuterColumnBorders("Balance: " + "p.getBalance()", normalFont, false, false, true, false));

        table3.addCell(createCellOuterColumnBorders("", normalFont, false, true, true, false));
        table3.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));
        table3.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));
        table3.addCell(createCellOuterColumnBorders("Agg Deduction: " + "p.getAggDeduction()", normalFont, false, false, true, false));

        table3.addCell(createCellOuterColumnBorders("", normalFont, false, true, true, false));
        table3.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));
        table3.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));
        table3.addCell(createCellOuterColumnBorders("Income under Hd Salary: " + "p.getIncomeUnderHeadSalary()", normalFont, false, false, true, false));

        table3.addCell(createCellOuterColumnBorders("", normalFont, true, true, true, true));
        table3.addCell(createCellOuterColumnBorders("", normalFont, true, false, true, true));
        table3.addCell(createCellOuterColumnBorders("", normalFont, true, false, true, true));
        table3.addCell(createCellOuterColumnBorders("Tax credit: " + "p.getTaxCredit()", normalFont, true, false, true, true));

        document.add(table3);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("This is computer generated. Does not require seal/signature.", normalFont));

        document.close();
        return baos.toByteArray();
    }

    /**
     * Utility method to create a PdfPCell with selective borders.
     * Only outer edges and vertical lines are true/false.
     */
    private static PdfPCell createCellOuterColumnBorders(String text, Font font,
            boolean borderTop, boolean borderLeft, boolean borderRight, boolean borderBottom) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
        int border = 0;
        if (borderTop)    border |= PdfPCell.TOP;
        if (borderLeft)   border |= PdfPCell.LEFT;
        if (borderRight)  border |= PdfPCell.RIGHT;
        if (borderBottom) border |= PdfPCell.BOTTOM;
        cell.setBorder(border);
        cell.setBorderWidth(1f);
        return cell;
    }
}
