package com.crossover.techtrial.controller;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.model.Panel;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/**
 * PanelControllerTest class will test all APIs in PanelController.java.
 * @author Crossover
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PanelControllerTest {
  
  MockMvc mockMvc;
  
  @Mock
  private PanelController panelController;
  
  @Autowired
  private TestRestTemplate template;

  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(panelController).build();
  }

  @Test
  public void testPanelShouldBeRegistered() throws Exception {
    HttpEntity<Object> panel = getHttpEntity(
        "{\"serial\": \"232323\", \"longitude\": \"54.123232\"," 
            + " \"latitude\": \"54.123232\",\"brand\":\"tesla\" }");
    ResponseEntity<Panel> response = template.postForEntity(
        "/api/register", panel, Panel.class);
    Assert.assertEquals(202,response.getStatusCode());
  }

  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }

  @Test
  public void testSaveHourlyElectricity() throws Exception{
	  HttpEntity<Object> hourly = getHttpEntity(
			  "{\"panel\":{\"id\": \"2\",\"generatedElectricity\":120, \"serial\":"
			  + " \"232323\", \"longitude\": \"54.123232\","
			  + " \"latitude\": \"54.123232\",\"brand\":\"tesla\"},"
			  +"\"generatedElectricity\":120,"
		      + "\"readingAt\":\""+LocalDateTime.now()+"\"}");
		    ResponseEntity<HourlyElectricity> response = template.postForEntity(
		        "/api/panels/232323/hourly", hourly, HourlyElectricity.class);
		    Assert.assertEquals(200,response.getStatusCode());
  }

  @Test
  public void testErrorSaveHourlyElectricity() throws Exception{
	  HttpEntity<Object> hourly = getHttpEntity(
			  "{\"panel\":{\"id\": \"2\",\"generatedElectricity\":120, \"serial\":"
			  + " \"545454\", \"longitude\": \"54.123232\","
			  + " \"latitude\": \"54.123232\",\"brand\":\"tesla\"},"
			  +"\"generatedElectricity\":120,"
		      + "\"readingAt\":\""+LocalDateTime.now()+"\"}");
		    ResponseEntity<HourlyElectricity> response = template.postForEntity(
		        "/api/panels/545454/hourly", hourly, HourlyElectricity.class);
		    Assert.assertEquals(404,response.getStatusCode());
  }

  @Test
  public void testHourlyElectricity() throws Exception{
	  ResponseEntity<HourlyElectricity> response = template.
			  getForEntity("/api/panels/232323/hourly", null);
	  Assert.assertEquals(200,response.getStatusCode());
  }

  @Test
  public void testErrorHourlyElectricity() throws Exception{
	  ResponseEntity<HourlyElectricity> response = template.
			  getForEntity("/api/panels/545454/hourly", null);
	  Assert.assertEquals(404,response.getStatusCode());
  }


  @Test
  public void testAllDailyElectricityFromYesterday() throws Exception{
	  ResponseEntity<List<DailyElectricity>> response = template.
			  getForEntity("/api/panels/1234567890123456/daily", null);

	  Assert.assertEquals(200,response.getStatusCode());
  }

  @Test
  public void testAllDailyElectricityFromYesterdayValues() throws Exception{
	  ResponseEntity<String> response = template.
			  getForEntity("/api/panels/1234567890123456/daily", String.class);
	  String result = "[{\"date\":\"2018-01-31\",\"sum\":951,\"average\":158.5,\"min\":100,\"max\":310},{\"date\":\"2018-01-08\",\"sum\":840,\"average\":168.0,\"min\":100,\"max\":310}]";
	  Assert.assertEquals(result,response.getBody());
  }
}
