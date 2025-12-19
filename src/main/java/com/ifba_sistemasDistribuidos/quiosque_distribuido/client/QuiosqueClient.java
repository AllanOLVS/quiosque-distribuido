package com.ifba_sistemasDistribuidos.quiosque_distribuido.client;

import com.ifba.quiosque.proto.PedidoRequest;
import com.ifba.quiosque.proto.PedidoResponse;
import com.ifba.quiosque.proto.PedidoServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class QuiosqueClient implements CommandLineRunner {

    @GrpcClient("quiosque-server")
    private PedidoServiceGrpc.PedidoServiceBlockingStub pedidoStub;

    @Override
    public void run(String... args) throws Exception {

        System.out.println();
        System.out.println("-> Inicio teste / SIMULAÇÃO DOS QUIOSQUES (CLIENTE)");
        System.out.println();

        System.out.println("-> Teste de APROVAÇÃO Proposital:");
        System.out.println();
        enviarPedidoSimulado("Quiosque-01 (Principal)", "X-Tudo", 25.00);
        enviarPedidoSimulado("Quiosque-02 (Drive-Thru)", "Refrigerante Lata", 6.50);
        System.out.println();

        System.out.println("-> Teste de REPROVAÇÃO Proposital:");
        System.out.println();
        enviarPedidoSimulado("Quiosque-DEBUG", "Erro Proposital", -100.00);

        System.out.println();
        System.out.println("-> FIM DA SIMULAÇÃO");
    }

    private void enviarPedidoSimulado(String nomeQuiosque, String prato, double valor) {
        try {
            PedidoRequest request = PedidoRequest.newBuilder()
                    .setIdQuiosque(nomeQuiosque)
                    .setNomePrato(prato)
                    .setValor(valor)
                    .build();

            PedidoResponse response = pedidoStub.enviarPedido(request);

            System.out.println(nomeQuiosque + " recebeu resposta:");
            System.out.println("   -> ID Pedido: " + response.getIdPedido());
            System.out.println("   -> Status:    " + response.getStatus());
            System.out.println("   -> Mensagem:  " + response.getMensagem());
            System.out.println("-----------------------------------");

        } catch (Exception e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
        }
    }
}
