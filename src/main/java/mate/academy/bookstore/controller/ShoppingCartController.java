package mate.academy.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.AddBookToCartDto;
import mate.academy.bookstore.dto.ShoppingCartDto;
import mate.academy.bookstore.dto.UpdateBookQuantityInCartDto;
import mate.academy.bookstore.model.User;
import mate.academy.bookstore.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(
            summary = "Add a new book to the shopping cart",
            description = "Creates a new cart item and returns the shopping cart"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartDto addBookToShoppingCart(@RequestBody @Valid AddBookToCartDto cartDto,
                                                 @AuthenticationPrincipal User user) {
        return shoppingCartService.addBookToCart(cartDto, user.getId());
    }

    @Operation(
            summary = "Get the authenticated user's shopping cart",
            description = "Retrieves the shopping cart of the currently authenticated user"
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ShoppingCartDto getTheShoppingCart(
            @AuthenticationPrincipal User user) {
        return shoppingCartService.findByUserId(user.getId());
    }

    @Operation(
            summary = "Update book quantity in the shopping cart",
            description = "Updates the quantity of a cart item by its ID"
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/items/{cartItemId}")
    public ShoppingCartDto updateBookQuantity(@RequestBody @Valid
                                                  UpdateBookQuantityInCartDto cartDto,
                                              @PathVariable Long cartItemId,
                                              @AuthenticationPrincipal User user) {
        return shoppingCartService.updateBookQuantityInCart(cartDto, cartItemId, user.getId());
    }

    @Operation(
            summary = "Remove an item from the shopping cart",
            description = "Removes a cart item from the authenticated user's shopping cart"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItem(@PathVariable Long cartItemId, @AuthenticationPrincipal User user) {
        shoppingCartService.deleteCartItemById(cartItemId, user.getId());
    }
}
