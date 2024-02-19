
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;


import org.openqa.selenium.*;
        import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Rediffreader {
    static WebDriver driver = new ChromeDriver();

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        Rediffreader t = new Rediffreader();
        driver.get("https://money.rediff.com/losers/bse/daily/groupall");
        HashMap<String, String> companyData1 = new HashMap<String, String>();
        HashMap<String, String> companyData2 = new HashMap<String, String>();
        Map<String, Boolean> companyData_expected = new HashMap<String, Boolean>();
        Map<String, Boolean> companyData_actual = new HashMap<String, Boolean>();

        companyData1 = t.getTableDetails("Class", "dataTable");
//        t.writeExcelFile(companyData1, "output");
        companyData2 = t.readExcelFile("d:\\output.xlsx");

        companyData_expected=t.areEqualKeyValues(companyData2,companyData1);
        companyData_actual=t.areEqualKeyValues(companyData1,companyData2);
        System.out.println(companyData1.equals(companyData2));


        t.writeExcelFile(companyData_expected, "OutputFileExpected");
        t.writeExcelFile(companyData_actual, "OutputFileActual");
    }
/*areEqualKeyValues
compare two hashmaps key values ,returns hashmaps with key and boolean
required parameters two hashmaps
created by Heena
created date: 16/02/2024
   */
    private Map<String, Boolean> areEqualKeyValues(Map<String, String> first, Map<String, String> second) {
        return first.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(),
                        e -> e.getValue().equals(second.get(e.getKey()))));
    }



    /*readExcelFile
    To read data from excel and store into HashMap
    required parameters excel file
    created by Heena
    created date: 16/02/2024
       */
    public HashMap<String,String> readExcelFile(String xlFileName) throws IOException{
        FileInputStream wb = new FileInputStream(xlFileName);
        XSSFWorkbook workbook = new XSSFWorkbook(wb);
        XSSFSheet ws = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = ws.iterator();
        HashMap<String, String> hashMap1 = new HashMap<String, String>();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();
            hashMap1.put(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
        }
        wb.close();

        return hashMap1;
    }
    /*writeExcelFile
        To write data to excel and store into HashMap
        required parameters HashMap<String,String>
        created by Heena
        created date: 16/02/2024
           */
    public void writeExcelFile(HashMap<String, String> companyDetails, String outputFileName) throws IOException {
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet workSheet = workBook.createSheet();
        int r = 1;

        for (Map.Entry<String, String> data: companyDetails.entrySet()) {
            Row row = workSheet.createRow(r);
            Cell cell = row.createCell(0);
            cell.setCellValue(data.getKey());
            cell = row.createCell(1);
            cell.setCellValue(data.getValue());
            r++;

        }

        FileOutputStream saveFile = new FileOutputStream("D:\\"+ outputFileName+ ".xlsx");
        workBook.write(saveFile);
    }

    /*writeExcelFile
        To write data to excel and store into HashMap
        required parameters HashMap<String,Boolean>
        created by Heena
        created date: 16/02/2024
           */
    public void writeExcelFile(Map<String, Boolean> companyDetails, String outputFileName) throws IOException {
        XSSFWorkbook workBook = new XSSFWorkbook();
        XSSFSheet workSheet = workBook.createSheet();
        int r = 1;

        for (Map.Entry<String, Boolean> data: companyDetails.entrySet()) {
            Row row = workSheet.createRow(r);
            Cell cell = row.createCell(0);
            cell.setCellValue(data.getKey());
            cell = row.createCell(1);
            cell.setCellValue(data.getValue());
            r++;

        }

        FileOutputStream saveFile = new FileOutputStream("D:\\"+ outputFileName+ ".xlsx");
        workBook.write(saveFile);
    }
    /*getTableDetails
            To retrieve data from UI (Webelements) and store into HashMap
            required parameters findBy classname
            created by Heena
            created date: 16/02/2024
               */
    public HashMap<String, String> getTableDetails(String byType, String byValue) {
        WebElement dTable = null;
        if (byType == "Class") {
            dTable = driver.findElement(By.className(byValue));
        }
        List<WebElement> dRow = dTable.findElements(By.tagName("tr"));
        List<WebElement> dCol;
        HashMap<String, String> companyData = new HashMap<String, String>();
        for (int r = 1; r < dRow.size(); r++) {
            dCol = dRow.get(r).findElements(By.tagName("td"));
            companyData.put(dCol.get(0).getText(), dCol.get(3).getText());
        }
        return companyData;
    }

}
