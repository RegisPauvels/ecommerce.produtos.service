package br.edu.utfpr.td.tsi.ecommerce.produtos.service.servico;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;

import com.google.gson.Gson;

import br.edu.utfpr.td.tsi.ecommerce.produtos.service.modelo.Produto;

@Service
public class ProdutosService {
	
	@Value("${caminho.arquivo.produtos}")
	private String caminhoArquivo;

	
	 public List<Produto> importarProdutosDeJson() {
	        try (Reader reader = new FileReader(caminhoArquivo)) {

	            Gson gson = new Gson();

	            Type tipoListaProdutos = new TypeToken<List<Produto>>() {}.getType();

	            return gson.fromJson(reader, tipoListaProdutos);

	        } catch (Exception e) {
	            throw new RuntimeException("Erro ao importar produtos do arquivo JSON", e);
	        }
	 }
}
