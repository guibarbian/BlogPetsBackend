package com.db.BlogPetsBackend.DTO.Response;

public record ResponsePostDTO(
        Long id,
        String title,
        String content,
        Integer likes,
        boolean liked
) {
}
