package br.edu.atitus.currencyapi.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface BcbClient {

    @GetExchange("/CotacaoMoedaDia(moeda=@moeda,dataCotacao=@dataCotacao)")
    BcbResposta buscarCotacao(@RequestParam("@moeda") String moeda,
                              @RequestParam("@dataCotacao") String dataCotacao,
                              @RequestParam("$format") String format);
}

