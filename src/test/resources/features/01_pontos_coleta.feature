# language: pt
Funcionalidade: Gestão de Pontos de Coleta Inteligentes (Pilar Ambiental ESG)
  Como gestor ambiental do sistema EcoSmart Waste Management
  Eu quero gerenciar os pontos de coleta inteligentes e monitorar a telemetria de sensores IoT
  Para otimizar a coleta seletiva urbana e evitar o transbordamento de resíduos

  Contexto:
    Dado que a base de dados do EcoSmart está inicializada com os registros padrões ESG

  Cenário: Consultar ponto de coleta ativo equipado com sensor IoT e validar contrato JSON Schema
    Quando eu envio uma requisição GET para "/api/pontos-coleta/PC-001"
    Então o status code da resposta deve ser 200
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/ponto-coleta-schema.json"
    E o campo JSON "codigo_ponto" deve ser igual a "PC-001"
    E o campo JSON "bairro" deve ser igual a "Centro"
    E o campo JSON "sensor_iot.modelo" deve ser igual a "EcoSense X200"

  Cenário: Filtrar pontos de coleta ativos com ocupação crítica acima de 80 por cento
    Quando eu envio uma requisição GET para "/api/pontos-coleta?status=ativo&ocupacaoMinima=80"
    Então o status code da resposta deve ser 200
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/pontos-coleta-lista-schema.json"
    E a lista retornada deve conter exatamente 1 registro
    E o campo JSON "[0].codigo_ponto" deve ser igual a "PC-003"
    E o campo JSON "[0].nivel_ocupacao_percentual" deve ser igual a 92

  Cenário: Cadastrar um novo ponto de coleta inteligente com sucesso
    Dado que eu possuo os dados válidos de um novo ponto de coleta "PC-011" no bairro "Morada Nobre" com capacidade de 600 litros
    Quando eu envio uma requisição POST para "/api/pontos-coleta" com o payload configurado
    Então o status code da resposta deve ser 201
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/ponto-coleta-schema.json"
    E o campo JSON "codigo_ponto" deve ser igual a "PC-011"
    E o campo JSON "status" deve ser igual a "ativo"

  Cenário: Atualizar a leitura de telemetria e ocupação de uma lixeira inteligente
    Dado que eu desejo atualizar a telemetria do ponto "PC-001" para ocupação 95 e bateria 79
    Quando eu envio uma requisição PATCH para "/api/pontos-coleta/PC-001/telemetria" com o payload configurado
    Então o status code da resposta deve ser 200
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/ponto-coleta-schema.json"
    E o campo JSON "nivel_ocupacao_percentual" deve ser igual a 95
    E o campo JSON "sensor_iot.bateria_percentual" deve ser igual a 79

  Cenário: Remover um ponto de coleta desativado com sucesso
    Quando eu envio uma requisição DELETE para "/api/pontos-coleta/PC-010"
    Então o status code da resposta deve ser 204

  Cenário: Falha ao cadastrar ponto de coleta com nível de ocupação acima de 100 por cento
    Dado que eu possuo um payload inválido de ponto de coleta "PC-999" com nivel_ocupacao_percentual igual a 150
    Quando eu envio uma requisição POST para "/api/pontos-coleta" com o payload configurado
    Então o status code da resposta deve ser 400
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/erro-api-schema.json"
    E o campo JSON "erro" deve ser igual a "Erro de Validação de Dados"

  Cenário: Falha ao buscar ponto de coleta com código inexistente
    Quando eu envio uma requisição GET para "/api/pontos-coleta/PC-INEXISTENTE"
    Então o status code da resposta deve ser 404
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/erro-api-schema.json"
    E o campo JSON "erro" deve ser igual a "Recurso Não Encontrado"
