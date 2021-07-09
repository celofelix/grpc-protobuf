package br.com.zup.edu

import java.io.FileInputStream
import java.io.FileOutputStream

fun main() {

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

    println(request)

    request.writeTo(FileOutputStream("request-funcionario.bin"))

    val request2 = FuncionarioRequest.newBuilder()
        .mergeFrom(FileInputStream("request-funcionario.bin"))

    request2.setCargo(Cargo.DEV)
        .build()

    println(request2)
}