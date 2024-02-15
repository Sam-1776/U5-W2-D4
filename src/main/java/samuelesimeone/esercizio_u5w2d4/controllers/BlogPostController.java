package samuelesimeone.esercizio_u5w2d4.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import samuelesimeone.esercizio_u5w2d4.entities.BlogPost;
import samuelesimeone.esercizio_u5w2d4.dto.BlogPost.BlogPostPayload;
import samuelesimeone.esercizio_u5w2d4.exceptions.BadRequestException;
import samuelesimeone.esercizio_u5w2d4.services.BlogPostService;

import java.util.UUID;

@RestController
@RequestMapping("/blogpost")
public class BlogPostController {

    @Autowired
    BlogPostService blogPostService;

    @GetMapping
    public Page<BlogPost> getAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String order){
        return this.blogPostService.getAll(page,size,order);
    }

    @GetMapping("/{id}")
    public BlogPost getBlogPostById(@PathVariable UUID id){
        return this.blogPostService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost save(@RequestBody @Validated BlogPostPayload post, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.blogPostService.save(post);
    }

    @PutMapping("/{id}")
    public BlogPost update(@PathVariable UUID id, @RequestBody BlogPostPayload post){
        return this.blogPostService.update(id,post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        this.blogPostService.deletePost(id);
    }


}
