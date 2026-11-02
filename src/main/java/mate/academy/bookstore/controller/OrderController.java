package mate.academy.bookstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.OrderItemDto;
import mate.academy.bookstore.dto.OrderResponseDto;
import mate.academy.bookstore.dto.PlaceAnOrderDto;
import mate.academy.bookstore.dto.UpdateOrderStatusDto;
import mate.academy.bookstore.model.User;
import mate.academy.bookstore.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto placeAnOrder(
            @RequestBody @Valid PlaceAnOrderDto orderDto,
            @AuthenticationPrincipal User user) {
        return orderService.placeAnOrder(orderDto, user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public Page<OrderResponseDto> getUsersHistory(
            Pageable pageable,
            @AuthenticationPrincipal User user) {
        return orderService.getOrdersHistory(pageable, user.getId());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public void updateOrderStatus(
            @PathVariable @Valid Long id,
            @RequestBody UpdateOrderStatusDto orderDto) {
        orderService.updateOrderStatus(orderDto, id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items")
    public Page<OrderItemDto> getOrderItemsForOrder(
            Pageable pageable,
            @PathVariable Long orderId,
            @AuthenticationPrincipal User user) {
        return orderService.getOrderItemsByOrderId(
                pageable, orderId, user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{orderId}/items/{itemId}")
    public OrderItemDto getOrderItemByIdAndOrderId(
            @PathVariable Long orderId,
            @PathVariable Long itemId,
            @AuthenticationPrincipal User user) {
        return orderService.getOrderItemById(
                orderId, itemId, user.getId());
    }
}
