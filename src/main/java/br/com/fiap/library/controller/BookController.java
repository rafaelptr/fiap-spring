package br.com.fiap.library.controller;

import br.com.fiap.library.dto.AutorDTO;
import br.com.fiap.library.dto.BookDTO;
import br.com.fiap.library.dto.CreateBookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.ws.Response;
import java.awt.print.Book;
import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController("books")
@RequestMapping("books")
public class BookController {
    private static List<BookDTO> books = new ArrayList<>();

    public BookController(){
        SecureRandom rnd = new SecureRandom();
        int rndMax= rnd.nextInt(11);
        for(int i = 1 ; i < rndMax ; i++) {
            int pg= rnd.nextInt(600);
            int year= rnd.nextInt(40);
            Integer isbn= rnd.nextInt(Integer.MAX_VALUE);
            books.add(new BookDTO(i,
                            "Senhor dos Anéis "+i,
                            pg,
                            isbn.toString(),
                            ZonedDateTime.now().minusYears(year),
                            new AutorDTO(i, "Autor "+i)
                    )
            );
        }
    }

    @GetMapping
    public List<BookDTO> getAll(@RequestParam(value = "title",required = false) String titulo){
        return books.stream()
                .filter(bookDTO -> titulo == null || bookDTO.getTitulo().contains(titulo))
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public BookDTO findById(@PathVariable Integer id){
        return books.stream()
                .filter(bookDTO -> bookDTO.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public BookDTO create(@RequestBody CreateBookDTO createBookDTO){
        Integer id = books.stream()
                .max(Comparator.comparing(BookDTO::getId))
                .map(dto -> (dto.getId()+1))
                .orElse(1);
        String titulo = createBookDTO.getTitulo();
        BookDTO bookDTO = new BookDTO(id,
                titulo,
                createBookDTO.getQtdPaginas(),
                createBookDTO.getISBN(),
                createBookDTO.getDataLancamento(),
                new AutorDTO(id, "Autor de " +titulo)
                );
        books.add(bookDTO);
        return bookDTO;
    }


    @PutMapping("{id}")
    public BookDTO create(@PathVariable Integer id, @RequestBody CreateBookDTO createBookDTO){
        BookDTO bookDTO = findById(id);
        int index = books.indexOf(bookDTO);

        bookDTO.setISBN(createBookDTO.getISBN());
        bookDTO.setTitulo(createBookDTO.getTitulo());
        bookDTO.setDataLancamento(createBookDTO.getDataLancamento());
        bookDTO.setQtdPaginas(createBookDTO.getQtdPaginas());

        books.set(index,bookDTO);

        return bookDTO;
    }
}
