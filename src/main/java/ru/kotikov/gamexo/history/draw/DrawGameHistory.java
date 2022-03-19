package ru.kotikov.gamexo.history.draw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.kotikov.gamexo.history.win.WinGameHistory;

// Класс журнал для записи и чтения истории игры в случае ничьи
@Data
public class DrawGameHistory {

    @JsonProperty("gameplay")
    private DrawGameplay drawGameplay = new DrawGameplay();

    // Переносит данные из объекта истории игры при победителе в объект истории игры в случае ничьи
    public static DrawGameHistory toDraw(WinGameHistory winGameHistory) {
        DrawGameHistory drawGameHistory = new DrawGameHistory();
        drawGameHistory.getDrawGameplay().setPlayers(winGameHistory.getWinGameplay().getPlayers());
        drawGameHistory.getDrawGameplay().setGame(winGameHistory.getWinGameplay().getGame());
        return drawGameHistory;
    }


}
