package ru.kotikov.gamexo;

import org.junit.*;
import ru.kotikov.gamexo.history.Step;
import ru.kotikov.gamexo.services.GameService;

import java.util.ArrayList;

import static ru.kotikov.gamexo.services.GameService.FIRST_PLAYER;
import static ru.kotikov.gamexo.services.GameService.SECOND_PLAYER;

public class GameServiceTest {

    @Before
    public void clear(){
        GameService.clearAll();
    }

    // при внесении имен, они не должны быть пустыми и должны совпадать с введенными
    @Test
    public void namesShouldNotBeEmpty() {
        GameService.setNames("Kirill", "Alena");
        Assert.assertNotNull(FIRST_PLAYER.getName());
        Assert.assertNotNull(SECOND_PLAYER.getName());
        Assert.assertEquals("Kirill", FIRST_PLAYER.getName());
        Assert.assertEquals("Alena", SECOND_PLAYER.getName());
    }

    // при нечетном счетчике должен ходить крестик, при нечетном нолик
    @Test
    public void moveNameShouldBeFirstPlayerName() {
        GameService.setNames("Kirill", "Alena");
        Assert.assertEquals(FIRST_PLAYER.getName(), GameService.getMovePlayer());
    }

    @Test
    public void moveCharShouldBeO() {
        GameService.setNames("Kirill", "Alena");
        GameService.movesCounter = 2;
        Assert.assertEquals(SECOND_PLAYER.getName(), GameService.getMovePlayer());
    }

    // должен возвращать игровое поле в одномерном массиве
    @Test
    public void playingFieldShouldBeConvert() {
        String[] expected = {"|1| |2| |3| ", "|4| |5| |6| ", "|7| |8| |9| "};
        Assert.assertArrayEquals(expected, GameService.showPlayingField(GameService.PLAYING_FIELD));
    }

    // координаты должны быть от 1 до 9
    @Test
    public void coordinatesShouldBeValid() {
        Assert.assertArrayEquals(new int[]{0, 0}, GameService.coordinateValid("1"));
        Assert.assertArrayEquals(new int[]{0, 1}, GameService.coordinateValid("2"));
        Assert.assertArrayEquals(new int[]{0, 2}, GameService.coordinateValid("3"));
        Assert.assertArrayEquals(new int[]{1, 0}, GameService.coordinateValid("4"));
        Assert.assertArrayEquals(new int[]{1, 1}, GameService.coordinateValid("5"));
        Assert.assertArrayEquals(new int[]{1, 2}, GameService.coordinateValid("6"));
        Assert.assertArrayEquals(new int[]{2, 0}, GameService.coordinateValid("7"));
        Assert.assertArrayEquals(new int[]{2, 1}, GameService.coordinateValid("8"));
        Assert.assertArrayEquals(new int[]{2, 2}, GameService.coordinateValid("9"));
        Assert.assertArrayEquals(new int[]{9, 9}, GameService.coordinateValid("0"));
    }

    // проверка свободно ли поле
    @Test
    public void fieldShouldBeFree() {
        Assert.assertTrue(GameService.freeField(0, 0));
        Assert.assertTrue(GameService.freeField(0, 1));
        Assert.assertTrue(GameService.freeField(0, 2));
        Assert.assertTrue(GameService.freeField(1, 0));
        Assert.assertTrue(GameService.freeField(1, 1));
        Assert.assertTrue(GameService.freeField(1, 2));
        Assert.assertTrue(GameService.freeField(2, 0));
        Assert.assertTrue(GameService.freeField(2, 1));
        Assert.assertTrue(GameService.freeField(2, 2));
    }

    // Проверка правильности внесения символа игрока
    @Test
    public void charShouldBeX() {
        GameService.drawMove(0, 0, "1");
        Assert.assertEquals(GameService.PLAYING_FIELD[0][0], "|X|");
    }

    // Проверка правильности внесения символа игрока
    @Test
    public void charShouldBeO() {
        GameService.movesCounter = 2;
        GameService.drawMove(0, 1, "2");
        Assert.assertEquals(GameService.PLAYING_FIELD[0][1], "|O|");
    }

    @Test
    public void firstPlayerShouldBeWinner() {
        GameService.setNames("Kirill", "Alena");
        for (int i = 0; i < GameService.PLAYING_FIELD.length; i++) {
            for (int j = 0; j < GameService.PLAYING_FIELD.length; j++) {
                if (GameService.movesCounter % 2 == 1) {
                    GameService.PLAYING_FIELD[i][j] = "|X|";
                } else GameService.PLAYING_FIELD[i][j] = "|O|";
                if (GameService.movesCounter == 7) break;
                GameService.movesCounter++;
            }
        }
        String expected = FIRST_PLAYER.getName() + " (крестик) победил(-а)! :)\n" + "Игра окончена! Общий счёт: \n" + "У игрока "
                + FIRST_PLAYER.getName() + " " + "1" + " побед, " + FIRST_PLAYER.getNumberOfLoses() + " поражений, "
                + FIRST_PLAYER.getNumberOfDraws() + " игр сыграно в ничью; \n" + "У игрока " + SECOND_PLAYER.getName() + " "
                + SECOND_PLAYER.getNumberOfWins() + " побед, " + "1" + " поражений, "
                + SECOND_PLAYER.getNumberOfDraws() + " игр сыграно в ничью.";
        Assert.assertEquals(expected, GameService.result());
    }

    @Test
    public void secondPlayerShouldBeWinner() {
        GameService.setNames("Kirill", "Alena");
        GameService.PLAYING_FIELD[2][1] = "|X|";
        GameService.PLAYING_FIELD[0][0] = "|O|";
        GameService.PLAYING_FIELD[0][1] = "|X|";
        GameService.PLAYING_FIELD[1][0] = "|O|";
        GameService.PLAYING_FIELD[0][2] = "|X|";
        GameService.PLAYING_FIELD[2][0] = "|O|";
        GameService.movesCounter = 6;

        String expected = SECOND_PLAYER.getName() + " (нолик) победил(-а)! :)\n" + "Игра окончена! Общий счёт: \n" + "У игрока "
                + FIRST_PLAYER.getName() + " " + FIRST_PLAYER.getNumberOfWins() + " побед, " + 1 + " поражений, "
                + FIRST_PLAYER.getNumberOfDraws() + " игр сыграно в ничью; \n" + "У игрока " + SECOND_PLAYER.getName() + " "
                + 1 + " побед, " + SECOND_PLAYER.getNumberOfLoses() + " поражений, "
                + SECOND_PLAYER.getNumberOfDraws() + " игр сыграно в ничью.";
        Assert.assertEquals(expected, GameService.result());
    }

    @Test
    public void shouldBeDraw() {
        GameService.setNames("Kirill", "Alena");
        GameService.PLAYING_FIELD[0][0] = "|X|";//1
        GameService.PLAYING_FIELD[0][1] = "|O|";//2
        GameService.PLAYING_FIELD[0][2] = "|O|";//3
        GameService.PLAYING_FIELD[1][0] = "|O|";//4
        GameService.PLAYING_FIELD[1][1] = "|X|";//5
        GameService.PLAYING_FIELD[1][2] = "|X|";//6
        GameService.PLAYING_FIELD[2][0] = "|X|";//7
        GameService.PLAYING_FIELD[2][1] = "|X|";//8
        GameService.PLAYING_FIELD[2][2] = "|O|";//9
        GameService.movesCounter = 9;
        String expected = "Ничья! Не удивительно 8)\n" + "Игра окончена! Общий счёт: \n" + "У игрока "
                + FIRST_PLAYER.getName() + " " + FIRST_PLAYER.getNumberOfWins() + " побед, " + FIRST_PLAYER.getNumberOfLoses() + " поражений, "
                + 1 + " игр сыграно в ничью; \n" + "У игрока " + SECOND_PLAYER.getName() + " "
                + SECOND_PLAYER.getNumberOfWins() + " побед, " + SECOND_PLAYER.getNumberOfLoses() + " поражений, "
                + 1 + " игр сыграно в ничью.";
        Assert.assertEquals(expected, GameService.result());
    }

    @Test
    public void allShouldBeBeginning() {
        for (int i = 0; i < GameService.PLAYING_FIELD.length; i++) {
            for (int j = 0; j < GameService.PLAYING_FIELD.length; j++) {
                GameService.PLAYING_FIELD[i][j] = "|X|";
            }
            GameService.steps.add(new Step("1", "X", "5"));
            GameService.movesCounter = 9;
            GameService.gameOver = true;
            GameService.draw = true;
            GameService.notValid = "Not empty";
        }
        GameService.clearAll();

        Assert.assertArrayEquals(GameXO.PLAYING_FIELD, GameService.PLAYING_FIELD);
        Assert.assertEquals(new ArrayList<Step>(), GameService.steps);
        Assert.assertEquals(1, GameService.movesCounter);
        Assert.assertFalse(GameService.gameOver);
        Assert.assertFalse(GameService.draw);
        Assert.assertEquals("", GameService.notValid);

    }
}
