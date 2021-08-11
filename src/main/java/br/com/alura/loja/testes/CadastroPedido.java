package br.com.alura.loja.testes;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroPedido {

	public static void main(String[] args) {
		popularBaseDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		
		em.getTransaction().begin();
		
		var produtoDao = new ProdutoDao(em);
		var pedidoDao = new PedidoDao(em);
		var clienteDao = new ClienteDao(em);
				
		var produto1 = produtoDao.buscarPorId(1L);
		var produto2 = produtoDao.buscarPorId(2L);
		var produto3 = produtoDao.buscarPorId(3L);
		
		var cliente = clienteDao.buscarPorId(1L);
		
		var pedido1 = new Pedido(cliente);
		var pedido2 = new Pedido(cliente);
		
		var itemPedido1 = new ItemPedido(10, pedido1, produto1);
		var itemPedido2 = new ItemPedido(10, pedido1, produto2);
		
		var itemPedido3 = new ItemPedido(10, pedido2, produto3);
		
		pedido1.adicionarItem(itemPedido1);
		pedido1.adicionarItem(itemPedido2);
		
		pedido2.adicionarItem(itemPedido3);
		
		pedidoDao.cadastrar(pedido1);
		pedidoDao.cadastrar(pedido2);
		
		em.getTransaction().commit();
		
		var totalVendido = pedidoDao.valorTotalVendido();
		
		System.out.println("Valor total vendido: " + totalVendido);
		
		var relatorio = pedidoDao.relatorioVendas();
		
		relatorio.forEach(System.out::println);
		
		em.close();
	}

	private static void popularBaseDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		var videogames = new Categoria("VIDEOGAME");
		var informatica = new Categoria("INFORMATICA");
		
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares );
		var videogame = new Produto("PS5", "Playstation 5", new BigDecimal("5000"), videogames);
		var notebook = new Produto("Lenovo Yoga", "Thinkpad", new BigDecimal("7000"), informatica);
				
		var cliente = new Cliente("Lucas", "1234");
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		var clienteDao = new ClienteDao(em);
		
		em.getTransaction().begin();
		
		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);
		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(notebook);
		
		clienteDao.cadastrar(cliente);
		
		em.getTransaction().commit();
		em.close();
	}
}
