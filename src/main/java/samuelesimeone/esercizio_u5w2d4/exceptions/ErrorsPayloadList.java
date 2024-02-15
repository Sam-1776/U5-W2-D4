package samuelesimeone.esercizio_u5w2d4.exceptions;

import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ErrorsPayloadList extends ErrorsPayload{
    private List<String> errorList;

    public ErrorsPayloadList(String message, LocalDateTime time, List<String> errorList) {
        super(message, time);
        this.errorList = errorList;
    }
}
