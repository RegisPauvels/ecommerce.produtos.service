package br.edu.utfpr.td.tsi.ecommerce.produtos.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.edu.utfpr.td.tsi.ecommerce.produtos.service.modelo.PedidoProcessamentoPagamento;

@Component
public class Publisher {

    private final RabbitTemplate rabbitTemplate;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Publisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void compraConcluida(PedidoProcessamentoPagamento pedido) {
        rabbitTemplate.convertAndSend("fila.compra.concluida", gson.toJson(pedido));
    }
}