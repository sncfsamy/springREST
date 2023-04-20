package com.spring.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    
    @Autowired
    BookRepository repository;
    @GetMapping("/books")
    public List<Book> index(){
        return repository.findAll();
    }

    @GetMapping("/books/{id}")
    public Book show(@PathVariable Long id){
        return repository.findById(id).get();
    }
    
    @PostMapping("/books")
    public String inscription(@RequestParam String title,
                              @RequestParam String author,
                              @RequestParam String description,
                              @RequestParam(required = false) Long id) {
        if (id != null) {
            Optional<Book> optionalBook = repository.findById(id);
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                book.setAuthor(author);
                book.setTitle(title);
                book.setDescription(description);
                repository.save(book);
                return "redirect:/books/" + book.id;
            }
        } else {
            Book book = new Book(title,author,description);
            repository.save(book);
            return "redirect:/books/" + book.id;
        }
        return "redirect:/books";
    }

    @DeleteMapping("/books/{id}")
    public String delete(@PathVariable Long id) {
        Optional<Book> optionalBook = repository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            repository.delete(book);
        }
        return "redirect:/books";
    }
}
