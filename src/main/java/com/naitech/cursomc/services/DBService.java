package com.naitech.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naitech.cursomc.domain.Address;
import com.naitech.cursomc.domain.BankSlipPayment;
import com.naitech.cursomc.domain.CardPayment;
import com.naitech.cursomc.domain.Category;
import com.naitech.cursomc.domain.City;
import com.naitech.cursomc.domain.Client;
import com.naitech.cursomc.domain.ClientOrder;
import com.naitech.cursomc.domain.ItemOrder;
import com.naitech.cursomc.domain.Payment;
import com.naitech.cursomc.domain.Product;
import com.naitech.cursomc.domain.State;
import com.naitech.cursomc.domain.enums.ClientType;
import com.naitech.cursomc.domain.enums.PaymentStatus;
import com.naitech.cursomc.repositories.AddressRepository;
import com.naitech.cursomc.repositories.CategoryRepository;
import com.naitech.cursomc.repositories.CityRepository;
import com.naitech.cursomc.repositories.ClientOrderRepository;
import com.naitech.cursomc.repositories.ClientRepository;
import com.naitech.cursomc.repositories.ItemOrderRepository;
import com.naitech.cursomc.repositories.PaymentRepository;
import com.naitech.cursomc.repositories.ProductRepository;
import com.naitech.cursomc.repositories.StateRepository;

@Service
public class DBService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private ClientOrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ItemOrderRepository itemOrderRepository;

	public void instantiateDatabase() throws ParseException {
		Category category1 = new Category(null, "Informatica");
		Category category2 = new Category(null, "Escritorio");
		Category category3 = new Category(null, "Cama mesa e banho");
		Category category4 = new Category(null, "Eletronicos");
		Category category5 = new Category(null, "Jardinagem");
		Category category6 = new Category(null, "Decoração");
		Category category7 = new Category(null, "Perfumaria");

		Product product1 = new Product(null, "Computador", 2000.00);
		Product product2 = new Product(null, "Impressora", 800.00);
		Product product3 = new Product(null, "Mouse", 80.00);
		Product product4 = new Product(null, "Mesa de escritorio", 300.00);
		Product product5 = new Product(null, "Toalha", 50.00);
		Product product6 = new Product(null, "Colcha", 200.00);
		Product product7 = new Product(null, "TV true color", 1200.00);
		Product product8 = new Product(null, "Roçadeira", 800.00);
		Product product9 = new Product(null, "Abajour", 100.00);
		Product product10 = new Product(null, "Pendente", 180.00);
		Product product11 = new Product(null, "Shampoo", 90.00);

		category1.getProducts().addAll(Arrays.asList(product1, product2, product3));
		category2.getProducts().addAll(Arrays.asList(product2, product4));
		category3.getProducts().addAll(Arrays.asList(product5, product6));
		category4.getProducts().addAll(Arrays.asList(product1, product2, product3, product7));
		category5.getProducts().addAll(Arrays.asList(product8));
		category6.getProducts().addAll(Arrays.asList(product9, product10));
		category7.getProducts().addAll(Arrays.asList(product11));

		product1.getCategories().addAll(Arrays.asList(category1, category4));
		product2.getCategories().addAll(Arrays.asList(category1, category2, category4));
		product3.getCategories().addAll(Arrays.asList(category1, category4));
		product4.getCategories().addAll(Arrays.asList(category2));
		product5.getCategories().addAll(Arrays.asList(category3));
		product6.getCategories().addAll(Arrays.asList(category3));
		product7.getCategories().addAll(Arrays.asList(category4));
		product8.getCategories().addAll(Arrays.asList(category5));
		product9.getCategories().addAll(Arrays.asList(category6));
		product10.getCategories().addAll(Arrays.asList(category6));
		product11.getCategories().addAll(Arrays.asList(category7));

		categoryRepository
				.saveAll(Arrays.asList(category1, category2, category3, category4, category5, category6, category7));
		productRepository.saveAll(Arrays.asList(product1, product2, product3, product4, product5, product6, product7,
				product8, product9, product10, product11));

		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "Sao Paulo");

		City city1 = new City(null, "Uberlandia", state1);
		City city2 = new City(null, "Sao Paulo", state2);
		City city3 = new City(null, "Campinas", state2);

		state1.getCities().addAll(Arrays.asList(city1));
		state2.getCities().addAll(Arrays.asList(city2, city3));

		stateRepository.saveAll(Arrays.asList(state1, state2));
		cityRepository.saveAll(Arrays.asList(city1, city2, city3));

		Client client1 = new Client(null, "Maria Silva", "maria@gmail.com", "00000000000", ClientType.PESSOAFISICA);

		client1.getPhones().addAll(Arrays.asList("000000000", "111111111"));

		Address address1 = new Address(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", client1, city1);
		Address address2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38220777", client1, city2);

		client1.getAddresses().addAll(Arrays.asList(address1, address2));

		clientRepository.saveAll(Arrays.asList(client1));
		addressRepository.saveAll(Arrays.asList(address1, address2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		ClientOrder order1 = new ClientOrder(null, sdf.parse("30/09/2024 10:32"), client1, address1);
		ClientOrder order2 = new ClientOrder(null, sdf.parse("30/12/2024 10:32"), client1, address2);

		Payment payment1 = new CardPayment(null, PaymentStatus.QUITADO, order1, 6);
		order1.setPayment(payment1);

		Payment payment2 = new BankSlipPayment(null, PaymentStatus.PENDENTE, order2, sdf.parse("30/12/2024 10:32"),
				null);
		order2.setPayment(payment2);

		client1.getOrders().addAll(Arrays.asList(order1, order2));

		orderRepository.saveAll(Arrays.asList(order1, order2));
		paymentRepository.saveAll(Arrays.asList(payment1, payment2));

		ItemOrder itemOrder1 = new ItemOrder(order1, product1, 0.00, 1, 2000.00);
		ItemOrder itemOrder2 = new ItemOrder(order1, product3, 0.00, 2, 80.00);
		ItemOrder itemOrder3 = new ItemOrder(order2, product2, 100.00, 1, 800.00);

		order1.getItems().addAll(Arrays.asList(itemOrder1, itemOrder2));
		order2.getItems().addAll(Arrays.asList(itemOrder3));

		product1.getItems().addAll(Arrays.asList(itemOrder1));
		product2.getItems().addAll(Arrays.asList(itemOrder3));
		product3.getItems().addAll(Arrays.asList(itemOrder2));

		itemOrderRepository.saveAll(Arrays.asList(itemOrder1, itemOrder2, itemOrder3));
	}
}
