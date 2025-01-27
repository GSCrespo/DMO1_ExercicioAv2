package br.edu.ifsp.dmo1.pesquisaopiniao.data.model

import java.util.UUID

class Voto (val codigo : String = gerarStringAleatoria(), val opcao: String) {


    // como a função vai ser usada so pra gerar o codigo do voto, se torna + adequado
    // deixar a funçao como estatica (seguindo a logica do contrato q esta no DataHelper )
    private companion object {

        fun gerarStringAleatoria(tamanho : Int = 10) : String{
            // esse metodo utiliza UUID para gerar 32 caracteres hexadecimais separados por hifen
            // no caso aqui vou limitar a 10 conforme documento de requisitos

            val uuid = UUID.randomUUID().toString().replace("-","")
            // usando o replace para retirar os hifens entre os caracteres
            return  uuid.take(tamanho)
        }

    }
}