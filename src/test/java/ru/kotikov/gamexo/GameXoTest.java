package ru.kotikov.gamexo;

import org.junit.Assert;
import org.junit.Test;

public class GameXoTest {

    // Проверка, что при ничье, не должно быть победителя
    @Test
    public void winnerSearchShouldBeFalseNoWinner() {
        String[][] playingField = new String[][]{
                {"|X|", "|O|", "|X|"},
                {"|O|", "|X|", "|O|"},
                {"|O|", "|X|", "|O|"}};

        Assert.assertFalse(GameXO.winnerSearch(playingField));
    }

    // Проверяем при наличии победителя
    @Test
    public void winnerSearchShouldBeTrueWithWinner() {
        String[][] playingField = new String[][]{
                {"|X|", "|O|", "|X|"},
                {"|O|", "|X|", "|O|"},
                {"|X|", "|X|", "|O|"}};

        Assert.assertTrue(GameXO.winnerSearch(playingField));
    }

    // Тест на обновление/очиску игрового поля
    @Test
    public void playingFieldShouldBeNew() {
        String[][] playingField = new String[][]{
                {"|X|", "|O|", "|X|"},
                {"|O|", "|X|", "|O|"},
                {"|X|", "|X|", "|O|"}};

        String[][] newPlayingField = new String[][]{
                {"|1|", "|2|", "|3|"},
                {"|4|", "|5|", "|6|"},
                {"|7|", "|8|", "|9|"}};

        GameXO.refreshPlayingField(playingField);

        Assert.assertArrayEquals(playingField, newPlayingField);
    }

    // проверка валидности введенных координат
    @Test
    public void coordinatesShouldBeValid() {
        Assert.assertArrayEquals(new int[]{0, 0}, GameXO.fieldCoordinates("1"));
        Assert.assertArrayEquals(new int[]{0, 1}, GameXO.fieldCoordinates("2"));
        Assert.assertArrayEquals(new int[]{0, 2}, GameXO.fieldCoordinates("3"));
        Assert.assertArrayEquals(new int[]{1, 0}, GameXO.fieldCoordinates("4"));
        Assert.assertArrayEquals(new int[]{1, 1}, GameXO.fieldCoordinates("5"));
        Assert.assertArrayEquals(new int[]{1, 2}, GameXO.fieldCoordinates("6"));
        Assert.assertArrayEquals(new int[]{2, 0}, GameXO.fieldCoordinates("7"));
        Assert.assertArrayEquals(new int[]{2, 1}, GameXO.fieldCoordinates("8"));
        Assert.assertArrayEquals(new int[]{2, 2}, GameXO.fieldCoordinates("9"));
    }

    // проверка свободно ли поле
    @Test
    public void fieldShouldBeFree() {
        Assert.assertTrue(GameXO.freeField(0, 0));
        Assert.assertTrue(GameXO.freeField(0, 1));
        Assert.assertTrue(GameXO.freeField(0, 2));
        Assert.assertTrue(GameXO.freeField(1, 0));
        Assert.assertTrue(GameXO.freeField(1, 1));
        Assert.assertTrue(GameXO.freeField(1, 2));
        Assert.assertTrue(GameXO.freeField(2, 0));
        Assert.assertTrue(GameXO.freeField(2, 1));
        Assert.assertTrue(GameXO.freeField(2, 2));
    }

    // проверка занято ли поле
    @Test
    public void fieldShouldBeOccupied(){
        for (int i = 0; i < GameXO.PLAYING_FIELD.length; i++) {
            for (int j = 0; j < GameXO.PLAYING_FIELD.length; j++) {
                GameXO.PLAYING_FIELD[i][j] = "|X|";
            }
        }
        Assert.assertFalse(GameXO.freeField(0, 0));
        Assert.assertFalse(GameXO.freeField(0, 1));
        Assert.assertFalse(GameXO.freeField(0, 2));
        Assert.assertFalse(GameXO.freeField(1, 0));
        Assert.assertFalse(GameXO.freeField(1, 1));
        Assert.assertFalse(GameXO.freeField(1, 2));
        Assert.assertFalse(GameXO.freeField(2, 0));
        Assert.assertFalse(GameXO.freeField(2, 1));
        Assert.assertFalse(GameXO.freeField(2, 2));
    }


}
