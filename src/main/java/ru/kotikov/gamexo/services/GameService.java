package ru.kotikov.gamexo.services;

import org.springframework.stereotype.Service;
import ru.kotikov.gamexo.GameXO;
import ru.kotikov.gamexo.RatingWriter;
import ru.kotikov.gamexo.history.Step;
import ru.kotikov.gamexo.history.draw.DrawGameHistory;
import ru.kotikov.gamexo.history.win.WinGameHistory;
import ru.kotikov.gamexo.history.win.WinGameResult;
import ru.kotikov.gamexo.history.win.WinGameplay;
import ru.kotikov.gamexo.parsers.JsonParser;
import ru.kotikov.gamexo.parsers.XmlParser;
import ru.kotikov.gamexo.players.Player;
import ru.kotikov.gamexo.players.PlayerWithStat;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class GameService {
    // Массив игрового поля
    public static final String[][] PLAYING_FIELD = new String[][]{{"|1|", "|2|", "|3|"}, {"|4|", "|5|", "|6|"}, {"|7|", "|8|", "|9|"}};
    // Счетчик ходов
    public static byte movesCounter = 1;
    // Определяет игра продолжается или окончена (есть победитель или ничья)
    public static boolean gameOver = false;
    //Первый игрок - х
    public static final PlayerWithStat FIRST_PLAYER = new PlayerWithStat("1", "X");
    //Второй игрок - о
    public static final PlayerWithStat SECOND_PLAYER = new PlayerWithStat("2", "O");
    // запись истории игры
    public static WinGameHistory winGameHistory = new WinGameHistory();
    // Список в который будут заисываться ходы игроков
    public static ArrayList<Step> steps = new ArrayList<>();
    // Сообщение при невалидности ответа
    public static String notValid;
    // флаг игры в ничью
    public static boolean draw = false;

    // Запись имен игроков
    public static void setNames(String nameX, String nameO) {
        FIRST_PLAYER.setName(nameX);
        SECOND_PLAYER.setName(nameO);
        Player[] players = {new Player("1", FIRST_PLAYER.getName(), "X"),
                new Player("2", SECOND_PLAYER.getName(), "O")};
        winGameHistory.setWinGameplay(new WinGameplay(players));
    }

    // получение имени игрока, который сейчас ходит
    public static String getMoveName() {
        // Оповещение об очередности хода
        if (movesCounter % 2 == 1) {
            return FIRST_PLAYER.getName();
        } else {
            return SECOND_PLAYER.getName();
        }
    }

    // Отображение игрового поля в текущем состоянии
    public static String[] showPlayingField(String[][] playingField) {
        StringBuilder temp = new StringBuilder();
        for (String[] charsX : playingField) {
            for (String charsY : charsX) {
                temp.append(charsY).append(" ");
            }
            temp.append("\n");
        }
        return temp.toString().split("\n");
    }

    // проверка введенной координаты на валидность
    public static int[] coordinateValid(String coordinate) {
        // Проверка координат на длину
        if (coordinate.length() != 1) {
            notValid = "ай-яй " + getMoveName() + ":) ввел какую-то ерунду! Введи нормальные свободные координаты (число от 1 до 9 включительно)";
        }
        // Переменные для координат хода на поле
        int x = 9, y = 9;
        // Проверка введенных координат на соответствие возможным
        switch (coordinate) {
            case "1" -> {
                x = 0;
                y = 0;
            }
            case "2" -> {
                x = 0;
                y = 1;
            }
            case "3" -> {
                x = 0;
                y = 2;
            }
            case "4" -> {
                x = 1;
                y = 0;
            }
            case "5" -> {
                x = 1;
                y = 1;
            }
            case "6" -> {
                x = 1;
                y = 2;
            }
            case "7" -> {
                x = 2;
                y = 0;
            }
            case "8" -> {
                x = 2;
                y = 1;
            }
            case "9" -> {
                x = 2;
                y = 2;
            }
            default -> {
                notValid = "ай-ай:) ввел какую-то ерунду! Введи нормальные свободные координаты (число от 1 до 9 включительно)";

            }
        }
        return new int[]{x, y};
    }

    // определяет свободно ли указанное поле для внесения символа игрока
    public static boolean freeField(int x, int y) {
        return Objects.equals(PLAYING_FIELD[x][y], "|1|") || Objects.equals(PLAYING_FIELD[x][y], "|2|")
                || Objects.equals(PLAYING_FIELD[x][y], "|3|") || Objects.equals(PLAYING_FIELD[x][y], "|4|")
                || Objects.equals(PLAYING_FIELD[x][y], "|5|") || Objects.equals(PLAYING_FIELD[x][y], "|6|")
                || Objects.equals(PLAYING_FIELD[x][y], "|7|") || Objects.equals(PLAYING_FIELD[x][y], "|8|")
                || Objects.equals(PLAYING_FIELD[x][y], "|9|");
    }

    // отрисовка хода игрока на игровом поле
    public static void drawMove(int x, int y, String coordinate) {
        // В зависимости от счетчика ходов, определяется каким символом заполнять свободное поле
        if ((movesCounter % 2 == 1)) {
            PLAYING_FIELD[x][y] = "|X|";
            steps.add(new Step("" + movesCounter, "1", coordinate));
        } else {
            PLAYING_FIELD[x][y] = "|O|";
            steps.add(new Step("" + movesCounter, "2", coordinate));
        }

    }

    // подведение резальтатов матча
    public static String result() {
        // сохраняем все ходы из списка в историю
        winGameHistory.getWinGameplay().getGame().setSteps(steps);
        // определяем победителя
        if (!GameXO.winnerSearch(PLAYING_FIELD)) {
            FIRST_PLAYER.setNumberOfDraws(FIRST_PLAYER.getNumberOfDraws() + 1);
            SECOND_PLAYER.setNumberOfDraws(SECOND_PLAYER.getNumberOfDraws() + 1);
            draw = true;
            return "Ничья! Не удивительно 8)\n" + "Игра окончена! Общий счёт: \n" + "У игрока "
                    + FIRST_PLAYER.getName() + " " + FIRST_PLAYER.getNumberOfWins() + " побед, " + FIRST_PLAYER.getNumberOfLoses() + " поражений, "
                    + FIRST_PLAYER.getNumberOfDraws() + " игр сыграно в ничью; \n" + "У игрока " + SECOND_PLAYER.getName() + " "
                    + SECOND_PLAYER.getNumberOfWins() + " побед, " + SECOND_PLAYER.getNumberOfLoses() + " поражений, "
                    + SECOND_PLAYER.getNumberOfDraws() + " игр сыграно в ничью.";
        } else if (movesCounter % 2 == 0) {
            SECOND_PLAYER.setNumberOfWins(SECOND_PLAYER.getNumberOfWins() + 1);
            FIRST_PLAYER.setNumberOfLoses(FIRST_PLAYER.getNumberOfLoses() + 1);
            // вносим второго игрока как победителя
            winGameHistory.getWinGameplay().setWinGameResult(new WinGameResult(new Player("2", SECOND_PLAYER.getName(), "O")));
            return SECOND_PLAYER.getName() + " (нолик) победил(-а)! :)\n" + "Игра окончена! Общий счёт: \n" + "У игрока "
                    + FIRST_PLAYER.getName() + " " + FIRST_PLAYER.getNumberOfWins() + " побед, " + FIRST_PLAYER.getNumberOfLoses() + " поражений, "
                    + FIRST_PLAYER.getNumberOfDraws() + " игр сыграно в ничью; \n" + "У игрока " + SECOND_PLAYER.getName() + " "
                    + SECOND_PLAYER.getNumberOfWins() + " побед, " + SECOND_PLAYER.getNumberOfLoses() + " поражений, "
                    + SECOND_PLAYER.getNumberOfDraws() + " игр сыграно в ничью.";
        } else {
            FIRST_PLAYER.setNumberOfWins(FIRST_PLAYER.getNumberOfWins() + 1);
            SECOND_PLAYER.setNumberOfLoses(SECOND_PLAYER.getNumberOfLoses() + 1);
            // вносим первого игрока как победителя
            winGameHistory.getWinGameplay().setWinGameResult(new WinGameResult(new Player("1", FIRST_PLAYER.getName(), "X")));
            return FIRST_PLAYER.getName() + " (крестик) победил(-а)! :)\n" + "Игра окончена! Общий счёт: \n" + "У игрока "
                    + FIRST_PLAYER.getName() + " " + FIRST_PLAYER.getNumberOfWins() + " побед, " + FIRST_PLAYER.getNumberOfLoses() + " поражений, "
                    + FIRST_PLAYER.getNumberOfDraws() + " игр сыграно в ничью; \n" + "У игрока " + SECOND_PLAYER.getName() + " "
                    + SECOND_PLAYER.getNumberOfWins() + " побед, " + SECOND_PLAYER.getNumberOfLoses() + " поражений, "
                    + SECOND_PLAYER.getNumberOfDraws() + " игр сыграно в ничью.";
        }
    }

    // сохранение игры
    public static String saveHistory(String fileFormat) {
        if (fileFormat.equalsIgnoreCase("XML")) {
            new XmlParser().write(winGameHistory);
            return "История игры в формате XML успешно сохранена";
        } else {
            if (draw) {
                DrawGameHistory drawGameHistory = DrawGameHistory.toDraw(winGameHistory);
                new JsonParser().write(drawGameHistory);
            } else new JsonParser().write(winGameHistory);
            return "История игры в формате JSON успешно сохранена!";
        }
    }

    // обнуление всех необходимых для новой игры полей
    public static void clearAll() {
        steps.clear();
        GameXO.refreshPlayingField(PLAYING_FIELD);
        movesCounter = 1;
        GameService.gameOver = false;
        draw = false;
        notValid = "";
    }

    // сохраняем успехи игроков в рейтинг
    public static void saveRating() {
        RatingWriter.writeRating(FIRST_PLAYER, SECOND_PLAYER);
    }
}

