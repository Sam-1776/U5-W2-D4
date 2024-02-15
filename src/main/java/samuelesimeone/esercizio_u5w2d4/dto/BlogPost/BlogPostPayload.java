package samuelesimeone.esercizio_u5w2d4.dto.BlogPost;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class BlogPostPayload {
    @NotEmpty(message = "La categoria è obbligatoria")
    private String categoria;
    @NotEmpty(message = "La titolo è obbligatoria")
    @Size(min = 3, max = 25, message = "Il titolo deve essere compreso tra 3 e 25")
    private String titolo;
    @NotEmpty(message = "La contenuto è obbligatoria")
    private String contenuto;
    private UUID autoreId;
}
