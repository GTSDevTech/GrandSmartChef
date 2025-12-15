package com.grandchefsupreme.controller;

import com.grandchefsupreme.dto.TagDTO;
import com.grandchefsupreme.service.TagService;
import com.grandchefsupreme.utils.ApiResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/all")
    public List<TagDTO> allRecipes(
            HttpServletRequest request
    ){

        request.setAttribute(
                ApiResponseMessage.MESSAGE_ATTR,
                "Tag cargados correctamente"
        );
        return tagService.getAllTags();
    }
}
