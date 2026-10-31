package mate.academy.bookstore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import mate.academy.bookstore.model.Status;

public record OrderResponseDto(Long id,
                               Long userId,
                               Set<OrderItemDto> orderItems,
                               LocalDateTime orderDate,
                               BigDecimal total,
                               Status status
) {
}
