package ru.kotikov.gamexo.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.kotikov.gamexo.GameXO;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

// Парсер для записи и чтения истории игры в формате JSON
public class JsonParser implements Parser {

    // Выводит в консоль историю игры по определенному шаблону
    @Override
    public void print(String fileName) {
        try {
            // Создаем объект JSONParser(
            Object obj = new JSONParser().parse(new FileReader(fileName));
            // Кастим obj в JSONObject
            JSONObject jsonObject = (JSONObject) obj;
            // Берем из файла данные gameplay
            JSONObject gameplay = (JSONObject) jsonObject.get("gameplay");
            // Берем из файла данные game, которые в свою очеред внутри gameplay
            JSONObject game = (JSONObject) gameplay.get("game");
            // Берем из файла данные step, которые внутри game
            JSONArray steps = (JSONArray) game.get("step");
            // Перебираем ходы в цикле для вывода на экран
            for (Object o : steps) {
                // кастим объект в json объект
                JSONObject step = (JSONObject) o;
                // получем id игрока данного хода, чтобы определить символ
                String playerId = String.valueOf(step.get("playerId"));
                String playerChar;
                if (playerId.equals("1")) playerChar = "X";
                else playerChar = "O";
                // вычисляем координаты на поле в зависимости от номера ячейки в данном периоде
                int[] coordinates = GameXO.fieldCoordinates(String.valueOf(step.get("text")));
                // усанавливаем символ в нужное место на поле
                playingField[coordinates[0]][coordinates[1]] = "|" + playerChar + "|";
                // выводим поле на экран
                for (String[] charsX : playingField) {
                    for (String charsY : charsX) {
                        System.out.print(charsY + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
            // Пробуем определить наличие победителя
            try {
                JSONObject gameResult = (JSONObject) gameplay.get("gameResult");
                JSONObject player = (JSONObject) gameResult.get("player");
                System.out.println("Player " + player.get("id") + " -> " +
                        player.get("name") + " is winner as '"
                        + player.get("symbol") + "'!");
                // если в файле нет графы с игроком, то значит ничья и выводим данную информацию
            } catch (ClassCastException e) {
                System.out.println("Draw!");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    // Записывает историю игры по определенному шаблону в файл GameHistory_номер файла.json в корень проекта
    @Override
    public void write(Object object) {
        // Номер файла с историей игры
        int fileNumber = 1;
        // Имя файла с историей игры
        String fileName = "GamesHistory_" + fileNumber + ".json";
        // Иницилизация файла с историей игры
        File file = new File(fileName);
        // Цикл для определения наличия файла с таким именем и при наличии, создает файл со следующим порядковым номером
        while (file.exists()) {
            fileNumber++;
            fileName = "GamesHistory_" + fileNumber + ".json";
            file = new File(fileName);
        }
        // создаем объект с опциями красивой записи json файла
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            // записываем объект в файл
            mapper.writeValue(file, object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
