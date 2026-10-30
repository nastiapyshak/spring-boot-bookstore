package mate.academy.bookstore.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddBookToCartDto(@NotNull
                               Long bookId,
                               @Positive
                               int quantity) {
}
