package com.example.demo.web;

import com.example.demo.dto.CreatePlayerRequest;
import com.example.demo.dto.PlayerResponse;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;

import java.time.LocalDate;

public class PlayerTestData {
    public static final MatcherFactory.Matcher<PlayerResponse> RESPONSE_MATCHER = MatcherFactory.usingEqualsComparator(PlayerResponse.class);
    public static final MatcherFactory.Matcher<Player> PLAYER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Player.class, "race.id", "profession.id");
    
    public static final long FIRST_PLAYER_ID = 1;
    public static final long SECOND_PLAYER_ID = 2;
    public static final long THIRD_PLAYER_ID = 3;
    public static final long NOT_FOUND = 100;
    
    public static final int ALL_PLAYER_COUNT = 40;
    
    public static final PlayerResponse PLAYER_1 = new PlayerResponse(FIRST_PLAYER_ID, "Ниус", "Приходящий Без Шума", Race.HOBBIT, Profession.ROGUE, 1286823600000L, false, 58347, 33, 1153);
    public static final PlayerResponse PLAYER_2 = new PlayerResponse(SECOND_PLAYER_ID, "Никрашш", "НайтВульф", Race.ORC, Profession.WARLOCK, 1266087600000L, false, 174403, 58, 2597);
    public static final PlayerResponse PLAYER_3 = new PlayerResponse(THIRD_PLAYER_ID, "Эззэссэль", "шипящая", Race.DWARF, Profession.CLERIC, 1141066800000L, true, 804, 3, 196);
    
    public static CreatePlayerRequest getNew() {
        return new CreatePlayerRequest("TestName", "TestTitle", Race.ORC, Profession.CLERIC, LocalDate.now(), false, 1000);
    }
    
    public static CreatePlayerRequest getUpdated() {
        return new CreatePlayerRequest("UpdatedName", "UpdatedTitle", Race.DWARF, Profession.WARLOCK, LocalDate.now(), true, 2000);
    }
}
