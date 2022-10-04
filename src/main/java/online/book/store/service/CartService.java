package online.book.store.service;

import online.book.store.dto.CartItemDto;
import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;

public interface CartService {
    void addBookToCart(Book book, Cart cart);
    void removeBookFromCart(Book book, Cart cart);
    CartItemDto contains(Cart cart, Book book);
    void updateCart(Cart cart);
    void updateCartItem(CartItem cartItem, Cart cart);
    void updateCartItem(CartItem cartItem, int quantity, Cart cart);
    CartItem getCartItemById(int id);
    CartItem getCartItemByBook(Cart cart, Book book);
    void deleteCartItem(CartItem cartItem);

}
