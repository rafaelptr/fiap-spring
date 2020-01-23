package br.com.fiap.library.controller;

import br.com.fiap.library.dto.AutorDTO;
import br.com.fiap.library.dto.BookDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {
    
    @GetMapping
    public List<BookDTO> getAll(@RequestParam(value = "title",required = false) String titulo){
        List<BookDTO> books = new ArrayList<>();
        SecureRandom rnd = new SecureRandom();
        int rndMax= rnd.nextInt(300);
        for(int i = 0 ; i < rndMax ; i++) {
            books.add(new BookDTO(i,
                            "Senhor dos Anéis "+i,
                            100+i,
                            "6712574124124"+i,
                            ZonedDateTime.now().minusYears(40),
                            new AutorDTO(i, "Autor "+i)
                    )
            );
        }
        return books.stream()
                .filter(bookDTO -> bookDTO.getTitulo().contains(titulo))
                .collect(Collectors.toList());
    }
}
