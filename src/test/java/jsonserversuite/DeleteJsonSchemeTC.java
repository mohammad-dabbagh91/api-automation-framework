package jsonserversuite;

import helpers.CustomKeywords;
import helpers.GlobalVariables;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;

import static helpers.ReadingConfigFileData.readConfig;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DeleteJsonSchemeTC {

    public void deleteJsonSchemeTC() throws IOException, InterruptedException {

        CustomKeywords customKeywords = new CustomKeywords();

        baseURI  = readConfig("baseUriJsonServer");

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        //.param("firstName", "Mohammad") // The params here does not work // Delete only takes Id to be sent with the path
                        .when()
                        .delete("/users/7")
                        .then()
                        .statusCode(200)
                        .log().all()  // This is to log all the response on the consol
                        .extract().response();

        GlobalVariables.CONSOL = response.jsonPath().prettify();

        // Verifications and extracting needed values go here

        Assert.assertEquals(response.getStatusCode(), 200, "header status code is not 200");
    }
}
