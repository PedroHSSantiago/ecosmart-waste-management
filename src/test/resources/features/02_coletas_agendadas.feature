# language: pt
Funcionalidade: Gestão de Coletas Agendadas e Rotas Sustentáveis (Pilar Governança e Operacional ESG)
  Como coordenador operacional de logística reversa
  Eu quero agendar, monitorar e atualizar rotas de coleta controlando as emissões de CO2
  Para garantir rastreabilidade operacional e redução da pegada de carbono da frota

  Contexto:
    Dado que a base de dados do EcoSmart está inicializada com os registros padrões ESG

  Cenário: Listar rotas de coleta agendadas e validar contrato JSON Schema
    Quando eu envio uma requisição GET para "/api/coletas-agendadas?status=agendado"
    Então o status code da resposta deve ser 200
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/coletas-agendadas-lista-schema.json"
    E o campo JSON "[0].id_rota" deve ser igual a "ROTA-001"
    E o campo JSON "[0].status" deve ser igual a "agendado"

  Cenário: Agendar uma nova rota de coleta seletiva com estimativa de emissão de CO2
    Dado que eu possuo os dados válidos para agendar a nova rota "ROTA-011" atendendo os pontos "PC-001" e "PC-002"
    Quando eu envio uma requisição POST para "/api/coletas-agendadas" com o payload configurado
    Então o status code da resposta deve ser 201
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/coleta-agendada-schema.json"
    E o campo JSON "id_rota" deve ser igual a "ROTA-011"
    E o campo JSON "caminhao.placa" deve ser igual a "BRS-9Z99"

  Cenário: Concluir uma rota de coleta em andamento com sucesso
    Dado que eu preparo a atualização de status da rota para "concluido"
    Quando eu envio uma requisição PATCH para "/api/coletas-agendadas/ROTA-002/status" com o payload configurado
    Então o status code da resposta deve ser 200
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/coleta-agendada-schema.json"
    E o campo JSON "status" deve ser igual a "concluido"

  Cenário: Falha ao cancelar uma rota de coleta sem informar o motivo de cancelamento obrigatório
    Dado que eu preparo a atualização de status da rota para "cancelado" sem motivo de cancelamento
    Quando eu envio uma requisição PATCH para "/api/coletas-agendadas/ROTA-001/status" com o payload configurado
    Então o status code da resposta deve ser 400
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/erro-api-schema.json"
    E o campo JSON "mensagem" deve conter "O motivo de cancelamento é obrigatório"

  Cenário: Falha ao agendar rota de coleta incluindo um ponto de coleta inativo
    Dado que eu possuo uma rota inválida "ROTA-999" vinculada ao ponto inativo "PC-010"
    Quando eu envio uma requisição POST para "/api/coletas-agendadas" com o payload configurado
    Então o status code da resposta deve ser 400
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/erro-api-schema.json"
    E o campo JSON "mensagem" deve conter "Não é permitido agendar coleta para ponto inativo"
