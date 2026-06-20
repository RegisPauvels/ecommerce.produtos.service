package br.edu.utfpr.td.tsi.ecommerce.produtos.service.controle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.td.tsi.ecommerce.produtos.service.modelo.Produto;
import br.edu.utfpr.td.tsi.ecommerce.produtos.service.servico.ProdutosService;

@RestController
public class ProdutosControle {

	
	@Autowired
	private ProdutosService produtosService;
	
	@GetMapping("/catalogo")
	public List<Produto> listarCatalogo() {
		return produtosService.importarProdutosDeJson();
	}
}
