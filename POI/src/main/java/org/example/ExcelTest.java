package org.example;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;

public class ExcelTest {
    public static void main(String[] args) {
        // .xls 확장자 지원
        HSSFWorkbook wb = null;
        HSSFSheet sheet = null;
        Row row = null;
        Cell cell = null;

        // .xlsx. 확장자 지원
        XSSFWorkbook xssfwb = null;
        XSSFSheet xssfSheet = null;
        XSSFRow xssfRow = null;
        XSSFCell xssfCell = null;

        try {
            int rowNo = 0;    // 행 갯수

            /* 워크북 생성 */
            xssfwb = new XSSFWorkbook();
            xssfSheet = xssfwb.createSheet("엑셀 테스트");

            /* 헤더용 폰트 스타일 */
            XSSFFont font = xssfwb.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);  // 폰트스타일
            font.setFontHeightInPoints((short)14);  // 폰트크기
            font.setBold(true);

            /* 셀 병합 */
            xssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

            /* 타이틀 생성 */
            xssfRow = xssfSheet.createRow(rowNo++);
            xssfCell = xssfRow.createCell((short) 0);
            xssfCell.setCellValue("제목입니다.");

            /* 빈 행 추가 */
            xssfRow = xssfSheet.createRow(rowNo++);

            /* 헤더 생성 */
            xssfSheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo, 0, 1));
            xssfRow = xssfSheet.createRow(rowNo++);
            xssfCell = xssfRow.createCell((short) 0);
            xssfCell.setCellValue("헤더01 셀01");
            xssfCell = xssfRow.createCell((short) 0);
            xssfCell.setCellValue("헤더01 셀02");

            xssfRow = xssfSheet.createRow(rowNo++);
            xssfCell = xssfRow.createCell((short) 0);
            xssfCell.setCellValue("테이블 셀1");
            xssfCell = xssfRow.createCell((short) 1);
            xssfCell.setCellValue("테이블 셀2");
            xssfCell = xssfRow.createCell((short) 2);
            xssfCell.setCellValue("테이블 셀3");
            xssfCell = xssfRow.createCell((short) 3);
            xssfCell.setCellValue("테이블 셀4");
            xssfCell = xssfRow.createCell((short) 4);
            xssfCell.setCellValue("테이블 셀5");
            xssfCell = xssfRow.createCell((short) 5);
            xssfCell.setCellValue("테이블 셀6");
            xssfCell = xssfRow.createCell((short) 6);
            xssfCell.setCellValue("테이블 셀7");
            xssfCell = xssfRow.createCell((short) 7);
            xssfCell.setCellValue("테이블 셀8");
            xssfCell = xssfRow.createCell((short) 8);
            xssfCell.setCellValue("테이블 셀9");

            String localFile = "/Users/misonaru/Desktop" + "테스트_엑셀" + ".xlsx";

            File file = new File(localFile);
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            xssfwb.write(fos);

            if (xssfwb != null)
                    xssfwb.close();
            if (fos != null)
                    fos.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
