package ru.kotikov.gamexo;


import ru.kotikov.gamexo.parsers.JsonParser;
import ru.kotikov.gamexo.parsers.XmlParser;

import java.io.File;

public class Main {

    public static void main(String[] args) {

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
