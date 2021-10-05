package ru.trofimov.warehouse.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.trofimov.warehouse.model.Goods;
import ru.trofimov.warehouse.rest.implement.GoodsRestControllerImpl;
import ru.trofimov.warehouse.service.GoodsService;
import ru.trofimov.warehouse.service.StorageService;

import java.util.Arrays;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GoodsRestControllerImpl.class)
class GoodsRestControllerTest {

    @MockBean
    private GoodsService goodsService;

    @MockBean
    private StorageService storageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetAllGoods() throws Exception {
        String url = "/api/v1/categories/1/goods/";
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setName("test");
        goods.setDescription("description");
        goods.setPrice(1);
        goods.setCategoryId(1L);
        when(goodsService.findAll()).thenReturn(Arrays.asList(goods));
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"totalItems\":0,\"offset\":0,\"items\":[],\"previousPage\":\"http://localhost/api/v1/categories/1/goods/?limit=50\",\"nextPage\":null}")));
    }

    @Test
    void shouldGetAllGoodsWithOffset() throws Exception {
        String url = "/api/v1/categories/1/goods/?offset=1";
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setName("test1");
        goods.setDescription("description1");
        goods.setPrice(1);
        goods.setCategoryId(1L);
        Goods goods2 = new Goods();
        goods.setId(2L);
        goods.setName("test2");
        goods.setDescription("description2");
        goods.setPrice(2);
        goods.setCategoryId(2L);
        when(goodsService.findAll()).thenReturn(Arrays.asList(goods, goods2));
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"totalItems\":0,\"offset\":1,\"items\":[],\"previousPage\":\"http://localhost/api/v1/categories/1/goods/?offset=1&limit=50\",\"nextPage\":null}")));
    }

    @Test
    void shouldGetAllGoodsWithLimit() throws Exception {
        String url = "/api/v1/categories/1/goods/?limit=1";
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setName("test1");
        goods.setDescription("description1");
        goods.setPrice(1);
        goods.setCategoryId(1L);
        Goods goods2 = new Goods();
        goods.setId(2L);
        goods.setName("test2");
        goods.setDescription("description2");
        goods.setPrice(2);
        goods.setCategoryId(2L);
        when(goodsService.findAll()).thenReturn(Arrays.asList(goods,goods2));
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"totalItems\":0,\"offset\":0,\"items\":[],\"previousPage\":\"http://localhost/api/v1/categories/1/goods/?limit=1\",\"nextPage\":null}")));
    }
}
