package br.edu.atitus.currencyapi.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BcbCotacao(BigDecimal cotacaoCompra, BigDecimal cotacaoVenda, String dataHoraCotacao) {}

