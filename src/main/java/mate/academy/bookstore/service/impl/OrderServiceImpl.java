package mate.academy.bookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.OrderItemDto;
import mate.academy.bookstore.dto.OrderResponseDto;
import mate.academy.bookstore.dto.PlaceAnOrderDto;
import mate.academy.bookstore.dto.UpdateOrderStatusDto;
import mate.academy.bookstore.mapper.OrderItemMapper;
import mate.academy.bookstore.mapper.OrderMapper;
import mate.academy.bookstore.model.Order;
import mate.academy.bookstore.model.OrderItem;
import mate.academy.bookstore.model.ShoppingCart;
import mate.academy.bookstore.model.Status;
import mate.academy.bookstore.repository.OrderItemRepository;
import mate.academy.bookstore.repository.OrderRepository;
import mate.academy.bookstore.repository.ShoppingCartRepository;
import mate.academy.bookstore.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public OrderResponseDto placeAnOrder(PlaceAnOrderDto orderDto, Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find Shopping cart for user wit ID: " + userId));

        if (shoppingCart.getCartItems().isEmpty()) {
            throw new IllegalStateException(
                    "Shopping cart is empty for user with ID: " + userId);
        }

        Order order = createOrder(shoppingCart, orderDto);
        shoppingCart.getCartItems().clear();
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrdersHistory(Pageable pageable, Long userId) {
        return orderRepository.findAllByUserId(userId, pageable)
                .map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItemDto> getOrderItemsByOrderId(
            Pageable pageable, Long orderId, Long userId) {
        return orderItemRepository
                .findAllByOrder_IdAndOrder_User_Id(orderId, userId, pageable)
                .map(orderItemMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItemDto getOrderItemById(
            Long orderId, Long orderItemId, Long userId) {
        OrderItem item = orderItemRepository
                .findByIdAndOrder_IdAndOrder_User_Id(
                        orderItemId, orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find order item"));
        return orderItemMapper.toDto(item);
    }

    @Override
    public void updateOrderStatus(UpdateOrderStatusDto orderDto, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can not find order with ID: " + orderId));
        order.setStatus(orderDto.status());
    }

    private Order createOrder(
            ShoppingCart shoppingCart, PlaceAnOrderDto orderDto) {
        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Status.PENDING);
        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(orderItemMapper::toModel)
                .collect(Collectors.toSet());
        orderItems.forEach(item -> item.setOrder(order));
        order.setOrderItems(orderItems);
        order.setShippingAddress(orderDto.shippingAddress());
        order.setTotal(countTotal(orderItems));
        return orderRepository.save(order);
    }

    private BigDecimal countTotal(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(
                        BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
