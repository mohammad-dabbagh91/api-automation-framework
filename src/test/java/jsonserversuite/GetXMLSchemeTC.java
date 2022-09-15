package jsonserversuite;

import helpers.CustomKeywords;
import helpers.GlobalVariables;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static helpers.ReadingConfigFileData.readConfig;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class GetXMLSchemeTC {

    public void getXMLSchemeTC() throws IOException, InterruptedException {

        CustomKeywords customKeywords = new CustomKeywords();

        baseURI = readConfig("baseUriXml");

        Response response =
                given()
                        .param("page", 1)
                        .when()
                        .get("/api/Traveler")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .log().all()  // This is to log all the response on the consol
                        .contentType(ContentType.XML)
                        .extract().response();

        GlobalVariables.CONSOL = response.xmlPath().prettify();

        // Verifications and extracting needed values go here
       List<String> names = new ArrayList<>();
       names = response.xmlPath().getList("TravelerinformationResponse.travelers.Travelerinformation.name");

       names.forEach(name ->  System.out.println("name: "+name));


       // Validating a specific traveler name does exist
        names.forEach(
                name -> {
                    if(name.equals("Jonathan Ramos"))
                        System.out.println("it's Jonathan Ramos");
                    else
                        System.out.println(name);
                }

        );

        List<String> filteredNames =  names.stream()
                .filter(name -> name.equals("Jonathan Ramos"))
                .sorted()
                .collect(Collectors.toList());

        assertThat(filteredNames, hasItem("Jonathan Ramos"));

        System.out.println("//////////////"+response.xmlPath().getString("TravelerinformationResponse.travelers.Travelerinformation.name[0]"));


    }
}
