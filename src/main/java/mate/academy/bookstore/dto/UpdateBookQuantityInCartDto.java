package mate.academy.bookstore.dto;

import jakarta.validation.constraints.Positive;

public record UpdateBookQuantityInCartDto(@Positive
                                          int quantity) {
}
