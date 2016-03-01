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
import com.example.repository.WidgetRepository;
import com.jayway.restassured.RestAssured;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LiquibaseSampleApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("test")
public class WidgetControllerTests {

    @Autowired
    WidgetRepository widgetRepository;

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() {
        widgetRepository.deleteAll();

        RestAssured.port = port;
    }

    @Test
    public void addWidgetTest() {
        // add new Widget
        String widgetName = "testName";
        String widgetType = "testType";
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
    }

    @Test
    public void updateWidgetTest() {
        // add new Widget
        String widgetName = "testName";
        String widgetType = "testType";
        String dataSource = "testdDataSource";
        String url = "/api/widget/add/" + widgetName + "/" + widgetType + "/" + dataSource;
        String response = RestAssured.get(url).asString();

        // update added Widget
        String newWidgetName = "NewTestName";
        String newWidgetType = "NewTestType";
        String newDataSource = "NewDataSource";
        url = "/api/widget/update/" + response + "/" + newWidgetName + "/" + newWidgetType + "/" + newDataSource;
        RestAssured
            .when()
            .get(url)
            .then()
            .statusCode(200)
            .body("name", Matchers.equalTo(newWidgetName))
            .body("widgetType", Matchers.equalTo(newWidgetType))
            .body("dataSource", Matchers.equalTo(newDataSource));
    }

    @Test
    public void deleteWorkspaceTest() {
        // add new Widget
        String widgetName = "testName";
        String widgetType = "testType";
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
            .body("widgetType", Matchers.equalTo(widgetType));

        // delete added Widget
        url = "/api/widget/delete/" + response;
        response = RestAssured.get(url).asString();
        assertFalse(response == "true");
    }
}
