# 📋 Documentação Técnica e Evidências de Testes Automatizados (BDD, API & Contrato)

**Projeto:** EcoSmart Waste Management — Sistema Inteligente de Gestão de Resíduos e Reciclagem para Cidades Inteligentes  
**Aluno:** Pedro Henrique De Souza Santiago  
**Disciplina / Atividade:** Desafio de Testes Automatizados com Foco em Critérios ESG  
**Stack Tecnológica:** Java 17 · Spring Boot 3.2.5 · Cucumber 7.16.1 (Gherkin PT) · RestAssured 5.4.0 · JSON Schema Validator · JUnit 5 · Docker · GitHub Actions

---

## 1. Contextualização e Aderência aos Pilares ESG

O **EcoSmart Waste Management** é uma plataforma backend para Cidades Inteligentes voltada à gestão eficiente de resíduos sólidos urbanos, reciclagem gamificada e governança ambiental. A automação de testes foi projetada para blindar as regras de negócio de todos os **três pilares ESG**:

1. **Pilar Ambiental (`pontos_coleta` — `/api/pontos-coleta`):**
   - Monitoramento em tempo real de lixeiras inteligentes equipadas com sensores IoT (`EcoSense X200` / `EcoSense Lite`), validando telemetria de bateria, temperatura e nível de ocupação (`0%` a `100%`) para evitar transbordamentos e poluição urbana.
2. **Pilar Governança e Operacional (`coletas_agendadas` — `/api/coletas-agendadas`):**
   - Rastreabilidade de ordens de serviço e rotas de caminhões coletores com estimativa de distância e emissão de $\text{CO}_2$ (`emissao_co2_estimada_kg`), impedindo agendamentos em pontos desativados e exigindo justificativa formal em cancelamentos.
3. **Pilar Social (`usuarios_cidadaos` — `/api/usuarios-cidadaos`):**
   - Engajamento cidadão com gamificação inclusiva (`bronze`, `prata`, `ouro`, `platina`), convertendo cada quilo de resíduo reciclável descartado em pontos de recompensa auditáveis.
4. **Pilar Social e Governança (`cooperativas_parceiras` — `/api/cooperativas-parceiras`):**
   - Integração de cooperativas de catadores conveniadas ao poder público, assegurando transparência nos contratos municipais, capacidade de processamento mensal e validação de CNPJ único.
5. **Pilar Governança e Compliance Ambiental (`auditoria_ambiental` — `/api/auditorias-ambientais`):**
   - Auditoria das metas ambientais do município com cálculo automático do percentual de atingimento e **regra estrita de compliance**: nenhuma auditoria classificada como `nao_conforme` pode ser registrada sem um `plano_acao_corretiva` estruturado.

---

## 2. Escrita dos Cenários de Teste BDD em Gherkin (23 Cenários)

Embora o requisito mínimo da atividade seja de **3 cenários BDD**, foram implementados **23 cenários BDD em português (`# language: pt`)**, cobrindo **100% das APIs do projeto**, incluindo caminhos felizes (sucesso) e cenários negativos (tratamento de falhas e regras de negócio ESG).

### 2.1 Funcionalidade 1: Gestão de Pontos de Coleta Inteligentes (`01_pontos_coleta.feature`)

```gherkin
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
```

### 2.2 Funcionalidade 2: Gestão de Coletas Agendadas (`02_coletas_agendadas.feature`)

```gherkin
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
```

### 2.3 Funcionalidade 3: Engajamento de Cidadãos (`03_usuarios_cidadaos.feature`)

```gherkin
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
```

### 2.4 Funcionalidade 4: Gestão de Cooperativas Parceiras (`04_cooperativas_parceiras.feature`)

```gherkin
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
```

### 2.5 Funcionalidade 5: Auditoria e Conformidade Ambiental (`05_auditoria_ambiental.feature`)

```gherkin
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
```

---

## 3. Evidência da Execução Bem-Sucedida dos Testes (`mvn test`)

Abaixo está o log real de execução via **Maven Surefire + JUnit 5 Platform Suite + Cucumber + RestAssured**, comprovando a aprovação de **100% dos 23 cenários (143 passos executados com zero falhas)**:

```text
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running br.com.ecosmart.CucumberTestRunner

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.5)

INFO --- b.c.e.steps.CucumberSpringConfiguration : Starting CucumberSpringConfiguration using Java 17.0.20.1
INFO --- o.s.b.w.embedded.tomcat.TomcatWebServer : Tomcat started on port 65190 (http) with context path ''
INFO --- b.c.e.steps.CucumberSpringConfiguration : Started CucumberSpringConfiguration in 2.486 seconds

23 Scenarios (23 passed)
143 Steps (143 passed)
0m6,711s

[INFO] Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 7.011 s -- in br.com.ecosmart.CucumberTestRunner
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 23, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  11.448 s
[INFO] ------------------------------------------------------------------------
```

### Relatório Visual Gerado Automaticamente
Ao executar `mvn test`, o relatório HTML interativo do Cucumber é gerado em:
- `target/cucumber-reports/cucumber.html`
- `target/cucumber-reports/cucumber.json`
