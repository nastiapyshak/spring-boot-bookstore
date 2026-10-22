package mate.academy.bookstore.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record CreateBookRequestDto(
        @NotBlank
        String title,

        @NotBlank
        String author,

        @NotBlank
        String isbn,

        @NotNull
        @DecimalMin("0.0")
        BigDecimal price,

        String description,

        String coverImage,

        List<Long> categoryIds
) {
}
