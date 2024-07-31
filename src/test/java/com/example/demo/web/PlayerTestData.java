package com.example.demo.web;

import com.example.demo.dto.CreatePlayerRequest;
import com.example.demo.dto.PlayerResponse;
import com.example.demo.dto.UpdatePlayerRequest;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;

import java.time.LocalDate;
import java.time.ZoneId;

public class PlayerTestData {
    public static final MatcherFactory.Matcher<PlayerResponse> RESPONSE_MATCHER =
            MatcherFactory.usingEqualsComparator(PlayerResponse.class);
    
    public static final long FIRST_PLAYER_ID = 1;
    public static final long SECOND_PLAYER_ID = 2;
    public static final long THIRD_PLAYER_ID = 3;
    public static final long NOT_FOUND_ID = 9999999;
    
    public static final int ALL_PLAYER_COUNT = 3;
    
    public static final PlayerResponse PLAYER_1 = new PlayerResponse(FIRST_PLAYER_ID, "Ниус",
            "Приходящий Без Шума", Race.HOBBIT, Profession.ROGUE, 1286823600000L, false,
            58347, 33, 1153);
    public static final PlayerResponse PLAYER_2 = new PlayerResponse(SECOND_PLAYER_ID, "Никрашш",
            "НайтВульф", Race.ORC, Profession.WARLOCK, 1266087600000L, false,
            174403, 58, 2597);
    public static final PlayerResponse PLAYER_3 = new PlayerResponse(THIRD_PLAYER_ID, "Эззэссэль",
            "шипящая", Race.DWARF, Profession.CLERIC, 1141066800000L, true,
            804, 3, 196);
    
    public static CreatePlayerRequest getRequestToCreate() {
        return new CreatePlayerRequest("TestName", "TestTitle", Race.ORC, Profession.CLERIC,
                LocalDate.now(), false, 1000);
    }
    
    public static PlayerResponse getResponseToCreate() {
        return new PlayerResponse(null, "TestName", "TestTitle", Race.ORC, Profession.CLERIC,
                LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                false, 1000, 4, 500);
    }
    
    public static UpdatePlayerRequest getRequestToUpdate() {
        return new UpdatePlayerRequest("UpdatedName", "UpdatedTitle", Race.TROLL, Profession.WARLOCK,
                LocalDate.now(), true, 3000);
    }
    
    public static PlayerResponse getResponseToUpdate() {
        return new PlayerResponse(null, "UpdatedName", "UpdatedTitle", Race.TROLL, Profession.WARLOCK,
                LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(), true,
                3000, 7, 600);
    }
}
