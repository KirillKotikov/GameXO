package ru.kotikov.gamexo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import ru.kotikov.gamexo.controllers.MainController;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController mainController;

    @Test
    public void contextLoads() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Привет! Добро пожаловать в игру крестики-нолики:)")))
                .andExpect(content().string(containsString("Запуск игры Крестики-нолики:")))
                .andExpect(content().string(containsString("Отображение истории игры из файла:")))
                .andExpect(content().string(containsString("Выберите .xml или .json файл с историей игры \"Крестики-нолики\" для отображения.")));
    }
//
//    @Test
//    public void uploadFileTest() throws Exception {
//        MockMultipartHttpServletRequestBuilder multipart = multipart("/")
//                .file("file", "123".getBytes());
//
//        this.mockMvc.perform(multipart)
//                .andDo(print())
//                .andExpect(content().string(containsString("Выберите .xml или .json файл с историей игры \"Крестики-нолики\" для отображения:")));
//    }

}
