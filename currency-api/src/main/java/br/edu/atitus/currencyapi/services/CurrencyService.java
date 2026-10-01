package br.edu.atitus.currencyapi.services;

import br.edu.atitus.currencyapi.dtos.CurrencyRequest;
import br.edu.atitus.currencyapi.dtos.CurrencyResponse;
import br.edu.atitus.currencyapi.entities.CurrencyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CurrencyService {

	CurrencyResponse findById(Long id, String targetCurrency) throws Exception;

	CurrencyResponse findBySourceCurrencyAndTargetCurrency(
			String sourceCurrency,
			String targetCurrency
	) throws Exception;

	Page<CurrencyResponse> findAll(Pageable pageable, String targetCurrency) throws Exception;

	CurrencyEntity save(CurrencyRequest request) throws Exception;
}
