package ru.kotikov.gamexo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.kotikov.gamexo.controllers.GameController;
import ru.kotikov.gamexo.services.GameService;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameController gameController;

    @Test
    public void startContextLoads() throws Exception {
        this.mockMvc.perform(get("/gameplay"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Введите имя первого игрока (Ходит крестиками):")))
                .andExpect(content().string(containsString("Введите имя второго игрока (Ходит ноликами):")));
    }

    @Test
    public void newGameContextLoads() throws Exception {
        GameService.FIRST_PLAYER.setName("Kirill");
        GameService.SECOND_PLAYER.setName("Alena");
        this.mockMvc.perform(get("/gameplay/new-field"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Игровое поле:")))
                .andExpect(content().string(containsString("Kirill (крестики), твой ход! Введи свободные координаты хода (число от 1 до 9 включительно):")));
    }
}
