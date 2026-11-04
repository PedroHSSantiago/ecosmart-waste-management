# language: pt
Funcionalidade: Auditoria e Conformidade Ambiental (Pilar Governança ESG)
  Como auditor de governança e compliance ambiental
  Eu quero consultar e registrar indicadores de metas ambientais e planos de ação corretivos
  Para assegurar transparência pública, rastreabilidade e conformidade regulatória do município

  Contexto:
    Dado que a base de dados do EcoSmart está inicializada com os registros padrões ESG

  Cenário: Consultar auditorias ambientais não conformes e verificar plano de ação corretivo
    Quando eu envio uma requisição GET para "/api/auditorias-ambientais?statusConformidade=nao_conforme"
    Então o status code da resposta deve ser 200
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/auditorias-ambientais-lista-schema.json"
    E a lista retornada deve conter exatamente 2 registros
    E o campo JSON "[0].codigo_auditoria" deve ser igual a "AUD-002"
    E o campo JSON "[0].plano_acao_corretiva.descricao" deve conter "Ampliar campanhas educativas"

  Cenário: Registrar uma nova auditoria ambiental em conformidade com a meta municipal
    Dado que eu possuo os dados de uma nova auditoria "AUD-005" com valor medido 1500.0 para a meta 1200.0
    Quando eu envio uma requisição POST para "/api/auditorias-ambientais" com o payload configurado
    Então o status code da resposta deve ser 201
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/auditoria-ambiental-schema.json"
    E o campo JSON "codigo_auditoria" deve ser igual a "AUD-005"
    E o campo JSON "status_conformidade" deve ser igual a "conforme"
    E o campo decimal JSON "percentual_atingimento" deve ser igual a 125.0

  Cenário: Falha de compliance ao registrar auditoria não conforme sem plano de ação corretiva
    Dado que eu possuo uma auditoria não conforme "AUD-006" abaixo da meta sem plano de ação corretiva
    Quando eu envio uma requisição POST para "/api/auditorias-ambientais" com o payload configurado
    Então o status code da resposta deve ser 400
    E o corpo da resposta deve respeitar o contrato JSON Schema "schemas/erro-api-schema.json"
    E o campo JSON "mensagem" deve conter "exigem obrigatoriamente um plano_acao_corretiva"
