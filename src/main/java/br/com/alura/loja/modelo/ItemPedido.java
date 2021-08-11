package br.com.alura.loja.modelo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "itens_pedido")
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	@Column(name = "preco_unitario")
	private BigDecimal precoUnitario;
	private Integer quantidade;
	
	@ManyToOne
	private Pedido pedido;
	
	@ManyToOne
	private Produto produto;
	
	public ItemPedido() {}

	public ItemPedido(Integer quantidade, Pedido pedido, Produto produto) {
		this.quantidade = quantidade;
		this.pedido = pedido;
		this.precoUnitario = produto.getPreco();
		this.produto = produto;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
}
