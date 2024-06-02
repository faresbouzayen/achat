package tn.esprit.rh.achat.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.ProduitServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProduitServiceImplTest {

	@Mock
	private ProduitRepository produitRepository;

	@Mock
	private StockRepository stockRepository;

	@InjectMocks
	private ProduitServiceImpl produitService;

	@Test
	void testRetrieveAllProduits() {
		// Given
		Produit produit1 = new Produit();
		Produit produit2 = new Produit();
		List<Produit> produits = new ArrayList<>();
		produits.add(produit1);
		produits.add(produit2);
		when(produitRepository.findAll()).thenReturn(produits);

		// When
		List<Produit> retrievedProduits = produitService.retrieveAllProduits();

		// Then
		assertEquals(2, retrievedProduits.size());
	}

	@Test
	void testAddProduit() {
		// Given
		Produit produit = new Produit();

		// When
		produitService.addProduit(produit);

		// Then
		verify(produitRepository, times(1)).save(produit);
	}

	@Test
	void testDeleteProduit() {
		// Given
		Long produitId = 1L;

		// When
		produitService.deleteProduit(produitId);

		// Then
		verify(produitRepository, times(1)).deleteById(produitId);
	}

	@Test
	void testUpdateProduit() {
		// Given
		Produit produit = new Produit();

		// When
		produitService.updateProduit(produit);

		// Then
		verify(produitRepository, times(1)).save(produit);
	}

	@Test
	void testRetrieveProduit() {
		// Given
		Long produitId = 1L;
		Produit produit = new Produit();
		when(produitRepository.findById(produitId)).thenReturn(java.util.Optional.ofNullable(produit));

		// When
		Produit retrievedProduit = produitService.retrieveProduit(produitId);

		// Then
		assertNotNull(retrievedProduit);
	}

	@Test
	void testAssignProduitToStock() {
		// Given
		Long idProduit = 1L;
		Long idStock = 1L;
		Produit produit = new Produit();
		Stock stock = new Stock();
		when(produitRepository.findById(idProduit)).thenReturn(java.util.Optional.ofNullable(produit));
		when(stockRepository.findById(idStock)).thenReturn(java.util.Optional.ofNullable(stock));

		// When
		produitService.assignProduitToStock(idProduit, idStock);

		// Then
		assertEquals(stock, produit.getStock());
	}
}
