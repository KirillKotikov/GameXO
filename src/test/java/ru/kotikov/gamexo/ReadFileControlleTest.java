package ru.kotikov.gamexo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.kotikov.gamexo.controllers.ReadFileController;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ReadFileControlleTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReadFileController readFileController;
    @Test
    public void printFileContextLoads() throws Exception {
        this.mockMvc.perform(get("/print-file"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString
                        ("Выберите .xml или .json файл с историей игры \"Крестики-нолики\" для отображения:")));
    }
}
