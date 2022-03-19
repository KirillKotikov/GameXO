package ru.kotikov.gamexo.parsers;

// Парсер для чтения истории игры
public interface Parser {
    String[][] playingField = new String[][]{
            {"|1|", "|2|", "|3|"},
            {"|4|", "|5|", "|6|"},
            {"|7|", "|8|", "|9|"}
    };

    void print(String fileName);

    void write(Object object);
}
