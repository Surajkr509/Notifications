
package com.example.jwtdemo.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.jwtdemo.repository.PlayersRepository;
import com.example.jwtdemo.repository.RoleRepository;

@Component
public class ReportGenerator {

	@Autowired
	PlayersRepository playersRepository;
	@Autowired
	RoleRepository roleRepository;

	public ByteArrayInputStream adminDashboardReports() throws IOException {

		String[] dashboardsColumns = { "Players Online", "Total Players", "Total Roles", "Total Earning" };
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet dashBoardSheet = workbook.createSheet("DashboardReport");
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.AQUA.getIndex());
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);
			Row headerRow = dashBoardSheet.createRow(0);
			for (int col = 0; col < dashboardsColumns.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(dashboardsColumns[col]);
				cell.setCellStyle(headerCellStyle);
			}
			Row row = dashBoardSheet.createRow(1);
			row.createCell(0).setCellValue(10368);
			row.createCell(1).setCellValue(playersRepository.count());
			row.createCell(2).setCellValue(roleRepository.count());
			row.createCell(3).setCellValue(106030);

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}

	}

}
