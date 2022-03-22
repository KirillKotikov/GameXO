package ru.kotikov.gamexo;


import ru.kotikov.gamexo.parsers.JsonParser;
import ru.kotikov.gamexo.parsers.XmlParser;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {

//        // создаем окно для воспроизведения игры
//        JFrame window = new JFrame("GameXO");
//        // создаем кнопку для закрытия окна
//        window.setDefaultCloseOperation((WindowConstants.EXIT_ON_CLOSE));
//        // устанавливаем размеры окна
//        window.setSize(400,400);
//        // менеджер компановки
//        window.setLayout(new BorderLayout());
//        // делаем, чтобы отображалось по центру
//        window.setLocationRelativeTo(null);
//        // делаем окно видимым
//        window.setVisible(true);
//
//        RestGame restGame = new RestGame();
//
//        window.add(restGame);




        // Запуск игры
//        GameXO.game();

        // Чтение XML файлов. В параметры вводить название файла из корня проекта или полный путь:
        // Выиграл крестик -
//        System.out.println(new XmlParser().print(new File("GamesHistory_1.xml")));
        // Выиграл нолик -
//        System.out.println(new XmlParser().print(new File("GamesHistory_2.xml")));
        // Ничья -
//        System.out.println(new XmlParser().print(new File("GamesHistory_3.xml")));

        // Чтение JSON файлов. В параметры вводить название файла из корня проекта или полный путь:
        // Выиграл крестик -
//        System.out.println(new JsonParser().print(new File("GamesHistory_1.json")));
        // Выиграл нолик -
//        System.out.println(new JsonParser().print(new File("GamesHistory_2.json")));
        // Ничья -
//        System.out.println(new JsonParser().print(new File("GamesHistory_3.json")));
    }
}
