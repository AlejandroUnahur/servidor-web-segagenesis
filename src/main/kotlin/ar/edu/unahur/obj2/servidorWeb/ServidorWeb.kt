package ar.edu.unahur.obj2.servidorWeb

import java.time.LocalDateTime

// Para no tener los códigos "tirados por ahí", usamos un enum que le da el nombre que corresponde a cada código
// La idea de las clases enumeradas es usar directamente sus objetos: CodigoHTTP.OK, CodigoHTTP.NOT_IMPLEMENTED, etc
enum class CodigoHttp(val codigo: Int) {
  OK(200),
  NOT_IMPLEMENTED(501),
  NOT_FOUND(404),
}

class Pedido(val ip: String, val url: String, val fechaHora: LocalDateTime)

class Respuesta(val codigo: CodigoHttp, val body: String, val tiempo: Int, val pedido: Pedido)

class ServidorWeb() {
  var modulos = mutableListOf<Modulo>()

  fun agregarModulo(modulo: Modulo) = this.modulos.add(modulo)

  fun realizarPedido(pedido: Pedido): Respuesta {
    if (!pedido.url.startsWith("http:")) {
      return Respuesta(CodigoHttp.NOT_IMPLEMENTED, "", 10, pedido)
    }
    if (this.algunModuloSoporta(pedido.url)) {
      val modulo = this.modulos.find { it.puedeSoportar(pedido.url) }!!
      return Respuesta(CodigoHttp.OK,modulo.body,modulo.tiempo,pedido)
    }
    else {
      return Respuesta(CodigoHttp.NOT_FOUND,"",10,pedido)
    }
  }

  fun algunModuloSoporta(url: String) = this.modulos.any {it.puedeSoportar(url) }
}

class Modulo(val extensiones: List<String>, val body: String, val tiempo: Int ) {
  fun puedeSoportar(url:String) = this.extensiones.any { url.endsWith(it) }

}

