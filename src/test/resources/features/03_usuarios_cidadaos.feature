# language: pt
Funcionalidade: Engajamento de Cidadãos e Programa de Recompensas (Pilar Social ESG)
  Como gestor de impacto social do programa EcoSmart
  Eu quero gerenciar o cadastro de cidadãos e contabilizar descartes recicláveis com gamificação
  Para incentivar a participação comunitária e promover educação ambiental inclusiva

  Contexto:
    Dado que a base de dados do EcoSmart está inicializada com os registros padrões ESG

  Cenário: Consultar cidadãos com pontuação de recompensas superior a 500 pontos
    Quando eu envio uma requisição GET para "/api/usuarios-cidadaos?pontuacaoMinima=500"
    Então o status code da resposta deve ser 200
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/usuarios-cidadaos-lista-schema.json"
    E a lista retornada deve conter exatamente 3 registros

  Cenário: Registrar novo descarte reciclável para cidadão e incrementar pontuação de recompensas
    Dado que o cidadão "bruno.carvalho@email.com" realiza um descarte de 4.0 kg de "reciclavel" no ponto "PC-002" valendo 80 pontos
    Quando eu envio uma requisição POST para "/api/usuarios-cidadaos/bruno.carvalho@email.com/descartes" com o payload configurado
    Então o status code da resposta deve ser 200
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/usuario-cidadao-schema.json"
    E o campo JSON "pontuacao_recompensas" deve ser igual a 420
    E o campo JSON "nivel_engajamento" deve ser igual a "bronze"

  Cenário: Cadastrar um novo cidadão no programa de reciclagem com sucesso
    Dado que eu possuo os dados válidos de um novo cidadão "Pedro Santiago" com e-mail "pedro.santiago@email.com"
    Quando eu envio uma requisição POST para "/api/usuarios-cidadaos" com o payload configurado
    Então o status code da resposta deve ser 201
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/usuario-cidadao-schema.json"
    E o campo JSON "nome" deve ser igual a "Pedro Santiago"
    E o campo JSON "nivel_engajamento" deve ser igual a "bronze"

  Cenário: Falha ao tentar registrar descarte em um ponto de coleta em manutenção
    Dado que o cidadão "ana.ramos@email.com" realiza um descarte de 2.5 kg de "reciclavel" no ponto "PC-006" valendo 50 pontos
    Quando eu envio uma requisição POST para "/api/usuarios-cidadaos/ana.ramos@email.com/descartes" com o payload configurado
    Então o status code da resposta deve ser 400
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/erro-api-schema.json"
    E o campo JSON "mensagem" deve conter "não está ativo para receber descartes"

  Cenário: Falha ao buscar cidadão com e-mail não cadastrado
    Quando eu envio uma requisição GET para "/api/usuarios-cidadaos/nao.existe@email.com"
    Então o status code da resposta deve ser 404
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/erro-api-schema.json"
    E o campo JSON "erro" deve ser igual a "Recurso Não Encontrado"
