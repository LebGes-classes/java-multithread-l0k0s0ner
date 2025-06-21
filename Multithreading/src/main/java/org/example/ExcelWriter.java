package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelWriter {
    private final Workbook workbook;
    private final String filename;
    private final Map<Integer, Integer> workerDayCounters = new HashMap<>();

    public ExcelWriter(String filename) {
        this.filename = filename;
        this.workbook = new XSSFWorkbook();
    }

    public synchronized void logHourByHour(int workerId, String workerName, String[] hourLog, int workedHours, int idleHours) {
        Sheet sheet = workbook.getSheet(workerName);
        if (sheet == null) {
            sheet = workbook.createSheet(workerName);
            workerDayCounters.put(workerId, 1);
        }

        int day = workerDayCounters.get(workerId);
        int startRow = sheet.getLastRowNum() + 2;

        Row dayLabelRow = sheet.createRow(startRow++);
        dayLabelRow.createCell(0).setCellValue("День " + day);

        for (String action : hourLog) {
            Row row = sheet.createRow(startRow++);
            row.createCell(0).setCellValue(action);
        }

        Row summaryRow = sheet.createRow(startRow++);
        summaryRow.createCell(0).setCellValue("Работал " + workedHours + " ч, отдыхал " + idleHours + " ч");

        workerDayCounters.put(workerId, day + 1);
    }

    public void save() {
        try (FileOutputStream out = new FileOutputStream(filename)) {
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
