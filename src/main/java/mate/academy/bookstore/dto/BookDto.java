package mate.academy.bookstore.dto;

import java.math.BigDecimal;
import java.util.Set;

public record BookDto(
        Long id,
        String title,
        String author,
        String isbn,
        BigDecimal price,
        String description,
        String coverImage,
        Set<Long> categoryIds
) {
}
