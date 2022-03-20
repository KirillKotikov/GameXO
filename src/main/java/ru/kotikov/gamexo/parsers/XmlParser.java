package ru.kotikov.gamexo.parsers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.kotikov.gamexo.GameXO;
import ru.kotikov.gamexo.history.Step;
import ru.kotikov.gamexo.history.win.WinGameHistory;
import ru.kotikov.gamexo.players.Player;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

// Парсер для записи и чтения истории игры в формате XML
public class XmlParser implements Parser {
    @Override
    public String print(File fileName) {
        StringBuilder stringBuilder = new StringBuilder();

        Player winner = new Player();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileName);
            doc.getDocumentElement().normalize();
            String result = "";

            NodeList playerList = doc.getElementsByTagName("Player");
            for (int temp = 0; temp < playerList.getLength(); temp++) {
                Node playerNode = playerList.item(temp);

                if (playerNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element playerElement = (Element) playerNode;
                    if (temp == 2) {
                        winner.setId(playerElement.getAttribute("id"));
                        winner.setName(playerElement.getAttribute("name"));
                        winner.setSymbol(playerElement.getAttribute("symbol"));
                        result = ("Player " + winner.getId() + " -> " +
                                winner.getName() + " is winner as '"
                                + winner.getSymbol() + "'!");
                    }
                }
            }
            if (winner.getName() == null) {
                NodeList gameResultList = doc.getElementsByTagName("GameResult");
                Node gameResultNode = gameResultList.item(0);
                Element gameResultElement = (Element) gameResultNode;
                result = gameResultElement.getTextContent();
            }

            NodeList stepList = doc.getElementsByTagName("Step");
            for (int i = 0; i < stepList.getLength(); i++) {
                Node stepListNode = stepList.item(i);
                Element stepElement = (Element) stepListNode;
                String playerId = stepElement.getAttribute("playerId");
                String playerChar;
                if (playerId.equals("1")) playerChar = "x";
                else playerChar = "o";
                int[] coordinates = GameXO.fieldCoordinates(stepElement.getTextContent());
                playingField[coordinates[0]][coordinates[1]] = "|" + playerChar + "|";
                for (String[] charsX : playingField) {
                    for (String charsY : charsX) {
                        stringBuilder.append(charsY).append(" ");
                    }
                    stringBuilder.append("\n");
                }
                stringBuilder.append("\n");
            }
            stringBuilder.append(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Обнуляем игровое поле
        GameXO.refreshPlayingField(playingField);
        return stringBuilder.toString();
    }

    @Override
    public void write(Object object) {

        // Номер файла с историей игры
        int fileNumber = 1;
        // Имя файла с историей игры
        String fileName = "GamesHistory_" + fileNumber + ".xml";
        // Иницилизация файла с историей игры
        File file = new File(fileName);
        // Цикл для определения наличия файла с таким именем и при наличии, создает файл со следующим порядковым номером
        while (file.exists()) {
            fileNumber++;
            fileName = "GamesHistory_" + fileNumber + ".xml";
            file = new File(fileName);
        }
        // Превращаем object в WinGameHistory
        WinGameHistory winGameHistory = (WinGameHistory) object;
        // Создание писателя истории игры
        try {
            XMLStreamWriter writer = XMLOutputFactory.newInstance()
                    .createXMLStreamWriter(new FileOutputStream(fileName), "windows-1251");
            // Вносим параметры файла
            writer.writeStartDocument("windows-1251", "1.0");
            writer.writeCharacters("\n");
            // Создаем корневой элемент <JsonGameplayWrite>
            writer.writeStartElement("Gameplay");
            writer.writeCharacters("\n  ");
            // добавляем игрока X в историю <Player>
            writer.writeEmptyElement("Player");
            // Устанавливаем атрибуты
            writer.writeAttribute("id", GameXO.FIRST_PLAYER.getId());
            writer.writeAttribute("name", GameXO.FIRST_PLAYER.getName());
            writer.writeAttribute("symbol", GameXO.FIRST_PLAYER.getSymbol());
            writer.writeCharacters("\n  ");
            // добавляем игрока O в историю <Player>
            writer.writeEmptyElement("Player");
            // Устанавливаем атрибуты
            writer.writeAttribute("id", GameXO.SECOND_PLAYER.getId());
            writer.writeAttribute("name", GameXO.SECOND_PLAYER.getName());
            writer.writeAttribute("symbol", GameXO.SECOND_PLAYER.getSymbol());
            writer.writeCharacters("\n  ");

            // Добавляем ход игры <Game> в историю
            writer.writeStartElement("Game");
            writer.writeCharacters("\n    ");

            // размер списка ходов
            int size = winGameHistory.getWinGameplay().getGame().getSteps().size();
            // Перебираем каждый ход и записываем
//            for (int i = 0; i < size; i++)
            for (Step step : winGameHistory.getWinGameplay().getGame().getSteps()) {
                // добавление хода игрока в историю <Step>
                writer.writeStartElement("Step");
                writer.writeAttribute("num", "" + step.getNum());
                // записываем номер id игрока в зависимости от номера хода
                if (Integer.parseInt(step.getNum()) % 2 == 1) {
                    writer.writeAttribute("playerId", "1");
                } else {
                    writer.writeAttribute("playerId", "2");
                }
                writer.writeCharacters(step.getText());
                writer.writeEndElement();
                if (!step.equals(winGameHistory.getWinGameplay().getGame().getSteps().get(size - 1))) {
                    writer.writeCharacters("\n    ");
                }
            }
            // делаем отступ для закрытия <Game>
            writer.writeCharacters("\n  ");
            writer.writeEndElement();
            // делаем отступ и начинаем <GameResult>
            writer.writeCharacters("\n  ");
            writer.writeStartElement("GameResult");
            try {
                if (!winGameHistory.getWinGameplay().getWinGameResult().getPlayer().getName().isEmpty()) {
                    // записываем победителя
                    writer.writeEmptyElement("Player");
                    writer.writeAttribute("id", winGameHistory.getWinGameplay().getWinGameResult().getPlayer().getId());
                    writer.writeAttribute("name", winGameHistory.getWinGameplay().getWinGameResult().getPlayer().getName());
                    writer.writeAttribute("symbol", winGameHistory.getWinGameplay().getWinGameResult().getPlayer().getSymbol());
                }
            } catch (NullPointerException e) {
                writer.writeCharacters("Draw!");
            }
            // закрываем теги
            writer.writeEndElement();
            writer.writeCharacters("\n");
            writer.writeEndDocument();
            // сохраняем в файл и закрываем парсер
            writer.flush();
            writer.close();
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
