package ru.kotikov.gamexo;

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
import java.util.Scanner;

public class GameXO {
    // Массив игрового поля
    public static final String[][] PLAYING_FIELD = new String[][]{{"|1|", "|2|", "|3|"}, {"|4|", "|5|", "|6|"}, {"|7|", "|8|", "|9|"}};
    // Счетчик ходов
    static byte movesCounter = 1;
    // Определяет игра продолжается или окончена (есть победитель или ничья)
    static boolean gameOver = false;
    //Первый игрок - х
    public static final PlayerWithStat FIRST_PLAYER = new PlayerWithStat("1", "X");
    //Второй игрок - о
    public static final PlayerWithStat SECOND_PLAYER = new PlayerWithStat("2", "O");
    // запись истории игры
    public static WinGameHistory winGameHistory = new WinGameHistory();

    // Игровой процесс
    public static void game() {
        // Приветствие
        System.out.println("Привет! Добро пожаловать в игру крестики-нолики:)");
        // Игра идет пока не найден победитель или нет ничьи
        while (!gameOver) {
            // При начальном запуске игры вводятся имена игроков
            if (FIRST_PLAYER.getName() == null) {
                // Сканнер для считывания имен из консоли
                Scanner namesScanner = new Scanner(System.in);
                String name;
                // Ввод и проверка имени первого игрока
                while (true) {
                    System.out.println("Первый игрок (будешь ходить крестиками), введи своё имя:");
                    name = namesScanner.nextLine();
                    if (name.trim().isEmpty()) {
                        System.out.println("Ты ввёл пустую строку:(");
                    } else {
                        GameXO.FIRST_PLAYER.setName(name);
                        break;
                    }
                }
                // Ввод и проверка имени второго игрока
                while (true) {
                    System.out.println("Второй игрок (будешь ходить ноликами), введи своё имя:");
                    name = namesScanner.nextLine();
                    if (name.trim().isEmpty()) {
                        System.out.println("Ты ввёл пустую строку:(");
                    } else {
                        GameXO.SECOND_PLAYER.setName(name);
                        break;
                    }
                }
                // Отображение игрового поля перед игрой
                System.out.println("Вот как выглядит игровое поле:");
                GameXO.showPlayingField(PLAYING_FIELD);
            }
            // Список в который будут заисываться ходы игроков
            ArrayList<Step> steps = new ArrayList<>();
            // Цикл для ходов пока игра не окончена
            while (!gameOver) {
                Player[] players = {new Player("1", FIRST_PLAYER.getName(), "X"), new Player("2", SECOND_PLAYER.getName(), "O")};
                winGameHistory.setWinGameplay(new WinGameplay(players));
                // Оповещение об очередности хода
                if (GameXO.movesCounter % 2 == 1) {
                    System.out.println(GameXO.FIRST_PLAYER.getName() + " (крестики), твой ход! Введи координаты хода (число от 1 до 9 включительно): ");
                } else {
                    System.out.println(GameXO.SECOND_PLAYER.getName() + " (нолики), твой ход! Введи координаты хода (число от 1 до 9 включительно): ");
                }
                // Считывает ход игрока
                Scanner scannerMove = new Scanner(System.in);
                // Сохраняем введенные координаты
                String coordinateFromPlayer = scannerMove.next();
                // Проверка координат на длину
                if (coordinateFromPlayer.length() != 1) {
                    System.out.println("ай-ай:) ввел какую-то ерунду! Введи нормальные координаты (число от 1 до 9 включительно)");
                    continue;
                }
                // Переменные для координат хода на поле
                int x, y;
                // Проверка введенных координат на соответствие возможным
                switch (coordinateFromPlayer) {
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
                        System.out.println("ай-ай:) ввел какую-то ерунду! Введи нормальные координаты (число от 1 до 9 включительно)");
                        continue;
                    }
                }
                // Проверка на уже заполненное поле
                if (freeField(x, y)) {
                    // В зависимости от счетчика ходов, определяется каким символом заполнять свободное поле
                    if ((GameXO.movesCounter % 2 == 1)) {
                        GameXO.PLAYING_FIELD[x][y] = "|X|";
                        steps.add(new Step("" + movesCounter, "1", coordinateFromPlayer));
                    } else {
                        GameXO.PLAYING_FIELD[x][y] = "|O|";
                        steps.add(new Step("" + movesCounter, "2", coordinateFromPlayer));
                    }
                    // Инкрементация счетчика ходов
                    GameXO.movesCounter++;
                } else {
                    System.out.println("Здесь уже занято:)");
                }
                // Проверка на победителя или ничью
                if (movesCounter > 5 && movesCounter < 10) {
                    gameOver = GameXO.winnerSearch(PLAYING_FIELD);
                } else if (movesCounter > 9) gameOver = true;
                // отображает вид игрового поля в настоящий момент
                GameXO.showPlayingField(PLAYING_FIELD);
                // подводим итоги игры
                if (gameOver) {
                    // определяем победителя
                    boolean draw = false;
                    if (!winnerSearch(PLAYING_FIELD)) {
                        System.out.println("Ничья! Не удивительно 8)");
                        FIRST_PLAYER.setNumberOfDraws(FIRST_PLAYER.getNumberOfDraws() + 1);
                        SECOND_PLAYER.setNumberOfDraws(SECOND_PLAYER.getNumberOfDraws() + 1);
                        draw = true;
                    } else if (movesCounter % 2 == 1) {
                        System.out.println(SECOND_PLAYER.getName() + " (нолик) победил(-а)! :)");
                        SECOND_PLAYER.setNumberOfWins(SECOND_PLAYER.getNumberOfWins() + 1);
                        FIRST_PLAYER.setNumberOfLoses(FIRST_PLAYER.getNumberOfLoses() + 1);
                        // вносим вторгго игрока как победителя
                        winGameHistory.getWinGameplay().setWinGameResult(new WinGameResult(new Player("2", SECOND_PLAYER.getName(), "O")));
                    } else {
                        System.out.println(FIRST_PLAYER.getName() + " (крестик) победил(-а)! :)");
                        FIRST_PLAYER.setNumberOfWins(FIRST_PLAYER.getNumberOfWins() + 1);
                        SECOND_PLAYER.setNumberOfLoses(SECOND_PLAYER.getNumberOfLoses() + 1);
                        // вносим первого игрока как победителя
                        winGameHistory.getWinGameplay().setWinGameResult(new WinGameResult(new Player("1", FIRST_PLAYER.getName(), "X")));
                    }
                    // сохраняем все ходы из списка в историю
                    winGameHistory.getWinGameplay().getGame().setSteps(steps);
                    // Вопрос о необходимости записи игры
                    System.out.println("Вы хотите сохранить историю данной игры в отдельный файл? Введите \"да\" или \"нет\".");
                    // проверяем валидность ответа
                    while (true) {
                        // Считыватель ответа игроков
                        Scanner writerScanner = new Scanner(System.in);
                        // сохраняем ответ игроков
                        String writeAnswer = writerScanner.next();
                        if (writeAnswer.equalsIgnoreCase("да")) {
                            System.out.println("Вы хотите сохранить игру в \"XML\" или \"JSON\" формате? Если \"XML\", " + "то введите число 1, если \"JSON\" - 2");
                            String fileFormat = writerScanner.next();
                            if (fileFormat.equals("1")) {
                                new XmlParser().write(winGameHistory);
                                System.out.println("История игры в формате XML успешно сохранена!");
                            } else if (fileFormat.equals("2")) {
                                if (draw) {
                                    DrawGameHistory drawGameHistory = DrawGameHistory.toDraw(winGameHistory);
                                    new JsonParser().write(drawGameHistory);
                                } else new JsonParser().write(winGameHistory);
                                System.out.println("История игры в формате JSON успешно сохранена!");
                            } else System.out.println("Вы ввели неверный ответ! Введите \"1\" - XML или \"2\" - JSON.");
                            break;
                        } else if (writeAnswer.equalsIgnoreCase("нет")) {
                            break;
                        } else System.out.println("Вы ввели неверный ответ! Введите \"да\" или \"нет\".");
                    }
                    // Очищаем список ходов, поле и счетчик ходов на случай повторной игры
                    endRound();
                    if (!gameOver) {
                        steps.clear();
                        GameXO.refreshPlayingField(GameXO.PLAYING_FIELD);
                        GameXO.movesCounter = 1;
                    }
                }
            }
        }
        // блок для решения вопроса сохранения данных в рейтинг
        System.out.println("Вы хотите сохранить ваши результаты в рейтинг? Введите \"да\" или \"нет\":)");
        Scanner ratingScanner = new Scanner(System.in);
        while (true) {
            String answerRating = ratingScanner.next();
            if (answerRating.equalsIgnoreCase("да")) {
                RatingWriter.writeRating();
                System.out.println("Рейтинг успешно сохранен!");
                break;
            } else if (!answerRating.equalsIgnoreCase("нет")) {
                System.out.println("Вы ввели неверный ответ! Введите \"да\" или \"нет\".");
            }
        }
        System.out.println("Спасибо за игру! Заходите поиграть ещё:)");
    }

    // Поиск возможного победителя
    public static boolean winnerSearch(String[][] playingField) {
        boolean haveWinner = (playingField[0][0].equals(playingField[0][1])) && (playingField[0][1].equals(playingField[0][2]));

        if ((playingField[1][0].equals(playingField[1][1])) && (playingField[1][1].equals(playingField[1][2]))) {
            haveWinner = true;
        }
        if ((playingField[2][0].equals(playingField[2][1])) && (playingField[2][1].equals(playingField[2][2]))) {
            haveWinner = true;
        }

        if ((playingField[0][0].equals(playingField[1][0])) && (playingField[1][0].equals(playingField[2][0]))) {
            haveWinner = true;
        }
        if ((playingField[0][1].equals(playingField[1][1])) && (playingField[1][1].equals(playingField[2][1]))) {
            haveWinner = true;
        }
        if ((playingField[0][2].equals(playingField[1][2])) && (playingField[1][2].equals(playingField[2][2]))) {
            haveWinner = true;
        }

        if ((playingField[0][0].equals(playingField[1][1])) && (playingField[1][1].equals(playingField[2][2]))) {
            haveWinner = true;
        }
        if ((playingField[0][2].equals(playingField[1][1])) && (playingField[1][1].equals(playingField[2][0]))) {
            haveWinner = true;
        }
        return haveWinner;
    }

    // Подведение результатов игры. Возвращает 0 - ничья, 1 - выиграл первый игрок, 2 - выиграл второй игрок.
    public static void endRound() {
        System.out.println("Игра окончена! Общий счёт: \n" + "У игрока " + FIRST_PLAYER.getName() + " " + FIRST_PLAYER.getNumberOfWins() + " побед, " + FIRST_PLAYER.getNumberOfLoses() + " поражений, " + FIRST_PLAYER.getNumberOfDraws() + " игр сыграно в ничью; \n" + "У игрока " + SECOND_PLAYER.getName() + " " + SECOND_PLAYER.getNumberOfWins() + " побед, " + SECOND_PLAYER.getNumberOfLoses() + " поражений, " + SECOND_PLAYER.getNumberOfDraws() + " игр сыграно в ничью.");
        System.out.println("Хотите сыграть ещё разок?;) Введи на клавиатуре ответ \"Да\" или \"Нет\":");
        // Определение начала нового раунда или окончания игры.
        Scanner answerScanner = new Scanner(System.in);
        while (true) {
            String answer = answerScanner.nextLine();
            if (answer.equalsIgnoreCase("да")) {
                GameXO.gameOver = false;
                break;
            } else if (answer.equalsIgnoreCase("нет")) {
                break;
            } else System.out.println("Вы ввели неверный ответ! Введите \"да\" или \"нет\"");
        }
    }

    // Обновление игрового поля (очистка)
    public static void refreshPlayingField(String[][] playingField) {
        int fieldCount = 1;
        for (int i = 0; i < playingField.length; i++) {
            for (int j = 0; j < playingField.length; j++) {
                playingField[i][j] = "|" + fieldCount + "|";
                fieldCount++;
            }
        }
    }

    // Отображение игрового поля в текущем состоянии
    public static void showPlayingField(String[][] playingField) {
        for (String[] charsX : playingField) {
            for (String charsY : charsX) {
                System.out.print(charsY + " ");
            }
            System.out.println();
        }
    }

    // Возвращает массив координат x, y игрового поля в зависимости от введенной координаты игроком
    public static int[] fieldCoordinates(String coordinate) {
        // переменная для внесения координат
        int[] coordinates = new int[2];
        // конвертация номера ячейки игрового поля в координаты
        switch (coordinate) {
            case "1" -> {
            }
            case "2" -> coordinates[1] = 1;
            case "3" -> coordinates[1] = 2;
            case "4" -> coordinates[0] = 1;
            case "5" -> {
                coordinates[0] = 1;
                coordinates[1] = 1;
            }
            case "6" -> {
                coordinates[0] = 1;
                coordinates[1] = 2;
            }
            case "7" -> coordinates[0] = 2;
            case "8" -> {
                coordinates[0] = 2;
                coordinates[1] = 1;
            }
            case "9" -> {
                coordinates[0] = 2;
                coordinates[1] = 2;
            }
        }
        return coordinates;
    }

    // определяет свободно ли указанное поле для внесения символа игрока
    public static boolean freeField(int x, int y) {
        return Objects.equals(GameXO.PLAYING_FIELD[x][y], "|1|") || Objects.equals(GameXO.PLAYING_FIELD[x][y], "|2|")
                || Objects.equals(GameXO.PLAYING_FIELD[x][y], "|3|") || Objects.equals(GameXO.PLAYING_FIELD[x][y], "|4|")
                || Objects.equals(GameXO.PLAYING_FIELD[x][y], "|5|") || Objects.equals(GameXO.PLAYING_FIELD[x][y], "|6|")
                || Objects.equals(GameXO.PLAYING_FIELD[x][y], "|7|") || Objects.equals(GameXO.PLAYING_FIELD[x][y], "|8|")
                || Objects.equals(GameXO.PLAYING_FIELD[x][y], "|9|");
    }
}

