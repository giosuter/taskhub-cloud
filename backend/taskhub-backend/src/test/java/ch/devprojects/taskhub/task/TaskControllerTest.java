package ch.devprojects.taskhub.task;

import ch.devprojects.taskhub.task.api.TaskRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class TaskControllerTest {

  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper om;

  @Test
  void create_list_get_update_delete_roundtrip() throws Exception {
    // create
    var req = new TaskRequest("First Task", "demo", "TODO", null);
    var createRes = mockMvc.perform(post("/api/tasks")
        .contentType("application/json")
        .content(om.writeValueAsString(req)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.title").value("First Task"))
      .andReturn();

    String id = om.readTree(createRes.getResponse().getContentAsString()).get("id").asText();

    // list
    mockMvc.perform(get("/api/tasks"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$[0].id").value(id));

    // get
    mockMvc.perform(get("/api/tasks/{id}", id))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id").value(id));

    // update
    var updateReq = new TaskRequest("First Task (edited)", null, "IN_PROGRESS", null);
    mockMvc.perform(put("/api/tasks/{id}", id)
        .contentType("application/json")
        .content(om.writeValueAsString(updateReq)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.status").value("IN_PROGRESS"))
      .andExpect(jsonPath("$.title").value("First Task (edited)"));

    // delete
    mockMvc.perform(delete("/api/tasks/{id}", id))
      .andExpect(status().isNoContent());
  }
}