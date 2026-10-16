package br.edu.atitus.currencyapi.services;

import br.edu.atitus.currencyapi.client.BcbClient;
import br.edu.atitus.currencyapi.client.BcbCotacao;
import br.edu.atitus.currencyapi.client.BcbResposta;
import br.edu.atitus.currencyapi.exception.CotacaoNaoEncontradaException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class BcbGateway {

    private static final Logger log = LoggerFactory.getLogger(BcbGateway.class);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    private final BcbClient client;

    public BcbGateway(BcbClient client) { this.client = client; }

    @Retry(name = "bcb")
    @CircuitBreaker(name = "bcb")
    public BigDecimal buscar(String moeda, LocalDate data) {
        log.info("Chamando BCB: moeda={} data={}", moeda, data);
        BcbResposta resp = client.buscarCotacao("'" + moeda + "'", "'" + data.format(FMT) + "'", "json");

        if (resp == null || resp.value() == null || resp.value().isEmpty()) {
            throw new CotacaoNaoEncontradaException("BCB sem cotação para " + moeda + " em " + data);
        }
        List<BcbCotacao> lista = resp.value();
        BigDecimal venda = lista.get(lista.size() - 1).cotacaoVenda(); // último boletim do dia (fechamento)
        if (venda == null) {
            throw new CotacaoNaoEncontradaException("BCB retornou cotação nula para " + moeda);
        }
        return venda;
    }
}

