package br.com.fiap.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookDTO {
    private String titulo;
    private Integer qtdPaginas;
    private String ISBN;
    private ZonedDateTime dataLancamento;
}
