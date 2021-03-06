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
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.LiquibaseSampleApplication;
import com.example.repository.LocationRepository;
import com.example.repository.WidgetRepository;
import com.example.repository.WorkspaceRepository;
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LiquibaseSampleApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("test")
public class LocationControllerTests {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    WidgetRepository widgetRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() {
        widgetRepository.deleteAll();
        workspaceRepository.deleteAll();
        locationRepository.deleteAll();

        RestAssured.port = port;
    }

    @Test
    public void addWidgetToWorkspaceTest() {
        // add new Widget
        String widgetName = "testWidgetName";
        String widgetType = "testWidgetType";
        String dataSource = "testdDataSource";
        String url = "/api/widget/add/" + widgetName + "/" + widgetType + "/" + dataSource;
        String response = RestAssured.get(url).asString();

        // check added Widget
        Long parsedWidgetId = Long.parseLong(response);
        url = "/api/widget/" + parsedWidgetId;
        RestAssured
            .when()
            .get(url)
            .then()
            .statusCode(200)
            .body("name", Matchers.equalTo(widgetName))
            .body("widgetType", Matchers.equalTo(widgetType))
            .body("dataSource", Matchers.equalTo(dataSource));

        // add new Workspace
        String workspaceName = "testWorkspaceName";
        url = "/api/workspace/add/" + workspaceName;
        response = RestAssured.get(url).asString();

        // check added Workspace
        Long parsedWorkspaceId = Long.parseLong(response);
        url = "/api/workspace/" + parsedWorkspaceId;
        RestAssured
            .when()
            .get(url)
            .then()
            .statusCode(200)
            .body("name", Matchers.equalTo(workspaceName));

        int rowNumber = 9;
        int columnNumber = 8;
        url = "/api/placeWidget/" + parsedWorkspaceId + "/" + parsedWidgetId + "/" + rowNumber + "/" + columnNumber;
        RestAssured
            .when()
            .get(url)
            .then()
            .statusCode(200)
            .body("rowNumber", Matchers.equalTo(rowNumber))
            .body("columnNumber", Matchers.equalTo(columnNumber));
    }

}
