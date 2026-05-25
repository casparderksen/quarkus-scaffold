package org.example.shared.contract.adapter.in.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@QuarkusTest
class ProblemContractTest {

    public static final String API_TEST_PROBLEMS = "api/_test/problems/";
    public static final String MEDIA_TYPE_APPLICATION_PROBLEM_JSON = "application/problem+json";

    @ParameterizedTest(name = "{0} -> {1}")
    @EnumSource(ProblemScenario.class)
    void shouldReturnExpectedProblem(ProblemScenario scenario) {
        given().when()
                .get(API_TEST_PROBLEMS + scenario.getPath())
                .then()
                .statusCode(scenario.getExpectedStatus())
                .contentType(MEDIA_TYPE_APPLICATION_PROBLEM_JSON)
                .body("$", hasKey("status"))
                .body("$", hasKey("title"))
                .body("$", hasKey("instance"))
                .body("status", equalTo(scenario.getExpectedStatus()));
    }
}
