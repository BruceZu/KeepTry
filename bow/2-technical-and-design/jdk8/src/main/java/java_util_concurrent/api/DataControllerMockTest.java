package java_util_concurrent.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DataController.class)
public class DataControllerMockTest {

  @Autowired private MockMvc mockMvc;

  @Test
  public void createAndGetObjects() throws Exception {
    MvcResult testResult =
        this.mockMvc
            .perform(
                put("/data/myRepo")
                    .content(new String("testContent".getBytes(), StandardCharsets.UTF_8)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andReturn();
    assertEquals(MediaType.APPLICATION_JSON.toString(), testResult.getResponse().getContentType());
    MvcResult helloResult =
        this.mockMvc
            .perform(
                put("/data/myRepo")
                    .content(new String("helloWorld".getBytes(), StandardCharsets.UTF_8)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andReturn();
    assertEquals(MediaType.APPLICATION_JSON.toString(), helloResult.getResponse().getContentType());
    JsonNode testResponse = getResponseJson(testResult);
    JsonNode helloResponse = getResponseJson(helloResult);

    assertNotEquals(helloResponse.get("oid").asText(), testResponse.get("oid").asText());
    assertEquals("testContent".length(), testResponse.get("size").asLong());
    assertEquals("helloWorld".length(), helloResponse.get("size").asLong());

    MvcResult testGetResult =
        this.mockMvc
            .perform(get("/data/myRepo/" + testResponse.get("oid").asText()))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    assertEquals("testContent", getResponseText(testGetResult));

    MvcResult helloGetResult =
        this.mockMvc
            .perform(get("/data/myRepo/" + helloResponse.get("oid").asText()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
            .andReturn();
    assertEquals("helloWorld", getResponseText(helloGetResult));
  }

  @Test
  public void deleteObjects() throws Exception {
    MvcResult myResult =
        this.mockMvc
            .perform(
                put("/data/myRepo")
                    .content(new String("helloWorld".getBytes(), StandardCharsets.UTF_8)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andReturn();
    MvcResult yourResult =
        this.mockMvc
            .perform(
                put("/data/yourRepo")
                    .content(new String("helloWorld".getBytes(), StandardCharsets.UTF_8)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andReturn();
    JsonNode myResponse = getResponseJson(myResult);
    JsonNode yourResponse = getResponseJson(yourResult);

    MvcResult myDeleteResult =
        this.mockMvc
            .perform(delete("/data/myRepo/" + myResponse.get("oid").asText()))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    assertTrue(getResponseText(myDeleteResult).isEmpty());

    this.mockMvc
        .perform(delete("/data/myRepo/" + myResponse.get("oid").asText()))
        .andDo(print())
        .andExpect(status().isNotFound());

    MvcResult yourDeleteResult =
        this.mockMvc
            .perform(delete("/data/yourRepo/" + yourResponse.get("oid").asText()))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    assertTrue(getResponseText(yourDeleteResult).isEmpty());
  }

  private String getResponseText(MvcResult result) throws Exception {
    return result.getResponse().getContentAsString(StandardCharsets.UTF_8);
  }

  private JsonNode getResponseJson(MvcResult result) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
  }
}
