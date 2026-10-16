package br.edu.atitus.currencyapi.services;

import br.edu.atitus.currencyapi.config.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class CotacaoBcbCache {

    private final BcbGateway gateway;

    public CotacaoBcbCache(BcbGateway gateway) { this.gateway = gateway; }

    @Cacheable(cacheNames = CacheConfig.COTACOES, key = "#moeda.toUpperCase() + ':' + #data")
    public BigDecimal buscar(String moeda, LocalDate data) {
        return gateway.buscar(moeda, data);
    }
}

