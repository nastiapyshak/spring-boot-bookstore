package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.AddBookToCartDto;
import mate.academy.bookstore.dto.ShoppingCartDto;
import mate.academy.bookstore.dto.UpdateBookQuantityInCartDto;
import mate.academy.bookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartDto findByUserId(Long id);

    ShoppingCartDto addBookToCart(AddBookToCartDto addBookToCartDto, Long userId);

    ShoppingCartDto updateBookQuantityInCart(
                UpdateBookQuantityInCartDto requestDto,
                Long cartItemId,
                Long userId);

    void deleteCartItemById(Long cartItemId, Long userId);

    public void createShoppingCart(User user);
}
