package com.example.demo.web;

import com.example.demo.dto.CreatePlayerRequest;
import com.example.demo.dto.PlayerResponse;
import com.example.demo.entity.Player;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.service.PlayerService;
import com.example.demo.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.demo.web.PlayerTestData.ALL_PLAYER_COUNT;
import static com.example.demo.web.PlayerTestData.NOT_FOUND;
import static com.example.demo.web.PlayerTestData.PLAYER_1;
import static com.example.demo.web.PlayerTestData.PLAYER_2;
import static com.example.demo.web.PlayerTestData.PLAYER_3;
import static com.example.demo.web.PlayerTestData.PLAYER_MATCHER;
import static com.example.demo.web.PlayerTestData.RESPONSE_MATCHER;
import static com.example.demo.web.PlayerTestData.getNew;
import static com.example.demo.web.PlayerTestData.getUpdated;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlayerControllerTest extends AbstractControllerTest {
    private final String REST_URL = "/rest/players";
    private final String REST_URL_SLASH = REST_URL + "/";
    
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private PlayerRepository playerRepository;
    
    @Autowired
    private PlayerService playerService;
    
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
        CreatePlayerRequest request = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(request)))
                .andDo(print())
                .andExpect(status().isOk());
        
        PlayerResponse response = RESPONSE_MATCHER.readFromJson(action);
        long newId = response.getId();
        Player toCreate = mapper.map(request, Player.class);
        toCreate.setId(newId);
        playerService.setPlayerLevelAndUntilNextLevel(toCreate, toCreate.getExperience());
        Player created = mapper.map(response, Player.class);
        playerService.setPlayerLevelAndUntilNextLevel(created, created.getExperience());
        
        PLAYER_MATCHER.assertMatch(created, toCreate);
        PLAYER_MATCHER.assertMatch(playerRepository.findById(newId).get(), toCreate);
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
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + PLAYER_1.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESPONSE_MATCHER.contentJson(PLAYER_1));
    }
    
    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + NOT_FOUND))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void update() throws Exception {
        CreatePlayerRequest request = getUpdated();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_SLASH + PLAYER_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(request)))
                .andDo(print())
                .andExpect(status().isOk());
        
        PlayerResponse response = RESPONSE_MATCHER.readFromJson(action);
        Player toUpdate = mapper.map(request, Player.class);
        toUpdate.setId(PLAYER_1.getId());
        playerService.setPlayerLevelAndUntilNextLevel(toUpdate, toUpdate.getExperience());
        Player updated = mapper.map(response, Player.class);
        playerService.setPlayerLevelAndUntilNextLevel(updated, updated.getExperience());
        
        PLAYER_MATCHER.assertMatch(updated, toUpdate);
        PLAYER_MATCHER.assertMatch(playerRepository.findById(PLAYER_1.getId()).get(), toUpdate);
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
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + PLAYER_1.getId()))
                .andExpect(status().isOk());
        assertFalse(playerRepository.findById(PLAYER_1.getId()).isPresent());
    }
    
    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + NOT_FOUND))
                .andExpect(status().isNotFound());
    }
}
