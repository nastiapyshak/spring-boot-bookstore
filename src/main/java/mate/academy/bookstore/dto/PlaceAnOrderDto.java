package mate.academy.bookstore.dto;

import jakarta.validation.constraints.NotBlank;

public record PlaceAnOrderDto(@NotBlank
                              String shippingAddress) {
}
