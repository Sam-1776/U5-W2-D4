package samuelesimeone.esercizio_u5w2d4.dto.Autori;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AutoriDTO(

        @NotEmpty(message = "Il nome è obbligatoria")
        @Size(min = 3, max = 10, message = "Il titolo deve essere compreso tra 3 e 10")
        String nome,
        @NotEmpty(message = "Il cognome è obbligatoria")
        @Size(min = 3, max = 10, message = "Il titolo deve essere compreso tra 3 e 10")
        String cognome,
        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "Email inerita non valida!")
        String email,
        @NotEmpty(message = "Data di nascita obbligatoria")
        String dataDiNascita){
}
