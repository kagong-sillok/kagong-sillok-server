package org.prography.kagongsillok.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.prography.kagongsillok.image.ui.dto.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@Import(DatabaseCleanerConfig.class)
public abstract class AcceptanceTest {

    @Autowired
    protected DatabaseCleanup databaseCleanup;
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        databaseCleanup.execute();
    }

    protected <T> T 응답_바디_추출(final ExtractableResponse<Response> response, final Class<T> clazz) {
        return response.body()
                .jsonPath()
                .getObject("data", clazz);
    }

    protected ExtractableResponse<Response> post(final String uri, final Object body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .when().post(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> get(final String uri) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> getPage(final String uri, final int page, final int size) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("page", page)
                .param("size", size)
                .when().get(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> put(final String uri, final Object body) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(body)
                .put(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> putWithoutBody(final String uri) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .put(uri)
                .then().log().all()
                .extract();
    }

    protected ExtractableResponse<Response> delete(final String uri) {
        return RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(uri)
                .then().log().all()
                .extract();
    }
}
