package mate.academy.bookstore.dto;

import jakarta.validation.constraints.NotNull;
import mate.academy.bookstore.model.Status;

public record UpdateOrderStatusDto(@NotNull
                                   Status status) {
}
