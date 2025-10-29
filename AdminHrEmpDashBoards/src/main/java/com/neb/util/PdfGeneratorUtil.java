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

        // Add company logo
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
        document.add(new Paragraph("Dear " + emp.getFirstName() + " " + emp.getLastName() + " : " + emp.getId(), normalFont));
        document.add(new Paragraph("\n"));

        // Employee details table, salary table, and tax details table
        // (each section formatted using PdfPTable and helper method)
        // ...

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
