package jsonserversuite;

import java.io.IOException;

import helpers.CustomKeywords;
import helpers.GlobalVariables;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ResourceCDN;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;

import static helpers.OSNames.*;
import static helpers.ReadingConfigFileData.readConfig;

public class JsonServerSuite extends TestingData{

    CustomKeywords customKeywords = new CustomKeywords();

    String ReportName_with_path;

    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;

    //helps to generate the logs in test report.
    ExtentTest test;

    // Report code //

    @BeforeTest
    public void startReport() throws IOException, InterruptedException {

        String osName = readConfig("os");

        switch (osName) {
            case"macOS":
                GlobalVariables.OS_NAME = MACOS;
                break;
            case"Windows":
                GlobalVariables.OS_NAME = WINDOWS;
                break;
            case"Linux":
                GlobalVariables.OS_NAME = LINUX;
                break;
        }

        System.out.println("GlobalVariables.OS_NAME is: "+GlobalVariables.OS_NAME);

        String reportRename = customKeywords.getRandomString(16);
        //ReportName_with_path = "/test-output/jsonServer"+reportRename+".html";
        ReportName_with_path = "/target/jsonServer"+reportRename+".html";


        // initialize the HtmlReporter

        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +ReportName_with_path);

        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);


        //configuration items to change the look and feel
        //add content, manage tests etc
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("REST Assured API Test");
        htmlReporter.config().setReportName("Json Server Suite");  // To be changed to the suite name
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        htmlReporter.config().getTestViewChartLocation();
        htmlReporter.config().setCSS("css-string");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setJS("js-string");
        htmlReporter.config().setProtocol(Protocol.HTTPS);

        htmlReporter.config().setCSS(".node.level-1  ul{ display:none;} .node.level-1.active ul{display:block;}  .card-panel.environment  th:first-child{ width:30%;}");
        htmlReporter.config().setJS("$(window).off(\"keydown\");");
        htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);

    }

    //////////////////////Creating objects for the test cases./////////////////////////
    GetJsonSchemeTC getJsonSchemeTC = new GetJsonSchemeTC();
    PostJsonSchemeTC postJsonSchemeTC = new PostJsonSchemeTC();
    DeleteJsonSchemeTC deleteJsonSchemeTC = new DeleteJsonSchemeTC();
    GetComplexJsonSchemeTC getComplexJsonSchemeTC = new GetComplexJsonSchemeTC();
    GetXMLSchemeTC getXMLSchemeTC = new GetXMLSchemeTC();

    /////////////////// Test cases list starts here///////////////////////////////////
    @Test(priority= 0)
    public void get_json_scheme_tc() throws InterruptedException, IOException {
        test = extent.createTest("Get Json Scheme");
        getJsonSchemeTC.getJsonSchemeTC();
    }

    @Test(priority= 1, dataProvider = "DataForPost")
    public void post_json_scheme_tc(String firstNameTD, String lastNameTD, int subjectIdTD) throws InterruptedException, IOException {
        test = extent.createTest("Post Json Scheme");
        postJsonSchemeTC.postJsonSchemeTC(firstNameTD, lastNameTD, subjectIdTD);
    }

    @Test(priority= 3)
    public void delete_json_scheme_tc() throws InterruptedException, IOException{
        test = extent.createTest("Delete Json Scheme");
        deleteJsonSchemeTC.deleteJsonSchemeTC();
    }

    // For the complex json scheme where the testers can practice Hamcrest assertions
    @Test(priority= 4)
    public void get_complex_json_scheme_tc() throws InterruptedException, IOException {
        test = extent.createTest("Get Complex Json Scheme");
        getComplexJsonSchemeTC.getComplexJsonSchemeTC();
    }

    // Xml format
    @Test(priority= 5)
    public void get_xml_scheme_tc() throws InterruptedException, IOException {
        test = extent.createTest("Get XML Scheme");
        getXMLSchemeTC.getXMLSchemeTC();
    }


    // After test cases running is done
    @AfterMethod
    public void getResult(ITestResult result) throws Throwable {

        String RequestBody  = "Request Body\n"+GlobalVariables.REQUEST_BODY;
        String responseLabel = "Response Body\n"+GlobalVariables.CONSOL;

        if(result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
            test.fail(result.getThrowable());
            test.fail(MarkupHelper.createCodeBlock(RequestBody));
            test.fail(MarkupHelper.createCodeBlock(responseLabel));
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" PASSED ", ExtentColor.GREEN));
            test.pass(MarkupHelper.createCodeBlock(RequestBody));
            test.pass(MarkupHelper.createCodeBlock(responseLabel));
        }
        else {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" SKIPPED ", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }

        // Resetting Request body and response after each test case
        GlobalVariables.REQUEST_BODY = null;
        GlobalVariables.CONSOL = null;

    }

    @AfterTest
    public void tearDown() {
        //to write or update test information to reporter
        extent.flush();
    }

    @AfterSuite
    public void aftersuite() throws IOException {

        GlobalVariables.JSONSERVER_SUITE_REPORT_PATH = ReportName_with_path;

        customKeywords.replacingAllSuitesNamesValue("jsonServer", ReportName_with_path);

    }


}
