package ar.edu.unahur.obj2.servidorWeb

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class ServidorWebTest : DescribeSpec({
  describe("Un servidor web") {
    val servidorWeb = ServidorWeb()

    describe("Realiza un pedido") {
      it("Cuando el protocolo es el correcto devuelve 200") {
        val pedido = Pedido("192.168.1.13","http://www.google.com/", LocalDateTime.now())
        val respuesta = servidorWeb.realizarPedido(pedido)
        respuesta.codigo.shouldBe(CodigoHttp.OK)
      }
      it("Cuando el protocolo no es el correcto devuelve 501") {
        val pedido = Pedido("192.168.1.13","https://www.google.com/", LocalDateTime.now())
        val respuesta = servidorWeb.realizarPedido(pedido)
        respuesta.codigo.shouldBe(CodigoHttp.NOT_IMPLEMENTED)
      }
    }

  }
})
