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
import samuelesimeone.esercizio_u5w2d4.dao.BlogPostDAO;
import samuelesimeone.esercizio_u5w2d4.entities.Autore;
import samuelesimeone.esercizio_u5w2d4.entities.BlogPost;
import samuelesimeone.esercizio_u5w2d4.dto.BlogPost.BlogPostPayload;
import samuelesimeone.esercizio_u5w2d4.exceptions.NotFoundException;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Service
public class BlogPostService {
    Random rdm = new Random();
    @Autowired
    BlogPostDAO blogPostDAO;

    @Autowired
    AutoriService autoriService;

    @Autowired
    Cloudinary cloudinary;

    public Page<BlogPost> getAll(int pageN, int pageS, String OrderBY){
        Pageable pageable = PageRequest.of(pageN, pageS, Sort.by(OrderBY));
        return blogPostDAO.findAll(pageable);
    }

    public BlogPost save(BlogPostPayload post){
        Autore found = autoriService.findById(post.getAutoreId());
        String cover = "https://picsum.photos/200/300";
        double time = (rdm.nextDouble(1.0, 60.0));
        BlogPost newPost = new BlogPost(post.getCategoria(), post.getTitolo(), post.getContenuto(), cover, time, found);
        return blogPostDAO.save(newPost);
    }

    public BlogPost findById(UUID id){
       return blogPostDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public BlogPost update(UUID id, BlogPostPayload postUp){
        double time = (rdm.nextDouble(1.0, 60.0));
        BlogPost found = this.findById(id);
        found.setCategoria(postUp.getCategoria());
        found.setContenuto(postUp.getContenuto());
        found.setTitolo(postUp.getTitolo());
        found.setCover(found.getCover());
        found.setTempoDiLettura(time);
        found.setAutore(found.getAutore());
        return blogPostDAO.save(found);
    }

    public void deletePost(UUID id){
        BlogPost found = this.findById(id);
        blogPostDAO.delete(found);
    }

    public BlogPost uploadCover(UUID id, MultipartFile image) throws IOException{
        double time = (rdm.nextDouble(1.0, 60.0));
        BlogPost found = this.findById(id);
        String coverURL = (String) cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setCategoria(found.getCategoria());
        found.setContenuto(found.getContenuto());
        found.setTitolo(found.getTitolo());
        found.setCover(coverURL);
        found.setTempoDiLettura(time);
        found.setAutore(found.getAutore());
        return blogPostDAO.save(found);
    }

}
