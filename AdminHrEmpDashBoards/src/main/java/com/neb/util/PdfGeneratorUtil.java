/**
 * ---------------------------------------------------------------------
 * File Name   : PdfGeneratorUtil.java
 * Package     : com.neb.util
 * ---------------------------------------------------------------------
 * Purpose :
 *   This utility class is used to generate employee payslips in PDF format.
 *   It takes employee and payslip details and creates a professional PDF file.
 *
 * Description :
 *   - Uses the iText library to build the PDF layout.
 *   - Adds company logo, employee details, salary breakdown, and tax info.
 *   - Returns the generated PDF as a byte array for download or email.
 *
 * Main Method :
 *   createPayslipPdf(Employee emp, Payslip p)
 *      → Creates and formats the payslip PDF using the employee and payslip data.
 *      → Adds sections like:
 *          1. Company name and logo
 *          2. Employee information (bank, PAN, UAN, etc.)
 *          3. Salary details (earnings, deductions, net pay)
 *          4. Tax and perk information
 *          5. Footer note
 *
 * Helper Method :
 *   createCellOuterColumnBorders(...)
 *      → Helps design table cells with borders on specific sides
 *        (top, left, right, bottom).
 *
 * Output :
 *   - The method returns the PDF content as a byte array.
 *   - This can be saved, downloaded, or sent as an email attachment.
 *
 * Example :
 *   byte[] pdf = PdfGeneratorUtil.createPayslipPdf(employee, payslip);
 *   // Use the byte array to send response or save file
 * ---------------------------------------------------------------------
 */

package com.neb.util;

import java.io.ByteArrayOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.neb.entity.Employee;
import com.neb.entity.Payslip;

public class PdfGeneratorUtil {

    public static byte[] createPayslipPdf(Employee emp, Payslip p) throws Exception {
        // PDF output stream setup
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        // Add company logo//E:/NEBULYTIX TECHNOLOGIES/files/nebTechLogo.jpg
        Image logo = Image.getInstance("C:\\path\\to\\NebulytixLogo.jpg");
        logo.scaleToFit(200f, 200f);
        logo.setAlignment(Element.ALIGN_RIGHT);
        document.add(logo);

        // Fonts
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        // Company header
        document.add(new Paragraph("NEBULYTIX TECHNOLOGIES PVT LTD", boldFont));
        document.add(new Paragraph("Payslip for the month: " + p.getPayslipMonth(), normalFont));
        document.add(new Paragraph("Dear " + emp.getFirstName() + " " + emp.getLastName() + " : " + emp.getCardNumber(), normalFont));
        document.add(new Paragraph("\n"));

     // ========================= Table 1 – Employee Info =========================
        PdfPTable table1 = new PdfPTable(2);
        table1.setWidthPercentage(100f);

        table1.addCell(createCellOuterColumnBorders("Location: " + p.getLocation(), normalFont, true, true, true, false));
        table1.addCell(createCellOuterColumnBorders("P.F.No: " + emp.getPfNumber(), normalFont, true, false, true, false));

        table1.addCell(createCellOuterColumnBorders("Bank A/C No: " + emp.getBankAccountNumber()
                + "   Bank: " + emp.getBankName(), normalFont, false, true, true, false));
        table1.addCell(createCellOuterColumnBorders("E.P.S No: " + emp.getEpsNumber(), normalFont, false, false, true, false));

        table1.addCell(createCellOuterColumnBorders("No. of days paid: " + emp.getDaysPresent(), normalFont, false, true, true, false));
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
        table3.addCell(createCellOuterColumnBorders("Balance: " + p.getBalance(), normalFont, false, false, true, false));

        table3.addCell(createCellOuterColumnBorders("", normalFont, false, true, true, false));
        table3.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));
        table3.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));
        table3.addCell(createCellOuterColumnBorders("Agg Deduction: " + p.getAggrgDeduction(), normalFont, false, false, true, false));

        table3.addCell(createCellOuterColumnBorders("", normalFont, false, true, true, false));
        table3.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));
        table3.addCell(createCellOuterColumnBorders("", normalFont, false, false, true, false));
        table3.addCell(createCellOuterColumnBorders("Income under Hd Salary: " + p.getIncHdSalary(), normalFont, false, false, true, false));

        table3.addCell(createCellOuterColumnBorders("", normalFont, true, true, true, true));
        table3.addCell(createCellOuterColumnBorders("", normalFont, true, false, true, true));
        table3.addCell(createCellOuterColumnBorders("", normalFont, true, false, true, true));
        table3.addCell(createCellOuterColumnBorders("Tax credit: " + p.getTaxCredit(), normalFont, true, false, true, true));

        document.add(table3);

        document.add(new Paragraph("\n"));

        // Footer note
        document.add(new Paragraph("This is computer generated. Does not require seal/signature.", normalFont));

        document.close();
        return baos.toByteArray();
    }

    // Helper function to create table cells with specific borders
    @SuppressWarnings("unused")
	private static PdfPCell createCellOuterColumnBorders(String text, Font font,
            boolean borderTop, boolean borderLeft, boolean borderRight, boolean borderBottom) {
        PdfPCell cell = new PdfPCell(new Paragraph(text, font));
        int border = 0;
        if (borderTop) border |= PdfPCell.TOP;
        if (borderLeft) border |= PdfPCell.LEFT;
        if (borderRight) border |= PdfPCell.RIGHT;
        if (borderBottom) border |= PdfPCell.BOTTOM;
        cell.setBorder(border);
        cell.setBorderWidth(1f);
        return cell;
    }
}
