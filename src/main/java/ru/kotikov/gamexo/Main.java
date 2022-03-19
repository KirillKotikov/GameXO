package ru.kotikov.gamexo;


import ru.kotikov.gamexo.parsers.JsonParser;
import ru.kotikov.gamexo.parsers.XmlParser;

public class Main {

    public static void main(String[] args) {

        // Запуск игры
        GameXO.game();

        // Чтение XML файлов. В параметры вводить название файла из корня проекта или полный путь:
        // Выиграл крестик -
//        new XmlParser().print("GamesHistory_1.xml");
        // Выиграл нолик -
//        new XmlParser().print("GamesHistory_2.xml");
        // Ничья -
//        new XmlParser().print("GamesHistory_3.xml");

        // Чтение JSON файлов. В параметры вводить название файла из корня проекта или полный путь:
        // Выиграл крестик -
//        new JsonParser().print("GamesHistory_1.json");
        // Выиграл нолик -
//        new JsonParser().print("GamesHistory_2.json");
        // Ничья -
//        new JsonParser().print("GamesHistory_3.json");
    }
}
