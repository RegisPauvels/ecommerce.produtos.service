package br.edu.utfpr.td.tsi.ecommerce.produtos.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import br.edu.utfpr.td.tsi.ecommerce.produtos.service.modelo.PedidoProcessamentoPagamento;

@Component
public class Listener {
	
	@Autowired
	private Publisher publisher;
	
	private Logger logger = LoggerFactory.getLogger(Listener.class);

    private final Gson gson = new Gson();

    @RabbitListener(queues = "fila.estoque", ackMode = "MANUAL")
    public void listen(Message message, Channel channel) throws IOException {

        String in = new String(message.getBody());
        PedidoProcessamentoPagamento pedido = gson.fromJson(in, PedidoProcessamentoPagamento.class);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {

            simularAtualizacaoEstoque(pedido);
            
            publisher.compraConcluida(pedido);

            channel.basicAck(deliveryTag, false);

        } catch (Exception e) {
        	logger.error("[PRODUTOS SERVICE] Erro ao processar mensagem da fila.estoque");
        	logger.error("[PRODUTOS SERVICE] Motivo: " + e.getMessage());
            
            channel.basicNack(deliveryTag, false, false);
        }
    }

    private void simularAtualizacaoEstoque(PedidoProcessamentoPagamento pedido) {

        if (pedido == null) {
            throw new RuntimeException("Pedido recebido está nulo.");
        }

        if (pedido.getProduto() == null) {
            throw new RuntimeException("Produto do pedido está nulo.");
        }

        if (pedido.getQuantidade() <= 0) {
            throw new RuntimeException("Quantidade inválida para baixa de estoque.");
        }
        
        logger.info("==================================================");
        logger.info("[PRODUTOS SERVICE] Mensagem recebida na fila.estoque");
        logger.info("[PRODUTOS SERVICE] Simulando baixa de estoque...");
        logger.info("[PRODUTOS SERVICE] Produto: " + pedido.getProduto().getNome());
        logger.info("[PRODUTOS SERVICE] Quantidade comprada: " + pedido.getQuantidade());
        logger.info("[PRODUTOS SERVICE] Estoque atualizado com sucesso de forma simulada.");
        logger.info("==================================================");

    }
}