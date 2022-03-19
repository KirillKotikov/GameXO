package ru.kotikov.gamexo.history.win;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kotikov.gamexo.history.Game;
import ru.kotikov.gamexo.players.Player;

// структура журнала истории игры для записи и чтения при наличии победителя
@Data
@NoArgsConstructor
public class WinGameplay {

    @JsonProperty("player")
    private Player[] players;
    @JsonProperty("game")
    private Game game = new Game();
    @JsonProperty("gameResult")
    private WinGameResult winGameResult;

    public WinGameplay(Player[] players) {
        this.players = players;
    }
}
