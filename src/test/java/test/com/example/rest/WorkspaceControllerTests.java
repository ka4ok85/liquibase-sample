package test.com.example.rest;

import static org.junit.Assert.*;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.LiquibaseSampleApplication;
import com.example.repository.WorkspaceRepository;
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LiquibaseSampleApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("test")
public class WorkspaceControllerTests {

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() {
        workspaceRepository.deleteAll();

        RestAssured.port = port;
    }

    @Test
    public void addWorkspaceTest() {
        // add new Workspace
        String workspaceName = "testName";
        String url = "/api/workspace/add/" + workspaceName;
        String response = RestAssured.get(url).asString();

        // check added Workspace
        Long parsedWorkspaceId = Long.parseLong(response);
        url = "/api/workspace/" + parsedWorkspaceId;
        RestAssured
            .when()
            .get(url)
            .then()
            .statusCode(200)
            .body("name", Matchers.equalTo(workspaceName));
    }

    @Test
    public void updateWorkspaceTest() {
        // add new Workspace
        String workspaceName = "testName";
        String url = "/api/workspace/add/" + workspaceName;
        String response = RestAssured.get(url).asString();

        // update added Workspace
        String newWorkspaceName = "NewTestName";
        url = "/api/workspace/update/" + response + "/" + newWorkspaceName;
        RestAssured
            .when()
            .get(url)
            .then()
            .statusCode(200)
            .body("name", Matchers.equalTo(newWorkspaceName));
    }

    @Test
    public void deleteWorkspaceTest() {
        // add new Workspace
        String workspaceName = "testName";
        String url = "/api/workspace/add/" + workspaceName;
        String response = RestAssured.get(url).asString();

        // check added Workspace
        Long parsedWorkspaceId = Long.parseLong(response);
        url = "/api/workspace/" + parsedWorkspaceId;
        RestAssured
            .when()
            .get(url)
            .then()
            .statusCode(200)
            .body("name", Matchers.equalTo(workspaceName));

        // delete added Workspace
        url = "/api/workspace/delete/" + response;
        response = RestAssured.get(url).asString();
        assertFalse(response == "true");
    }
}
