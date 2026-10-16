package br.edu.atitus.currencyapi.services;

import br.edu.atitus.currencyapi.entities.CurrencyEntity;
import br.edu.atitus.currencyapi.repositories.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service

public class CotacaoService {

    private static final Logger log = LoggerFactory.getLogger(CotacaoService.class);

    private final CotacaoBcbCache bcbCache;
    private final CurrencyRepository repository;
    private final LocalDate dataCotacao;

    public CotacaoService(CotacaoBcbCache bcbCache,
                      CurrencyRepository repository,
                          @Value("${bcb.data-cotacao}") LocalDate dataCotacao) {
        this.bcbCache = bcbCache;
        this.repository = repository;
        this.dataCotacao = dataCotacao;
    }

    public BigDecimal obterCotacao(String source, String target) {
        try {
            return bcbCache.buscar(source, dataCotacao);
        } catch (Exception e) {
            log.warn("Fallback para banco local ({} -> {}): {}", source, target, e.toString());
            return repository.findBySourceCurrencyAndTargetCurrency(source, target)
                    .map(c -> BigDecimal.valueOf(c.getConversionRate()))
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Cotação não encontrada para " + source + " -> " + target));
        }
    }
}

