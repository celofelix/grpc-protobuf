package br.com.zup.edu

import com.google.protobuf.Timestamp
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import java.time.LocalDateTime
import java.time.ZoneId

fun main() {

    val server = ServerBuilder
        .forPort(50051)
        .addService(FunciarioEndPoint())
        .build()

    server
        .start()
        .awaitTermination()
}

class FunciarioEndPoint : FuncionarioServiceGrpc.FuncionarioServiceImplBase() {

    override fun cadastra(request: FuncionarioRequest?, responseObserver: StreamObserver<FuncionarioResponse>?) {

        val instant = LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()

        var nome: String? = request?.nome

        val hasField = request?.hasField(
            FuncionarioRequest
                .getDescriptor()
                .findFieldByName("nome"))

        if (!hasField!!) {
            nome = "???"
        }

        FuncionarioResponse
            .newBuilder()
            .setNome(nome)
            .setCriadoEm(Timestamp.newBuilder()
                .setSeconds(instant.epochSecond)
                .setNanos(instant.nano))
            .build()
            .let {
                responseObserver?.onNext(it)
                responseObserver?.onCompleted()
            }
    }
}