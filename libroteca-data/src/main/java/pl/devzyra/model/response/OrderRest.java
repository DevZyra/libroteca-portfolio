package pl.devzyra.model.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class OrderRest {

    private String orderId;
    private Set<BookRest> books = new HashSet<>();
    private UserRest userRest;

}
