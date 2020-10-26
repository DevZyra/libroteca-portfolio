package pl.devzyra.services;

import pl.devzyra.model.entities.BookEntity;
import pl.devzyra.model.entities.RestCartEntity;
import pl.devzyra.model.entities.UserEntity;

public interface RestCartService {

    RestCartEntity getCartByUser(UserEntity user);

    BookEntity addToCart(UserEntity user, BookEntity book);

    RestCartEntity save(RestCartEntity cart);


}
