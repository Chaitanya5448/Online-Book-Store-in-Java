package online.book.store.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.dto.UserDto;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Table(name = "USERS")
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Column(name = "EMAIL")
    @Getter
    @Setter
    private String email;

    @Column(name = "USERNAME")
    @Getter
    @Setter
    private String username;

    @Column(name = "USER_PASSWORD")
    @Getter
    @Setter
    private String password;

    @Column(name = "IN_SESSION")
    @Getter
    @Setter
    private boolean inSession;

    @Column(name = "COUNT_RESET_PASSWORD")
    @Getter
    @Setter
    private int countResetPassword;

    @Column(name = "USER_ID")
    @Getter
    @Setter
    private String userID;


    @OneToOne(cascade = CascadeType.ALL)
    @Setter
    @Getter
    @JoinColumn(name = "checkout_id")
    public Checkout checkout;

    @Column(name = "IS_ADMIN")
    @Getter
    @Setter
    private boolean admin = false;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishList;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    @Setter
    private Cart cart;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @Getter
    @Setter
    private List<Order> orders;

    public void addOrder(Order order) {
        if (this.orders == null) this.orders = new LinkedList<>();
        this.orders.add(order);
        order.setUser(this);
    }

    public void removeOrder(Order order) {
        this.orders.remove(order);
        order.setUser(null);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @Getter
    @Setter
    private List<BookReview> bookReviews;


    public void addReview(BookReview bookReview) {
        if (this.bookReviews == null) this.bookReviews = new LinkedList<>();
        this.bookReviews.add(bookReview);
        bookReview.setUser(this);
    }

    public void removeReview(BookReview bookReview) {
        this.bookReviews.remove(bookReview);
        bookReview.setUser(null);
    }

    public User(String username, String email, String password, boolean inSession) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.inSession = inSession;

    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User user = (User) obj;
        return this.email.equals(user.email);
    }


    public Wishlist getWishList() {
        return wishList;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setWishList(Wishlist wishList) {
        this.wishList = wishList;
    }

    @Override
    public String toString() {
        return "User{" +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' + "";
    }


}
