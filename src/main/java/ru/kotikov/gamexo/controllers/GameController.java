package ru.kotikov.gamexo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kotikov.gamexo.GameXO;
import ru.kotikov.gamexo.services.GameService;

@Controller
public class GameController {
    // Отображает страницу с вводом имен игроков
    @GetMapping("/gameplay")
    public String startGame() {
        return "gameplay/start";
    }

    // Проверяет валидность введенных имен и переносит на страницу для хода
    @PostMapping("/gameplay/new-field")
    public String registration(@RequestParam(name = "nameX") String nameX, @RequestParam(name = "nameO") String nameO, Model model) {
        String nameXNull, nameONull;
        if (nameX.trim().isEmpty()) {
            nameXNull = "Ты ввёл пустую строку:(";
            model.addAttribute("nameXNull", nameXNull);
            if (nameO.trim().isEmpty()) {
                nameONull = "Ты ввёл пустую строку:(";
                model.addAttribute("nameONull", nameONull);
            }
            return "/gameplay/repeatName";
        }
        if (nameO.trim().isEmpty()) {
            nameONull = "Ты ввёл пустую строку:(";
            model.addAttribute("nameONull", nameONull);
            return "/gameplay/repeatName";
        }
        GameService.setNames(nameX, nameO);
        model.addAttribute("field", GameService.showPlayingField(GameService.PLAYING_FIELD));
        model.addAttribute("name", GameService.getMoveName());
        return "/gameplay/field";
    }

    // Обновляет страницу с полем и ходом игрока
    @PostMapping("/gameplay/field")
    public String move(@RequestParam(name = "coordinate") String coordinate, Model model) {
        // сообщение при возникновении ошибки
        String message;
        // проверяем валидность введенных координат
        if (GameService.coordinateValid(coordinate)[0] == 9) {
            message = GameService.notValid;
            model.addAttribute("message", message);
            model.addAttribute("name", GameService.getMoveName());
            return "/gameplay/repeatField";
        }
        // проверяем ни занято ли поле
        if (GameService.freeField(GameService.coordinateValid(coordinate)[0],
                GameService.coordinateValid(coordinate)[1])) {
            GameService.drawMove(GameService.coordinateValid(coordinate)[0],
                    GameService.coordinateValid(coordinate)[1], coordinate);
        } else {
            message = "Эта ячейка занята! Выбери другую!";
            model.addAttribute("message", message);
            model.addAttribute("name", GameService.getMoveName());
            model.addAttribute("field", GameService.showPlayingField(GameService.PLAYING_FIELD));
            return "/gameplay/repeatField";
        }
        // ищем победителя или ничью
        if (GameService.movesCounter > 5 && GameService.movesCounter < 9) {
            GameService.gameOver = GameXO.winnerSearch(GameService.PLAYING_FIELD);
        } else if (GameService.movesCounter > 8) GameService.gameOver = true;
        // сохраняем игровое поле для отображения
        model.addAttribute("field", GameService.showPlayingField(GameService.PLAYING_FIELD));
        // Выводим результаты если игра окончена
        if (GameService.gameOver) {
            message = GameService.result();
            model.addAttribute("message", message);
            return "/gameplay/result";
        }
        // продолжаем игру если она не окончена
        else {
            GameService.movesCounter++;
            model.addAttribute("name", GameService.getMoveName());
            return "/gameplay/field";
        }
    }

    // получаем ответы игрока по поводу сохранения истории игры и начала новой
    @PostMapping("/gameplay/result")
    public String result(@RequestParam(name = "saveH") String saveH, @RequestParam(name = "fileFormat", defaultValue = "Нет") String fileFormat,
                         @RequestParam(name = "newGame") String newGame, Model model) {
        String save = "";
        // если игрок решил сохранить игру
        if (saveH.equalsIgnoreCase("Да")) {
            // создается файл с указанным форматом
            save = GameService.saveHistory(fileFormat);
        }
        // если хочет начать новую игру
        if (newGame.equalsIgnoreCase("Да")) {
            // очищаем все поля игры
            GameService.clearAll();
            return "redirect:/gameplay/new-field";
        } else // закрытие игры
            model.addAttribute("save", save);
        model.addAttribute("message", "Спасибо за игру! Заходите поиграть еще!");
        return "/gameplay/finalResult";
    }

    // начала нового матча с сохранением имен и статистики игроков
    @GetMapping("/gameplay/new-field")
    public String newField(Model model) {
        // сохраняем игровое поле для отображения
        model.addAttribute("field", GameService.showPlayingField(GameService.PLAYING_FIELD));
        model.addAttribute("name", GameService.getMoveName());
        return "/gameplay/field";
    }

    // Реашем вопрос сохранения рейтинга и заканчиваем игру
    @PostMapping("/gameplay/final-result")
    public String finalResult(@RequestParam(name = "saveR") String saveR, Model model) {
        String message = "";
        if (saveR.equalsIgnoreCase("Да")) {
            GameService.saveRating();
            message = "Рейтинг успешно сохранен! ";
        }
        model.addAttribute("message", message);
        return "/gameplay/goodBye";
    }
}


