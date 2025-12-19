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

        System.out.println("=> [SERVIDOR] Recebido pedido do " + request.getIdQuiosque());
        System.out.println("    Prato: " + request.getNomePrato() + " | Valor: R$ " + String.format("%.2f", request.getValor()));

        String status = "APROVADO";
        String mensagem = "Seu pedido está sendo preparado. Aguarde a senha.";

        if (request.getValor() <= 0) {
            status = "RECUSADO";
            mensagem = "Valor incorreto no sistema.";
        }

        PedidoResponse response = PedidoResponse.newBuilder()
                .setIdPedido(UUID.randomUUID().toString())
                .setStatus(status)
                .setMensagem(mensagem)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
