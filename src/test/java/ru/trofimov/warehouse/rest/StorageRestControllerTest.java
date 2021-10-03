package ru.trofimov.warehouse.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.trofimov.warehouse.model.Storage;
import ru.trofimov.warehouse.service.StorageService;

import java.util.Arrays;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StorageRestController.class)
class StorageRestControllerTest {

    @MockBean
    private StorageService storageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetAllStorage() throws Exception {
        String url = "/api/v1/categories/1/goods/1/storage/";
        Storage storage = new Storage();
        storage.setId(1L);
        storage.setRow(1);
        storage.setPlace(1);
        storage.setAmount(1);
        storage.setGoodsId(1L);
        when(storageService.findAll()).thenReturn(Arrays.asList(storage));
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"totalItems\":0,\"offset\":0,\"items\":[],\"previousPage\":\"http://localhost/api/v1/categories/1/goods/1/storage/?limit=50\",\"nextPage\":null}")));
    }
}
