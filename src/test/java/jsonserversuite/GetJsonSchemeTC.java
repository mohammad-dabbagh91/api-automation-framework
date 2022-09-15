package jsonserversuite;

import helpers.CustomKeywords;
import helpers.GlobalVariables;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static helpers.ReadingConfigFileData.readConfig;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetJsonSchemeTC {

    public void getJsonSchemeTC() throws IOException, InterruptedException {

        CustomKeywords customKeywords = new CustomKeywords();

        baseURI  = readConfig("baseUriJsonServer");

        Response response =
                given()
                        //.header("id", 2)
                        .param("lastName", "Hanks")
                        .when()
                        .get("/users")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .log().all()  // This is to log all the response on the consol
                        //.body("jicoId", equalToPath("jicoId"))
                        //.body("lastName[1:]", equalTo("Dabbagh"))
                        .contentType(ContentType.JSON)
                        .extract().response();

        GlobalVariables.CONSOL = response.jsonPath().prettify();

        // Verifications and extracting needed values go here

        // Assertion using TestNG assertion library
        Assert.assertEquals(response.getStatusCode(), 200, "header status code is not 200");

        // Assertion using Hamcrest assertion library
        assertThat(response.jsonPath().getString("firstName[0]"), equalTo("Tom"));


        // Assertion using TestNG assertion library
        List<String> firstName = new ArrayList<>();
        firstName=   response.jsonPath().get("firstName");

        Assert.assertTrue(firstName.contains("Tom"), "It does not contains Tom");



        // Assertion using Hamcrest assertion library to assert existing a specific value within the array [First Solution]
        List<String> filteredFirstNames = firstName.stream()
                .filter(name -> name.equals("Tom"))
                .sorted()
                .collect(Collectors.toList());

        System.out.println(filteredFirstNames);

        assertThat(filteredFirstNames, contains("Tom"));

        // Assertion using Hamcrest assertion library to assert existing a specific value within the array [Second Solution]
        firstName=   response.jsonPath().get("firstName");
        assertThat(firstName, hasItem("Tom"));





        // Assertion using AssertJ assertion library



        // Showing specific fields' values

        JsonPath jsonPathEvaluator = response.jsonPath();

        System.out.println("jsonPathEvaluator is:    "+jsonPathEvaluator.get("lastName[3]"));

        System.out.println("jsonPathEvaluator.getList(lastName)"+ jsonPathEvaluator.getList("lastName"));
    }

}
