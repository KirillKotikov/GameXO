package ru.kotikov.gamexo.history.draw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.kotikov.gamexo.history.Game;
import ru.kotikov.gamexo.players.Player;

// структура журнала истории игры для записи и чтения при ничье
@Data
public class DrawGameplay {

    @JsonProperty("player")
    private Player[] players;
    @JsonProperty("game")
    private Game game = new Game();
    @JsonProperty("gameResult")
    private final String draw = "Draw!";
}

