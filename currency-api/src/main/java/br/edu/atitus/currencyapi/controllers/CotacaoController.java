package br.edu.atitus.currencyapi.controllers;

import br.edu.atitus.currencyapi.services.CotacaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/currency")
public class CotacaoController {

    public record CotacaoResponse(String source, String target, BigDecimal valor) {}

    private final CotacaoService service;

    public CotacaoController(CotacaoService service) { this.service = service; }

    @GetMapping("/{source}/{target}")
    public CotacaoResponse buscar(@PathVariable String source, @PathVariable String target) {
        return new CotacaoResponse(source.toUpperCase(), target.toUpperCase(),
                service.obterCotacao(source, target));
    }
}

