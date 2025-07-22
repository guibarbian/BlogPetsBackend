package com.db.BlogPetsBackend.DTO.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record RequestPostDTO(
        @NotNull
        @Size(min=1, max=15, message = "Title must be between 1 and 15 characters long")
        String title,

        @NotNull
        @Size(min=1, max=255, message = "Content must be between 1 and 255 characters long")
        String content,

        @NotNull
        @PositiveOrZero
        Integer likes,

        @NotNull
        Boolean liked
) {
}
