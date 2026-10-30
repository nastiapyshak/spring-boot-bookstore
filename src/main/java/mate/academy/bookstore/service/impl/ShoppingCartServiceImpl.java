package mate.academy.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.AddBookToCartDto;
import mate.academy.bookstore.dto.ShoppingCartDto;
import mate.academy.bookstore.dto.UpdateBookQuantityInCartDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.ShoppingCartMapper;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.model.CartItem;
import mate.academy.bookstore.model.ShoppingCart;
import mate.academy.bookstore.model.User;
import mate.academy.bookstore.repository.BookRepository;
import mate.academy.bookstore.repository.CartItemRepository;
import mate.academy.bookstore.repository.ShoppingCartRepository;
import mate.academy.bookstore.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartDto findByUserId(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Can not find a Shopping cart for User with id: " + id));
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto addBookToCart(AddBookToCartDto addBookToCartDto, Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can not find a Shopping cart for User with id: " + userId));
        Book book = bookRepository.findById(addBookToCartDto.bookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find Book with id: " + addBookToCartDto.bookId()));
        CartItem cartItem = shoppingCart.getCartItems().stream()
                .filter(item -> item.getBook().getId()
                        .equals(addBookToCartDto.bookId()))
                .findFirst()
                .orElse(null);
        if (cartItem != null) {
            cartItem.setQuantity(
                    cartItem.getQuantity() + addBookToCartDto.quantity()
            );
        } else {
            CartItem newItem = new CartItem();
            newItem.setBook(book);
            newItem.setQuantity(addBookToCartDto.quantity());
            newItem.setShoppingCart(shoppingCart);
            shoppingCart.getCartItems().add(newItem);
        }
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto updateBookQuantityInCart(
            UpdateBookQuantityInCartDto requestDto,
            Long cartItemId,
            Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find ShoppingCart for user with id: " + userId));
        CartItem cartItem = cartItemRepository
                .findCartItemByIdAndShoppingCart(cartItemId, shoppingCart)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find CartItem with id: " + cartItemId));
        cartItem.setQuantity(requestDto.quantity());
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public void deleteCartItemById(Long cartItemId,
                                   Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find ShoppingCart for user with id: " + userId));
        CartItem cartItem = cartItemRepository
                .findCartItemByIdAndShoppingCart(cartItemId, shoppingCart)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find CartItem with id: " + cartItemId));
        shoppingCart.getCartItems().remove(cartItem);
    }

    @Override
    public void createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }
}
