package com.astetech.omnifidelidade.models

object Config {
     //var clienteId: String = ""
     //var clienteNome: String = "Caro Cliente"
     val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJvbW5pc2lnZTk5OUB3ZWJic3lzLmNvbS5iciIsImp0aSI6ImIyMTQxMDg4LWQ5ZDUtNDBmYi05MTM4LTNmMzkzZWZjNGM3MyIsImlhdCI6MTYyMTk1OTExOCwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvZ2l2ZW5uYW1lIjoiT21uaXNpZ2UiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJBZG1pbmlzdHJhZG9yIiwidGVuYW50IjoiT21uaXNpZ2UiLCJsb2phIjoiOTk5IiwibmJmIjoxNjIxOTU5MTE3LCJleHAiOjE2MjIwMDIzMTcsImlzcyI6Imh0dHBzOi8vYXBpLm9tbmlzaWdlLmNvbS5iciIsImF1ZCI6Imh0dHBzOi8vYXBpLm9tbmlzaWdlLmNvbS5iciJ9.WTWaRwJd7ouOpo2OSrJ12afDQNi5ip0OcmU-TbMkegY"
     val tenant = "Aste"
     val lojaId = "lojas/150-C"

     fun obterImagem(imagem: String): String {

          var imagens = listOf<Imagem>(
               Imagem("New Balance", "https://aste.blob.core.windows.net/app-images/logo-nb-black.png"),
               Imagem("Jansport", "https://infracommerce.blob.core.windows.net/app-imagens/logo-jansport2.png"),
               Imagem("Diesel", "https://infracommerce.blob.core.windows.net/app-imagens/diesel-1.png"),
               Imagem("Kipling", "https://aste.blob.core.windows.net/app-images/kipling.png"),
               Imagem("Coach", "https://infracommerce.blob.core.windows.net/app-imagens/logo-allbags.png"),
               Imagem("Allbags", "https://infracommerce.blob.core.windows.net/app-imagens/logo-allbags.png")
          )

          return imagens.find { it.empresa == imagem }?.imagemUrl ?: "https://infracommerce.blob.core.windows.net/app-imagens/logo-jansport2.png"
     }
}

data class Imagem (
     var empresa: String,
     var imagemUrl: String
)



