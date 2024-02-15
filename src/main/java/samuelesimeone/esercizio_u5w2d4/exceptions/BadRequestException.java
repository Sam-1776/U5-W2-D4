package samuelesimeone.esercizio_u5w2d4.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException{
    List<ObjectError> errorList;
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(List<ObjectError> errorList){
        super("Errori nel payload");
        this.errorList = errorList;
    }
}
