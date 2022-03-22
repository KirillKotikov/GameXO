package ru.kotikov.gamexo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kotikov.gamexo.services.GameService;

@Controller
public class MainController {

    // Главная страница с кнопками для загрузки файла
    @GetMapping("/")
    public String readFile() {
        GameService.clearAll();
        return "main";
    }
}
