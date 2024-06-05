package tn.esprit.rh.achat.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;

import java.util.List;

import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {

	@InjectMocks
	private StockServiceImpl stockService;

	@Mock
	private StockRepository stockRepository;

	@Test
	@Order(1)
	void testDeleteStock() {
		Long stockId = 1L;
		stockService.deleteStock(stockId);
		verify(stockRepository, times(1)).deleteById(stockId);
	}


	@Test
	@Order(2)
	void testRetrieveAllStocks() {
		when(stockRepository.findAll()).thenReturn(List.of(new Stock(), new Stock()));
		List<Stock> stocks = stockService.retrieveAllStocks();
	}

	@Test
	@Order(3)
	void testAddStock() {
		Stock stockToAdd = new Stock();
		when(stockRepository.save(stockToAdd)).thenReturn(stockToAdd);
		Stock savedStock = stockService.addStock(stockToAdd);
	}
}
