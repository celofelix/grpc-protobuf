package br.com.zup.edu

import io.grpc.ManagedChannelBuilder

fun main() {

    val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val clientGrpc = FuncionarioServiceGrpc.newBlockingStub(channel)

    val request = FuncionarioRequest.newBuilder()
        .setNome("Marcelo Felix")
        .setIdade(28)
        .setCpf("000.000.00-00")
        .setSalario(1000.0)
        .setCargo(Cargo.GERENTE)
        .setAtivo(true)
        .addEndereco(
            Endereco.newBuilder()
                .setLogradouro("Rua Teste")
                .setCep("00000-000")
                .setComplemento("Casa")
                .build())
        .build()

    clientGrpc.cadastra(request).let {
        println(it.nome)
        println(it.criadoEm)
    }
}