package samuelesimeone.esercizio_u5w2d4.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import samuelesimeone.esercizio_u5w2d4.dao.AutoriDAO;
import samuelesimeone.esercizio_u5w2d4.dto.Autori.AutoriDTO;
import samuelesimeone.esercizio_u5w2d4.entities.Autore;
import samuelesimeone.esercizio_u5w2d4.exceptions.BadRequestException;
import samuelesimeone.esercizio_u5w2d4.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.UUID;


@Service
public class AutoriService {


    @Autowired
    AutoriDAO autoriDAO;

    public Page<Autore> getAll(int pageN, int pageS, String OrderBy){
        Pageable pageable = PageRequest.of(pageN, pageS, Sort.by(OrderBy));
        return autoriDAO.findAll(pageable);
    }


    public Autore save(AutoriDTO autore){
        autoriDAO.findByEmail(autore.email()).ifPresent(element -> {
            throw new BadRequestException("L'email inserita è già in uso, riprovare");
        });
        String avatar = "https://ui-avatars.com/api/?name=" + autore.nome() + "+" + autore.cognome();
        String Day = autore.dataDiNascita().toString();
        LocalDate bDay = LocalDate.parse(Day);
        Autore newAutore = new Autore(autore.nome(), autore.cognome(), autore.email(), bDay, avatar);
        return autoriDAO.save(newAutore);
    }

    public Autore findById(UUID id){
        return autoriDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Autore update(UUID id, Autore autoreUp){
        autoreUp.setAvatar("https://ui-avatars.com/api/?name=" + autoreUp.getNome() + "+" + autoreUp.getCognome());
        String Day = autoreUp.getDataDiNascita().toString();
        LocalDate bDay = LocalDate.parse(Day);
        autoreUp.setDataDiNascita(bDay);
        Autore found = this.findById(id);
        found.setNome(autoreUp.getNome());
        found.setCognome(autoreUp.getCognome());
        found.setEmail(autoreUp.getEmail());
        found.setDataDiNascita(autoreUp.getDataDiNascita());
        found.setAvatar(autoreUp.getAvatar());
        return autoriDAO.save(found);
    }

    public void delete(UUID id){
       Autore found = this.findById(id);
       autoriDAO.delete(found);
    }
}
