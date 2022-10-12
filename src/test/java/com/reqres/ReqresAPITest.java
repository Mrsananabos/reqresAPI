package com.reqres;


import com.reqres.pojo.UserDTO;
import com.reqres.pojo.domain.User;
import com.reqres.pojo.domain.UserMapper;
import com.reqres.pojo.remote.UserDataRemote;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;

public class ReqresAPITest extends TestBase {

    @Test
    @DisplayName("Проверка корректного создания пользователя")
    void createUserTest() {
//        JSONObject requestBody = new JSONObject()
//                .put("name", "Oleg")
//                .put("job", "developer");

        UserDTO user = new UserDTO();
        user.name = "Oleg";
        user.job = "developer";


        UserDTO response = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(user)
                .when()
                .post("/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(STATUS_CODE_201)
                .extract().as(UserDTO.class);

        Assertions.assertEquals(user.name, response.name);
    }

    @Test
    @DisplayName("Получение пользователя с id = 2")
    void getUserTest() {

        UserDataRemote response = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .when()
                .get("/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(STATUS_CODE_200)
                .extract().as(UserDataRemote.class);

        User user = UserMapper.map(response.data);

        Assertions.assertEquals(user.contact.email, response.data.email);
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
