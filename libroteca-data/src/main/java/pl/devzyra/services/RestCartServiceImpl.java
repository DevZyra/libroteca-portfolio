package pl.devzyra.services;

import org.springframework.stereotype.Service;
import pl.devzyra.model.entities.BookEntity;
import pl.devzyra.model.entities.RestCartEntity;
import pl.devzyra.model.entities.UserEntity;
import pl.devzyra.repositories.RestCartRepository;

@Service
public class RestCartServiceImpl implements RestCartService {

    private final RestCartRepository restCartRepository;

    public RestCartServiceImpl(RestCartRepository restCartRepository) {
        this.restCartRepository = restCartRepository;
    }


    @Override
    public RestCartEntity getCartByUser(UserEntity user) {

        return restCartRepository.findByUser(user);
    }

    @Override
    public BookEntity addToCart(UserEntity user, BookEntity book) {

        RestCartEntity cart = user.getCart();

        cart.getBooks().add(book);

        restCartRepository.save(cart);

        return book;
    }
}
