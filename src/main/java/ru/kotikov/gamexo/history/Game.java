package ru.kotikov.gamexo.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

// блок игра со списком ходов игры
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @JsonProperty("step")
    private ArrayList<Step> steps = new ArrayList<>();
}
