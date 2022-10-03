package com.reqres;


import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;

public class ReqresAPITest extends TestBase {

    @Test
    @DisplayName("Проверка корректного создания пользователя")
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
                .statusCode(STATUS_CODE_201)
                .body("name", is("Oleg"))
                .body("job", is("developer"));
    }

    @Test
    @DisplayName("Проверка успешной авторизации пользователя")
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
                .statusCode(STATUS_CODE_200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Проверка неуспешной авторизации пользователя")
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
                .statusCode(STATUS_CODE_400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Проверка успешной регистрации пользователя")
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
                .statusCode(STATUS_CODE_200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Проверка неуспешной регистрации пользователя")
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
                .statusCode(STATUS_CODE_400)
                .body("error", is("Missing password"));
    }

}
