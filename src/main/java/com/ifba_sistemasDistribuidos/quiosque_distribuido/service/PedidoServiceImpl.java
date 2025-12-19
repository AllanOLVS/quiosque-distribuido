package com.ifba_sistemasDistribuidos.quiosque_distribuido.service;

import com.ifba.quiosque.proto.PedidoRequest;
import com.ifba.quiosque.proto.PedidoResponse;
import com.ifba.quiosque.proto.PedidoServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import java.util.UUID;

@GrpcService
public class PedidoServiceImpl extends PedidoServiceGrpc.PedidoServiceImplBase {

    @Override
    public void enviarPedido(PedidoRequest request, StreamObserver<PedidoResponse> responseObserver) {

        // Log para ver no console do servidor o que chegou
        System.out.println("=> [SERVIDOR] Recebido pedido do " + request.getIdQuiosque());
        System.out.println("    Prato: " + request.getNomePrato() + " | Valor: R$ " + String.format("%.2f", request.getValor()));

        String status = "APROVADO";
        String mensagem = "Seu pedido está sendo preparado. Aguarde a senha.";

        // Validação pra valores iguais ou abaixo de zero
        if (request.getValor() <= 0) {
            status = "RECUSADO";
            mensagem = "Valor incorreto no sistema.";
        }

        // Construindo a resposta, utilizando o padrão Builder
        // UUID esta sendo gerado de forma automatica, a fim de simular pedidos reais
        PedidoResponse response = PedidoResponse.newBuilder()
                .setIdPedido(UUID.randomUUID().toString())
                .setStatus(status)
                .setMensagem(mensagem)
                .build();

        // Devolução da resposta
        responseObserver.onNext(response);
        // Avisa que finalizou, fecha a conexão dessa chamada
        responseObserver.onCompleted();
    }
}
