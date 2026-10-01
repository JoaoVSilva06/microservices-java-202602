package br.edu.atitus.currencyapi.dtos;

import br.edu.atitus.currencyapi.entities.CurrencyEntity;

public record CurrencyResponse(
		String sourceCurrency,
		String targetCurrency,
		Double conversionRate,
		String environment) {

	public static CurrencyResponse fromEntity(CurrencyEntity product, String environment, String promotionMessage, String targetCurrency, int i) {
        return null;
    }
}
