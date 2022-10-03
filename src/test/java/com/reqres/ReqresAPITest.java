package com.reqres;


import io.restassured.RestAssured;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;

public class ReqresAPITest {

    @BeforeAll
    static void configureBeforeAll() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void createUserTest() {
        JSONObject requestBody = new JSONObject()
                .put("name", "Oleg")
                .put("job", "developer");

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody.toString())
                .when()
                .post("/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Oleg"))
                .body("job", is("developer"));
    }

    @Test
    void loginSuccessfulUserTest() {
        JSONObject requestBody = new JSONObject()
                .put("email", "eve.holt@reqres.in")
                .put("password", "cityslicka");

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody.toString())
                .when()
                .post("/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void loginUnsuccessfulUserTest() {
        JSONObject requestBody = new JSONObject()
                .put("email", "peter@klaven");

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody.toString())
                .when()
                .post("/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void registerSuccessfulUserTest() {
        JSONObject requestBody = new JSONObject()
                .put("email", "eve.holt@reqres.in")
                .put("password", "pistol");

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody.toString())
                .when()
                .post("/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void registerUnsuccessfulUserTest() {
        JSONObject requestBody = new JSONObject()
                .put("email", "sydney@fife");

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(requestBody.toString())
                .when()
                .post("/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

}
