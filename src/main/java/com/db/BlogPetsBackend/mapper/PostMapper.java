package com.db.BlogPetsBackend.mapper;

import com.db.BlogPetsBackend.DTO.Response.ResponsePostDTO;
import com.db.BlogPetsBackend.Models.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "likes", source = "likes")
    @Mapping(target = "liked", source = "liked")
    ResponsePostDTO postToDto(Post source);
}
