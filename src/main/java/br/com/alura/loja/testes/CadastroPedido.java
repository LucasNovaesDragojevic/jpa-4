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
				
		var produto = produtoDao.buscarPorId(1L);
		
		var cliente = clienteDao.buscarPorId(1L);
		
		var pedido = new Pedido(cliente);
		
		var itemPedido = new ItemPedido(10, pedido, produto);
		
		pedido.adicionarItem(itemPedido);
		
		pedidoDao.cadastrar(pedido);
		
		em.getTransaction().commit();
		em.close();
	}

	private static void popularBaseDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares );
		var cliente = new Cliente("Lucas", "1234");
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		var clienteDao = new ClienteDao(em);
		
		em.getTransaction().begin();
		
		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(celular);
		clienteDao.cadastrar(cliente);
		
		em.getTransaction().commit();
		em.close();
	}
}
