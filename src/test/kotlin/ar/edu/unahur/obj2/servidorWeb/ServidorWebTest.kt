package ar.edu.unahur.obj2.servidorWeb

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class ServidorWebTest : DescribeSpec({
  describe("Un servidor web") {
    val servidorWeb = ServidorWeb()

    describe("Realiza un pedido") {
      it("Cuando el protocolo no es el correcto devuelve 501(NOT_IMPLEMENTED)") {
        val pedido = Pedido("192.168.1.13","https://pepito.com.ar/documentos/doc1.html", LocalDateTime.now())
        val respuesta = servidorWeb.realizarPedido(pedido)
        respuesta.codigo.shouldBe(CodigoHttp.NOT_IMPLEMENTED)
      }

      it("Cuando algun modulo si puede trabajar con el pedido devuelve 200(OK)") {
        val pedido = Pedido("192.168.1.13","http://pepito.com.ar/documentos/doc1.html", LocalDateTime.now())
        val modulo = Modulo(listOf("html"),"",10)
        servidorWeb.agregarModulo(modulo)
        val respuesta = servidorWeb.realizarPedido(pedido)
        respuesta.codigo.shouldBe(CodigoHttp.OK)
      }

      it("Cuando algun modulo no puede trabajar con el pedido devuelve 404(NOT_FOUND)") {
        val pedido = Pedido("192.168.1.13","http://pepito.com.ar/documentos/doc1.html", LocalDateTime.now())
        val respuesta = servidorWeb.realizarPedido(pedido)
        respuesta.codigo.shouldBe(CodigoHttp.NOT_FOUND)

      }
    }
  }
})
