package com.example.eindopdrachtbackend.controller;

import com.example.eindopdrachtbackend.dto.input.ImageInputDto;
import com.example.eindopdrachtbackend.dto.output.ImageOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.service.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ImageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ImageService imageService;

    private ImageInputDto imageInputDto;
    private ImageOutputDto imageOutputDto;

    @BeforeEach
    void setUp() {
        // Set up test data
        imageInputDto = new ImageInputDto();
        imageInputDto.setUrl("http://example.com/test-image.jpg");

        imageOutputDto = new ImageOutputDto();
        imageOutputDto.setId(1L);
        imageOutputDto.setUrl("http://example.com/test-image.jpg");
    }

    @Test
    void testGetImageById() throws Exception {
        when(imageService.getImageById(anyLong())).thenReturn(imageOutputDto);

        MvcResult result = mockMvc.perform(get("/images/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.url").value("http://example.com/test-image.jpg"))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        ImageOutputDto responseDto = objectMapper.readValue(response, ImageOutputDto.class);

        assertThat(responseDto.getId()).isEqualTo(1);
        assertThat(responseDto.getUrl()).isEqualTo("http://example.com/test-image.jpg");
    }

    @Test
    void testUpdateImage() throws Exception {
        when(imageService.updateImage(anyLong(), Mockito.any(ImageInputDto.class))).thenReturn(imageOutputDto);

        String inputJson = objectMapper.writeValueAsString(imageInputDto);

        mockMvc.perform(put("/images/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.url").value("http://example.com/test-image.jpg"));
    }

    @Test
    void testDeleteImage() throws Exception {
        when(imageService.deleteImage(anyLong())).thenReturn("Image deleted successfully");

        mockMvc.perform(delete("/images/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Image deleted successfully"));
    }

    @Test
    void testGetImageById_NotFound() throws Exception {
        when(imageService.getImageById(anyLong())).thenThrow(new RecordNotFoundException("Image not found"));

        mockMvc.perform(get("/images/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Image not found"));
    }
}
