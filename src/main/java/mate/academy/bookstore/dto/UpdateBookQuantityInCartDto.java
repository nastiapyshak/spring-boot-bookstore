package mate.academy.bookstore.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateBookQuantityInCartDto(@NotNull
                                          @Positive
                                          Integer quantity) {
}
