package tn.esprit.rh.achat.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.IStockService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StockServiceImplTest implements IStockService {

	@Autowired
	@Mock
	StockRepository stockRepository;


	@Override
	public List<Stock> retrieveAllStocks() {
		// récuperer la date à l'instant t1
		log.info("In method retrieveAllStocks");
		List<Stock> stocks = (List<Stock>) stockRepository.findAll();
		for (Stock stock : stocks) {
			log.info(" Stock : " + stock);
		}
		log.info("out of method retrieveAllStocks");
		// récuperer la date à l'instant t2
		// temps execution = t2 - t1
		return stocks;
	}

	@Override
	public Stock addStock(Stock s) {
		// récuperer la date à l'instant t1
		log.info("In method addStock");
		return stockRepository.save(s);

	}

	@Override
	public void deleteStock(Long id) {

	}

	@Test
	@Order(1)
	void deleteStock() {
		Long stockId = 1L; // Provide the value of stockId
		log.info("In method deleteStock");
		stockRepository.deleteById(stockId);
	}


	@Override
	public Stock updateStock(Stock s) {
		log.info("In method updateStock");
		return stockRepository.save(s);
	}

	@Override
	public Stock retrieveStock(Long stockId) {
		long start = System.currentTimeMillis();
		log.info("In method retrieveStock");
		Stock stock = stockRepository.findById(stockId).orElse(null);
		log.info("out of method retrieveStock");
		 long elapsedTime = System.currentTimeMillis() - start;
		log.info("Method execution time: " + elapsedTime + " milliseconds.");

		return stock;
	}

	@Override
	public String retrieveStatusStock() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
		String msgDate = sdf.format(now);
		String finalMessage = "";
		String newLine = System.getProperty("line.separator");
		List<Stock> stocksEnRouge = (List<Stock>) stockRepository.retrieveStatusStock();
		for (int i = 0; i < stocksEnRouge.size(); i++) {
			finalMessage = newLine + finalMessage + msgDate + newLine + ": le stock "
					+ stocksEnRouge.get(i).getLibelleStock() + " a une quantité de " + stocksEnRouge.get(i).getQte()
					+ " inférieur à la quantité minimale a ne pas dépasser de " + stocksEnRouge.get(i).getQteMin()
					+ newLine;

		}
		log.info(finalMessage);
		return finalMessage;
	}

}