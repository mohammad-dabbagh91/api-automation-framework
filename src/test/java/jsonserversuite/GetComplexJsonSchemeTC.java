package jsonserversuite;

import helpers.CustomKeywords;
import helpers.GlobalVariables;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatcher;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static helpers.ReadingConfigFileData.readConfig;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetComplexJsonSchemeTC {

    public void getComplexJsonSchemeTC() throws IOException, InterruptedException {

        CustomKeywords customKeywords = new CustomKeywords();

        baseURI  = readConfig("baseUriJsonServer");

        // The recommended way of sending the request
        Response response =
                given()
                        //.header("id", 2)
                        //.param("lastName", "Hanks")
                        .when()
                        .get("/persons")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .log().all()  // This is to log all the response on the consol
                        .contentType(ContentType.JSON)
                        .extract().response();

        GlobalVariables.CONSOL = response.jsonPath().prettify();

//        // Another way of sending the request (Not recommended way)
//        // Request Object
//        RequestSpecification httpRequest = RestAssured.given();
//
//        // Response Object
//        Response response = httpRequest.request(Method.GET, "/persons");
//
//        // Print response in console window
//        String responseBody = response.getBody().asString();
//        System.out.println("Response Body is: "+responseBody);
//        GlobalVariables.CONSOL = responseBody;
//
//        // Validating Headers
//        String contentType = response.header("Content-Type"); // Capture details of Content-Type header
//        System.out.println("contentType is: "+contentType);  // It will retrieve - application/json; charset=utf-8
//
//        String contentEncodingReponse = response.header("Content-Encoding");
//        System.out.println("contentEncodingReponse is: "+contentEncodingReponse);



        // Verifications and extracting needed values go here

        // Assertion using TestNG assertion library
        Assert.assertEquals(response.getStatusCode(), 200, "header status code is not 200");

        // Assertion using Hamcrest assertion library



        // Showing specific fields' values
        JsonPath jsonPathEvaluator = response.jsonPath();

        System.out.println("List of all cities is:    "+jsonPathEvaluator.get("address.city"));
        System.out.println("List of all cities is:    "+jsonPathEvaluator.getList("address[0].city"));

        System.out.println("First city is:    "+jsonPathEvaluator.get("address[0].city[0]"));
        System.out.println("Second city is:    "+jsonPathEvaluator.get("address[0].city[1]"));

        // Another way of getting a specific field key's value from Json
        System.out.println("cities : "+ JsonPath.from(response.getBody().asString()).get("address[0].city[1]"));





        List<String> cities = new ArrayList<>();
        cities=   jsonPathEvaluator.getList("address[0].city");

        List<String> filteredCities = cities.stream()
                .filter(name -> name.equals("New York"))
                .sorted()
                .collect(Collectors.toList());

        System.out.println("filteredCities are:  "+filteredCities);

        // Assert that the List has Item
        assertThat(filteredCities, hasItem("New York"));

        // assert that the List has Items
        assertThat(cities, hasItems("New York", "Chicago"));


        cities.stream()
                .forEach(city ->  System.out.println("city: "+city));


        List<String> citis = new ArrayList<String>();
        citis = JsonPath.from(response.getBody().asString()).get("address[0].city");
        //assertThat((new Object[]{citis.get(0), citis.get(1)}), is(new Object[]{"Chicago", " New York"}));




//        Map<String, String> map = response.jsonPath().getMap("Map");
//        System.out.println(map.get("Key1"));
//        assertThat(map, hasKey("Key2"));

        List<String> map = response.jsonPath().get("Map");
        System.out.println("map"+map);

        // This one works
        List<Map<String, String>> types = response.jsonPath().getList("Map");
        System.out.println(types.get(0).get("Key1"));

        assertThat(types.get(0), hasKey("Key1"));
        assertThat(types.get(0).get("Key1"), equalTo("Value1"));

        // This will pass
        assertThat(JsonPath.from(response.getBody().asString()).get("address[0].city[2]"), nullValue());

        // This will fail
        //assertThat(JsonPath.from(response.getBody().asString()).get("address[0].city[2]"), notNullValue());


    }


}
