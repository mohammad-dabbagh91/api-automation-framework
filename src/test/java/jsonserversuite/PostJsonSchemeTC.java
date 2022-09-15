package jsonserversuite;

import helpers.CustomKeywords;
import helpers.GlobalVariables;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static helpers.ReadingConfigFileData.readConfig;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PostJsonSchemeTC {

    public void postJsonSchemeTC(String firstNameTD, String lastNameTD, int subjectIdTD) throws IOException, InterruptedException {

        CustomKeywords customKeywords = new CustomKeywords();

        String body;

        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();

        String jsonString = new JSONObject()
                .put("firstName", firstNameTD)
                .put("lastName", lastNameTD)
                .put("subjectId", subjectIdTD)
                .toString();

        body = jsonString;

        GlobalVariables.REQUEST_BODY = body;

        baseURI  = readConfig("baseUriJsonServer");

        Response response =
                given()
                        .header("Content-Type", "application/json")
                        //.param("lastName", "Dabbagh")
                        .contentType(ContentType.JSON)
                        .accept(ContentType.JSON)
                        .body(body)
                        .when()
                        .post("/users")
                        .then()
                        .statusCode(201)
                        .log().all()  // This is to log all the response on the consol
                        //.body("jicoId", equalToPath("jicoId"))
                        //.body("jicoId", equalTo("42374"))
                        .extract().response();

        GlobalVariables.CONSOL = response.jsonPath().prettify();


        // Verifications and extracting needed values go here
        Assert.assertEquals(response.getStatusCode(), 201, "header status code is not 201");

        // Hamcrest assertions go here
        //assertThat(response.jsonPath().getString("$.firstName.*"), equalTo("1234"));


       String lastName = response.jsonPath().getString("lastName");
       assertThat(lastName ,equalTo(lastNameTD));





//        String pass = "Fail";
//
//        assertThat(pass, equalTo("Pass"));

    }
}
