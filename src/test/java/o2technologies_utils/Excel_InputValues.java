package o2technologies_utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Excel_InputValues {

    private Workbook workbook;
    private Sheet sheet;

    public Excel_InputValues(String excelFilePath, String sheetName) throws IOException {
        FileInputStream inputStream = new FileInputStream(excelFilePath);
        workbook = new XSSFWorkbook(inputStream);
        sheet = workbook.getSheet(sheetName);
    }

    public List<Map<String, String>> getExcelData() {
        List<Map<String, String>> dataList = new ArrayList<>();
        int rowCount = sheet.getPhysicalNumberOfRows();
        Row headerRow = sheet.getRow(0);

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            Map<String, String> dataMap = new HashMap<>();
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                Cell headerCell = headerRow.getCell(j);
                Cell dataCell = row.getCell(j);
                DataFormatter formatter = new DataFormatter();
                dataMap.put(formatter.formatCellValue(headerCell), formatter.formatCellValue(dataCell));
            }
            dataList.add(dataMap);
        }
        return dataList;
    }

    public void close() throws IOException {
        workbook.close();
    }
}
