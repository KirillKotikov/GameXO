package ru.kotikov.gamexo.players;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// класс игрока с дополнительными полями для ведения рейтинга
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlayerWithStat extends Player {
    private int numberOfWins = 0;
    private int numberOfLoses = 0;
    private int numberOfDraws = 0;

    public PlayerWithStat(String id, String symbol) {
        super(id, symbol);
    }

}
