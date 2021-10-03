package ru.trofimov.warehouse.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.trofimov.warehouse.model.Category;
import ru.trofimov.warehouse.service.CategoryService;
import ru.trofimov.warehouse.service.GoodsService;

import java.util.Arrays;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryRestController.class)
class CategoryRestControllerTest {

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private GoodsService goodsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetAllCategory() throws Exception {
        String url = "/api/v1/categories/";
        Category category = new Category();
        category.setId(1L);
        category.setName("test");
        when(categoryService.findAll()).thenReturn(Arrays.asList(category));
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"totalItems\":1,\"offset\":0,\"items\":[{\"id\":1,\"name\":\"test\"}],\"previousPage\":\"http://localhost/api/v1/categories/?limit=50\",\"nextPage\":null}")));
    }
}
