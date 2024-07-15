package com.example.demo.web;

import com.example.demo.dto.CreatePlayerRequest;
import com.example.demo.dto.PlayerResponse;
import com.example.demo.dto.UpdatePlayerRequest;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.demo.web.PlayerTestData.ALL_PLAYER_COUNT;
import static com.example.demo.web.PlayerTestData.NOT_FOUND_ID;
import static com.example.demo.web.PlayerTestData.PLAYER_1;
import static com.example.demo.web.PlayerTestData.PLAYER_2;
import static com.example.demo.web.PlayerTestData.PLAYER_3;
import static com.example.demo.web.PlayerTestData.RESPONSE_MATCHER;
import static com.example.demo.web.PlayerTestData.getRequestToCreate;
import static com.example.demo.web.PlayerTestData.getRequestToUpdate;
import static com.example.demo.web.PlayerTestData.getResponseToCreate;
import static com.example.demo.web.PlayerTestData.getResponseToUpdate;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlayerControllerImplTest extends AbstractControllerTest {
    private final String REST_URL = "/rest/players";
    private final String REST_URL_SLASH = REST_URL + "/";
    
    @Autowired
    private PlayerControllerImpl playerController;
    
    @Autowired
    private PlayerRepository playerRepository;
    
    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESPONSE_MATCHER.contentJson(PLAYER_1, PLAYER_2, PLAYER_3));
    }
    
    @Test
    void getAllWithFilter() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .param("minLevel", String.valueOf(PLAYER_1.getLevel()))
                .param("maxLevel", String.valueOf(PLAYER_2.getLevel())))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESPONSE_MATCHER.contentJson(PLAYER_1, PLAYER_2));
    }
    
    @Test
    void getCount() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "count"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(equalTo(ALL_PLAYER_COUNT)));
    }
    
    @Test
    void getCountWithFilter() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "count")
                .param("name", PLAYER_1.getName()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(equalTo(1)));
    }
    
    @Test
    void create() throws Exception {
        CreatePlayerRequest request = getRequestToCreate();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(request)))
                .andDo(print())
                .andExpect(status().isOk());
        
        PlayerResponse actual = RESPONSE_MATCHER.readFromJson(action);
        long newId = actual.getId();
        PlayerResponse expected = getResponseToCreate();
        expected.setId(newId);
        RESPONSE_MATCHER.assertMatch(actual, expected);
        
        playerController.delete(newId);
    }
    
    @Test
    void createInvalid() throws Exception {
        CreatePlayerRequest invalid = new CreatePlayerRequest(null, null, null, null, null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void get() throws Exception {
        PlayerResponse created = (PlayerResponse) playerController.create(getRequestToCreate()).getBody();
        long id = created.getId();
        
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + id))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESPONSE_MATCHER.contentJson(created));
        
        playerController.delete(id);
    }
    
    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + NOT_FOUND_ID))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void update() throws Exception {
        PlayerResponse created = (PlayerResponse) playerController.create(getRequestToCreate()).getBody();
        long id = created.getId();
        
        UpdatePlayerRequest request = getRequestToUpdate();
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(request)))
                .andDo(print())
                .andExpect(status().isOk());
        
        PlayerResponse expected = getResponseToUpdate();
        PlayerResponse actual = (PlayerResponse) playerController.get(id).getBody();
        expected.setId(id);
        RESPONSE_MATCHER.assertMatch(actual, expected);
        
        playerController.delete(id);
    }
    
    @Test
    void updateInvalid() throws Exception {
        CreatePlayerRequest invalid = new CreatePlayerRequest("TooLongName11111111111", null, null, null, null, null, null);
        perform(MockMvcRequestBuilders.post(REST_URL_SLASH + PLAYER_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void delete() throws Exception {
        PlayerResponse created = (PlayerResponse) playerController.create(getRequestToCreate()).getBody();
        long id = created.getId();
        
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + id))
                .andExpect(status().isOk());
        assertFalse(playerRepository.findById(id).isPresent());
    }
    
    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + NOT_FOUND_ID))
                .andExpect(status().isNotFound());
    }
}
