package mate.academy.bookstore.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(@NotBlank
                                 String name,
                                 String description) {
}
