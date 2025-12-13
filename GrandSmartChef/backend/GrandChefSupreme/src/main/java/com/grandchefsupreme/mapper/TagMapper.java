package com.grandchefsupreme.mapper;

import com.grandchefsupreme.dto.TagDTO;
import com.grandchefsupreme.model.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDTO toDto(Tag tag);

    Tag toEntity(TagDTO tagDTO);

    List<TagDTO> toDto(List<Tag> tags);

    List<Tag> toEntity(List<TagDTO> tagDTOs);
}
