package ru.kotikov.gamexo.players;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Класс игрок в общем формате
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    // символ игрока - крестик или нолик
    @JsonProperty("symbol")
    private String symbol;

    public Player(String id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

}
