package com.fredrikpedersen.springmvcrest.controllers.v1;

import com.fredrikpedersen.springmvcrest.api.v1.model.category.CategoryDTO;
import com.fredrikpedersen.springmvcrest.services.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 16/02/2020 at 18:02
 */
class CustomerControllerTest {

    public static final String NAME = "Jim";

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

    }

    @Test
    public void testListCategories() throws Exception {
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1L);
        category1.setName(NAME);

        CategoryDTO category2 = new CategoryDTO();
        category2.setId(2L);
        category2.setName("Bob");

        List<CategoryDTO> categories = Arrays.asList(category1, category2);

        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/categories/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));
    }

    @Test
    public void testGetByNameCategories() throws Exception {
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1l);
        category1.setName(NAME);

        when(categoryService.getCategoryByName(anyString())).thenReturn(category1);

        mockMvc.perform(get("/api/v1/categories/Jim")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }
}