package com.wtf.tool.util.excel.imp.factory;

import com.wtf.tool.util.excel.imp.param.WorkbookParameter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorkbookImportFactory implements WorkbookImportFactory {


    private Workbook workbook;

    @Override
    public Workbook createWorkbook(InputStream inputStream) throws IOException, InvalidFormatException {
        return WorkbookFactory.create(inputStream);
    }

    //获取Excel数据集合
    private Map<String, Map<Integer, List<Object>>> getExcelCell(WorkbookParameter parameter){
        String[] sheetNames = parameter.getSheetName();
        int[] rowIndex = parameter.getRowIndex();
        int[] colIndex = parameter.getColIndex();
        if (parameter.getFile() == null) {
            throw new IllegalArgumentException("The MultipartFile can not be empty");
        }
        Map<String, Map<Integer, List<Object>>> rowsMap = new HashMap<>();
        try {
            Workbook book = createWorkbook(parameter.getFile().getInputStream());
            for (int i = 0; i < sheetNames.length; i++) {
                //获取sheet
                Sheet sheet = book.getSheet(sheetNames[i]);
                Map<Integer, List<Object>> singleSheetData = this.getSingleSheetData(sheet, rowIndex[i], colIndex[i]);
                rowsMap.put(sheetNames[i], singleSheetData);
            }
        } catch (OfficeXmlFileException ofe) {
            ofe.printStackTrace();
        } catch (InvalidFormatException ife) {
            ife.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsMap;
    }

    public Map<Integer, List<Object>> getSingleSheetData(Sheet sheet, int rowIndex, int colIndex) {
        //存储行列表
        Map<Integer, List<Object>> cellsMap = new HashMap<>();
        for (int i = rowIndex; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            //获取每一行的单元格
            List<Object> cells = getCells(row, colIndex);
            cellsMap.put(i, cells);
        }
        return cellsMap;
    }

    // 存储每一行的单元格
    private List<Object> getCells(Row row, int colIndex) {
        List<Object> cells = new ArrayList<>();//每一列的单元格数据
        for (int j = colIndex; j < row.getLastCellNum(); j++) {
            if (row.getCell(j) != null){
                cells.add(this.getCellValue(row.getCell(j)));
            }
        }
        return cells;
    }

    public Object getCellValue(Cell cell) {
        Object value = null;
        int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_NUMERIC:
                value = cell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            default:
                break;
        }
        return value;
    }
}
