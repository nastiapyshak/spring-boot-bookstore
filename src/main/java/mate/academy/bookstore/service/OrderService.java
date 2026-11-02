package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.OrderItemDto;
import mate.academy.bookstore.dto.OrderResponseDto;
import mate.academy.bookstore.dto.PlaceAnOrderDto;
import mate.academy.bookstore.dto.UpdateOrderStatusDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto placeAnOrder(PlaceAnOrderDto orderDto, Long userId);

    Page<OrderResponseDto> getOrdersHistory(Pageable pageable, Long userId);

    Page<OrderItemDto> getOrderItemsByOrderId(
            Pageable pageable, Long orderId, Long userId);

    OrderItemDto getOrderItemById(
            Long orderId, Long orderItemId, Long userId);

    void updateOrderStatus(UpdateOrderStatusDto orderDto, Long orderId);
}
