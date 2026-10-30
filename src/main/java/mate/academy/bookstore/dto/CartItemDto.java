package mate.academy.bookstore.dto;

public record CartItemDto(
        Long id,
        Long bookId,
        String bookTitle,
        int quantity) {
}
