package samuelesimeone.esercizio_u5w2d4.entities;

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
    private String categoria;
    private String titolo;
    private String cover;
    private String contenuto;
    private double tempoDiLettura;
    private UUID autoreId;
}
