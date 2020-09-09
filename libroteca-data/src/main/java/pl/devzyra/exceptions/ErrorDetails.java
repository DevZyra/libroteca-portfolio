package pl.devzyra.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorDetails {

    private Date timestamp;
    private String message;
    private String causePath;

    public ErrorDetails(Date timestamp, String message, String causePath) {
        this.timestamp = timestamp;
        this.message = message;
        this.causePath = causePath;
    }
}
