package ru.kotikov.gamexo.history.win;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kotikov.gamexo.players.Player;

// структура результата игры при наличии победителя
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WinGameResult {
    @JsonProperty("player")
    private Player Player;
}
