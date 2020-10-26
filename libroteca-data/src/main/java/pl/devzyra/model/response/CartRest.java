package pl.devzyra.model.response;

import lombok.Data;
import pl.devzyra.model.entities.BookEntity;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartRest {

    private String cartId;
    private UserRest userRest;
    private List<BookEntity> books = new ArrayList<>();
}
