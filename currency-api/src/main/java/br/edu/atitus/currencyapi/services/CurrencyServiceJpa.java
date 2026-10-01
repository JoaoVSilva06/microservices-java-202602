package br.edu.atitus.currencyapi.services;

import br.edu.atitus.currencyapi.dtos.CurrencyRequest;
import br.edu.atitus.currencyapi.dtos.CurrencyResponse;
import br.edu.atitus.currencyapi.entities.CurrencyEntity;
import br.edu.atitus.currencyapi.repositories.CurrencyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceJpa implements CurrencyService {

	private final CurrencyRepository repository;

	public CurrencyServiceJpa(CurrencyRepository repository) {
		this.repository = repository;
	}

	@Value("${server.port:8080}")
	private String serverPort;

	@Value("${app.promotion.message:Nenhuma Promoção Ativa}")
	private String promotionMessage;


	@Override
	public CurrencyResponse findById(Long id, String targetCurrency) throws Exception {
		var product = repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
		String environment = "Product API running in port " + serverPort;
		return CurrencyResponse.fromEntity(
				product,
				environment,
				promotionMessage,
				targetCurrency,
				0
		);
	}

	@Override
	public CurrencyResponse findBySourceCurrencyAndTargetCurrency(String sourceCurrency, String targetCurrency) throws Exception {
		return null;
	}

	@Override
	public Page<CurrencyResponse> findAll(Pageable pageable, String targetCurrency) throws Exception {
		var products = repository.findAll(pageable);
		String environment = "Product API running in port " + serverPort;

		return products.map(
				entity -> CurrencyResponse.fromEntity(
						entity,
						environment,
						promotionMessage,
						targetCurrency,
						0
				)
		);
	}

	@Override
	public CurrencyEntity save(CurrencyRequest request) throws Exception {
		return null;
	}
}