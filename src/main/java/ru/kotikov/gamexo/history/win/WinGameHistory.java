package ru.kotikov.gamexo.history.win;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// Класс журнал для записи и чтения истории игры в случае победы одного из игроков
@Data
public class WinGameHistory {
    @JsonProperty("gameplay")
    private WinGameplay winGameplay;
}
