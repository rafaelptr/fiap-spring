package br.com.fiap.library.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class BookDTO {
    private Integer id;
    private String titulo;
    private Integer qtdPaginas;
    private String ISBN;
    private ZonedDateTime dataLancamento;
    private AutorDTO autor;
}
