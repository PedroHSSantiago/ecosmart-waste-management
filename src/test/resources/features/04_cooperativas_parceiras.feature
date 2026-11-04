# language: pt
Funcionalidade: Gestão de Cooperativas Parceiras de Reciclagem (Pilar Social e Governança ESG)
  Como gestor de parcerias socioambientais do município
  Eu quero gerenciar as cooperativas conveniadas e suas capacidades de triagem
  Para promover geração de renda digna para catadores e transparência nos repasses públicos

  Contexto:
    Dado que a base de dados do EcoSmart está inicializada com os registros padrões ESG

  Cenário: Filtrar cooperativas que processam plástico com capacidade mensal acima de 10000 kg
    Quando eu envio uma requisição GET para "/api/cooperativas-parceiras?material=plastico&capacidadeMinima=10000"
    Então o status code da resposta deve ser 200
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/cooperativas-parceiras-lista-schema.json"
    E a lista retornada deve conter exatamente 2 registros
    E o campo JSON "[0].nome_cooperativa" deve ser igual a "Cooperativa Recicla Vida"

  Cenário: Cadastrar uma nova cooperativa parceira conveniada com sucesso
    Dado que eu possuo os dados válidos da nova cooperativa "COOP-005" com CNPJ "99.888.777/0001-55"
    Quando eu envio uma requisição POST para "/api/cooperativas-parceiras" com o payload configurado
    Então o status code da resposta deve ser 201
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/cooperativa-parceira-schema.json"
    E o campo JSON "codigo_cooperativa" deve ser igual a "COOP-005"
    E o campo JSON "cnpj" deve ser igual a "99.888.777/0001-55"

  Cenário: Falha ao cadastrar cooperativa com CNPJ já existente na base municipal
    Dado que eu possuo os dados válidos da nova cooperativa "COOP-009" com CNPJ "12.345.678/0001-01"
    Quando eu envio uma requisição POST para "/api/cooperativas-parceiras" com o payload configurado
    Então o status code da resposta deve ser 409
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/erro-api-schema.json"
    E o campo JSON "erro" deve ser igual a "Conflito de Dados"
