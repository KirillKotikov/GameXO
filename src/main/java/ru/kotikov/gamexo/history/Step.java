package ru.kotikov.gamexo.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// класс для описания хода по шаблону
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Step {
    // информация о заполненной ячейке игрового поля:
    // номер хода
    @JsonProperty("num")
    private String num;
    // id игрока, который сделал ход
    @JsonProperty("playerId")
    private String playerId;
    // ячейка в которую был занесён симфол игрока
    @JsonProperty("text")
    private String text;
}
