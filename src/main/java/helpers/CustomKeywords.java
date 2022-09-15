package helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static helpers.OSNames.LINUX;
import static helpers.OSNames.MACOS;

public class CustomKeywords {

    ////////////////////////////////////////////// Suites Names handling  //////////////////////////////////////////////
    ////////////////////////////////////// For Reporting and Sending Emails - excel sheets functions //////////////////////////////////////
    // 3 functions

    // Resetting All suites' values by getting them back to null
    public void resettingAllSuitesNames(String suiteName) throws IOException {

        boolean rowfound = false;

        String oldValue="";
        String  suiteValue="";

        String userDirectory = System.getProperty("user.dir");
        String filePath = userDirectory + "\\src\\main\\resources\\suiteNames.xlsx"; //For Windows
        if(GlobalVariables.OS_NAME.equals(MACOS) || GlobalVariables.OS_NAME.equals(LINUX)) {
            filePath = filePath.replace("\\", "//");
        }

        FileInputStream fileXlsx = new FileInputStream(filePath);

        XSSFWorkbook workbook = new XSSFWorkbook(fileXlsx);

        XSSFSheet sheet = workbook.getSheet("suites");

        for(int r = 0; r < sheet.getPhysicalNumberOfRows(); r++) {


            XSSFRow row = sheet.getRow(r);

            for(int c = 0; c < row.getPhysicalNumberOfCells(); c++) {

                XSSFCell cell = row.getCell(c);

                String  cellValue =null;

                cell.setCellType(CellType.STRING);

                cellValue = cell.getStringCellValue();

                if(cellValue.equalsIgnoreCase(suiteName)) {

                    rowfound = true;

                    row.getCell(1).setCellType(CellType.STRING);

                    oldValue = row.getCell(1).getStringCellValue();

                    System.out.println("oldValue value is:  "+oldValue);

                    // Here to reset/overwrite

                    row.getCell(1).setCellValue("null");

                    suiteValue = row.getCell(1).getStringCellValue();
                    System.out.println("new suite_value value is:  "+suiteValue);

                    FileOutputStream out = new FileOutputStream(filePath);

                    workbook.write(out);

                    out.close();

                }
            }

            if (rowfound == true) {
                break;
            }
        }
    }

    // Replacing null values by new suites paths
    public void replacingAllSuitesNamesValue(String suiteName, String suitePath) throws IOException {

        String oldValue="";
        String  suiteValue= "";

        boolean rowfound = false;

        String userDirectory = System.getProperty("user.dir");
        String filePath = userDirectory + "\\src\\main\\resources\\suiteNames.xlsx"; //For Windows
        if(GlobalVariables.OS_NAME.equals(MACOS) || GlobalVariables.OS_NAME.equals(LINUX)) {
            filePath = filePath.replace("\\", "//");
        }

        FileInputStream fileXlsx = new FileInputStream(filePath);

        XSSFWorkbook workbook = new XSSFWorkbook(fileXlsx);

        XSSFSheet sheet = workbook.getSheet("suites");

        for(int r = 0; r < sheet.getPhysicalNumberOfRows(); r++) {

            XSSFRow row = sheet.getRow(r);

            for(int c = 0; c < row.getPhysicalNumberOfCells(); c++) {

                XSSFCell cell = row.getCell(c);

                String  cellValue =null;

                cell.setCellType(CellType.STRING);

                cellValue = cell.getStringCellValue();

                if(cellValue.equalsIgnoreCase(suiteName)) {

                    rowfound = true;

                    row.getCell(1).setCellType(CellType.STRING);

                    oldValue = row.getCell(1).getStringCellValue();

                    System.out.println("oldValue value is:  "+oldValue);

                    // Here to reset/overwrite

                    row.getCell(1).setCellValue(suitePath);

                    suiteValue = row.getCell(1).getStringCellValue();
                    System.out.println("new suite_value value is:  "+suiteValue);

                    FileOutputStream out = new FileOutputStream(filePath);

                    workbook.write(out);

                    out.close();

                }

            }

            if (rowfound == true) {
                break;
            }
        }
    }



    // Taking the suite paths from the sheet and assign them to the related global variables
    public String settingGBsSuitesPaths(String suiteName)throws IOException{

        String value="";

        boolean rowfound = false;

        String userDirectory = System.getProperty("user.dir");
        String filePath = userDirectory + "\\src\\main\\resources\\suiteNames.xlsx"; //For Windows
        if(GlobalVariables.OS_NAME.equals(MACOS) || GlobalVariables.OS_NAME.equals(LINUX)) {
            filePath = filePath.replace("\\", "//");
        }

        FileInputStream fileXlsx = new FileInputStream(filePath);

        XSSFWorkbook workbook = new XSSFWorkbook(fileXlsx);

        XSSFSheet sheet = workbook.getSheet("suites");

        for(int r = 0; r < sheet.getPhysicalNumberOfRows(); r++) {

            XSSFRow row = sheet.getRow(r);

            for(int c = 0; c < row.getPhysicalNumberOfCells(); c++) {

                XSSFCell cell = row.getCell(c);

                String  cellValue =null;

                cell.setCellType(CellType.STRING);

                cellValue = cell.getStringCellValue();

                if(cellValue.equalsIgnoreCase(suiteName)) {

                    rowfound = true;

                    row.getCell(1).setCellType(CellType.STRING);

                    value = row.getCell(1).getStringCellValue();

                    System.out.println("oldValue value is:  "+value);

                    break;

                }

            }

        }


        return value;

    }
    ////////////////////////////////////////////// Suites Names handling ends here //////////////////////////////////////////////



    // Generating a random string consisted of chars only from A-Z whether upper or lower case
    public String getRandomString(int i) {

        // bind the length
        //bytearray = new byte[256];
        byte[] bytearray = new byte[256];
        String mystring;
        StringBuffer thebuffer;
        String theAlphaNumericS;

        new Random().nextBytes(bytearray);

        mystring = new String(bytearray, Charset.forName("UTF-8"));

        thebuffer = new StringBuffer();

        //remove all spacial char
        theAlphaNumericS = mystring.replaceAll("[^A-Za-z]", "");

        //random selection
        for (int m = 0; m < theAlphaNumericS.length(); m++) {

            if (Character.isLetter(theAlphaNumericS.charAt(m))
                    && (i > 0)
                    || Character.isDigit(theAlphaNumericS.charAt(m))
                    && (i > 0)) {

                thebuffer.append(theAlphaNumericS.charAt(m));
                i--;
            }
        }

        // the resulting string

        return thebuffer.toString();

    }
    //////////////////////////////////////////////// Generating random values functions end here


    //////////////////////////////////////////////// Date & Time functions ////////////////////////////////////////////////
    // Getting date with increased one month
    public String getting_date_inFuture_OneMonth() {
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);

        date = cal.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(date);
    }


    // Getting date with increased two month
    public String getting_date_inFuture_TwoMonths() {
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 2);

        date = cal.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.format(date);
    }


    //DDMMYYYYhhmmssms" For testing only
    public String customTimeStamp() {

        Date date= new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        SimpleDateFormat formatter = new SimpleDateFormat("DDMMYYYYhhmmssms");

        return formatter.format(ts);
    }



    // Geeting the current time and add an hour
    public String addhourTime() {

        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, 1);
        date = cal.getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        return formatter.format(date);
    }

    //////////////////////////////////////////////// Date & Time functions end here



}
