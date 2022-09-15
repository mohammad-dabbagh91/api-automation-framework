package helpers;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Parameters;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static helpers.OSNames.*;
import static helpers.ReadingConfigFileData.readConfig;

public class ReportingPreconditions {

    //@Parameters("OS")  // OS to be set and taken from the config.properties file
    @AfterSuite
    public void afterSuite() throws ParserConfigurationException, SAXException, IOException, InterruptedException {

        CustomKeywords customKeywords = new CustomKeywords();

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

        customKeywords.resettingAllSuitesNames("jsonServer"); // Put your suiteName right here
        customKeywords.resettingAllSuitesNames("travelInsurance");
    }
}
