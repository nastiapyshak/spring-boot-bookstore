package mate.academy.bookstore.dto;

public record OrderItemDto(Long id,
                           Long bookId,
                           int quantity
) {
}
