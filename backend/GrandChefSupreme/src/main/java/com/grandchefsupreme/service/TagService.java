package com.grandchefsupreme.service;


import com.grandchefsupreme.dto.TagDTO;
import com.grandchefsupreme.mapper.TagMapper;
import com.grandchefsupreme.model.Tag;
import com.grandchefsupreme.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    public List<TagDTO> getAllTags(){

        List<Tag> tags = tagRepository.findAll();
        return tagMapper.toDto(tags);
    }


}
