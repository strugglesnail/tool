package com.wtf.tool.util.excel.export.param;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @auther: strugglesnail
 * @date: 2020/10/9 16:20
 * @desc:
 */
public class WorkbookParameter {

        private final Workbook workbook;

        private final Sheet sheet;

        private final CellStyle cellStyle;

        private final CreationHelper creationHelper;

        private final BeanParameter beanParameter;

        public WorkbookParameter(Workbook workbook, Sheet sheet, CellStyle cellStyle, CreationHelper creationHelper, BeanParameter beanParameter) {
            this.workbook = workbook;
            this.sheet = sheet;
            this.cellStyle = cellStyle;
            this.creationHelper = creationHelper;
            this.beanParameter = beanParameter;
        }

        public Workbook getWorkbook() {
            return workbook;
        }

        public Sheet getSheet() {
            return sheet;
        }

        public CellStyle getCellStyle() {
            return cellStyle;
        }

        public CreationHelper getCreationHelper() {
            return creationHelper;
        }

        public BeanParameter getBeanParameter() {
            return beanParameter;
        }
}
