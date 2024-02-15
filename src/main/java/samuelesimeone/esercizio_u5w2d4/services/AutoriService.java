package samuelesimeone.esercizio_u5w2d4.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import samuelesimeone.esercizio_u5w2d4.dao.AutoriDAO;
import samuelesimeone.esercizio_u5w2d4.dto.Autori.AutoriDTO;
import samuelesimeone.esercizio_u5w2d4.entities.Autore;
import samuelesimeone.esercizio_u5w2d4.exceptions.BadRequestException;
import samuelesimeone.esercizio_u5w2d4.exceptions.NotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;


@Service
public class AutoriService {


    @Autowired
    AutoriDAO autoriDAO;

    @Autowired
    Cloudinary cloudinary;

    public Page<Autore> getAll(int pageN, int pageS, String OrderBy){
        Pageable pageable = PageRequest.of(pageN, pageS, Sort.by(OrderBy));
        return autoriDAO.findAll(pageable);
    }


    public Autore save(AutoriDTO autore){
        autoriDAO.findByEmail(autore.email()).ifPresent(element -> {
            throw new BadRequestException("L'email inserita è già in uso, riprovare");
        });
        String avatar = "https://ui-avatars.com/api/?name=" + autore.nome() + "+" + autore.cognome();
        String Day = autore.dataDiNascita();
        LocalDate bDay = LocalDate.parse(Day);
        Autore newAutore = new Autore(autore.nome(), autore.cognome(), autore.email(), bDay, avatar);
        return autoriDAO.save(newAutore);
    }

    public Autore findById(UUID id){
        return autoriDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Autore update(UUID id, AutoriDTO autoreUp){
        String avatar = "https://ui-avatars.com/api/?name=" + autoreUp.nome() + "+" + autoreUp.cognome();
        String Day = autoreUp.dataDiNascita();
        LocalDate bDay = LocalDate.parse(Day);
        Autore found = this.findById(id);
        found.setNome(autoreUp.nome());
        found.setCognome(autoreUp.cognome());
        found.setEmail(autoreUp.email());
        found.setDataDiNascita(bDay);
        found.setAvatar(avatar);
        return autoriDAO.save(found);
    }

    public void delete(UUID id){
       Autore found = this.findById(id);
       autoriDAO.delete(found);
    }

    public Autore uploadAvatar(UUID id, MultipartFile image) throws IOException{
        Autore found = this.findById(id);
        String urlAvatar = (String) cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setNome(found.getNome());
        found.setCognome(found.getCognome());
        found.setEmail(found.getEmail());
        found.setDataDiNascita(found.getDataDiNascita());
        found.setAvatar(urlAvatar);
        return autoriDAO.save(found);
    }
}
