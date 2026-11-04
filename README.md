# 🌱 EcoSmart Waste Management — Testes Automatizados BDD, API & Contrato (ESG)

**Sistema Inteligente de Gestão de Resíduos e Reciclagem para Cidades Inteligentes**  
**Aluno:** Pedro Henrique De Souza Santiago  
**Tecnologias:** Java 17 · Spring Boot 3.2.5 · Cucumber BDD (Gherkin PT) · RestAssured · JSON Schema Validator · Docker · GitHub Actions CI/CD

---

## 🎯 1. Objetivo e Integração ESG no Projeto

Este projeto aplica técnicas modernas de **automação de testes de software (BDD, Testes de API REST e Testes de Contrato JSON Schema)** sobre o ecossistema **EcoSmart Waste Management**, desenvolvido originalmente com modelagem orientada a documentos (**NoSQL / MongoDB**) para gestão sustentável de resíduos sólidos urbanos.

A aplicação e sua suíte de testes automatizados estão estruturadas sobre os **três pilares ESG (Environmental, Social & Governance)**:

| Pilar ESG | Domínio / API | Responsabilidade e Regras de Negócio Validadas nos Testes |
| :--- | :--- | :--- |
| **🌿 Ambiental** *(Gestão de Resíduos & Eficiência)* | `/api/pontos-coleta` (`pontos_coleta`) | Monitoramento de lixeiras inteligentes equipadas com sensores IoT (bateria, temperatura, nível de ocupação de 0% a 100%) e pontos convencionais, prevenindo transbordamentos urbanos. |
| **⚖️ Governança & Operacional** *(Rastreabilidade)* | `/api/coletas-agendadas` (`coletas_agendadas`) | Agendamento e roteirização de caminhões coletores com cálculo de emissões estimadas de $\text{CO}_2$, bloqueio de rotas para pontos inativos e exigência de justificativa em cancelamentos. |
| **🤝 Social** *(Inclusão & Engajamento Cidadão)* | `/api/usuarios-cidadaos` (`usuarios_cidadaos`) | Programa de gamificação e recompensas para moradores que descartam recicláveis, com cálculo automático de pontos e evolução de nível (`bronze`, `prata`, `ouro`, `platina`). |
| **🤝 Social & Governança** *(Inclusão de Catadores)* | `/api/cooperativas-parceiras` (`cooperativas_parceiras`) | Gestão de cooperativas de reciclagem conveniadas ao município, validando capacidade mensal de triagem, certificações ISO e unicidade cadastral (CNPJ). |
| **🏛️ Governança & Compliance Ambiental** | `/api/auditorias-ambientais` (`auditoria_ambiental`) | Rastreabilidade de metas ambientais municipais ($\text{CO}_2$ evitado, taxa de reciclagem) e **bloqueio automático** de auditorias `nao_conforme` que não apresentem `plano_acao_corretiva`. |

---

## 🏗️ 2. Arquitetura do Projeto

```text
ecosmart-waste-management/
├── .github/workflows/
│   └── ci-tests.yml                         # Pipeline CI/CD (GitHub Actions)
├── docker/
│   └── mongo-init.js                        # Script NoSQL MongoDB com as 5 collections e dados ESG
├── src/
│   ├── main/
│   │   ├── java/br/com/ecosmart/
│   │   │   ├── EcoSmartApplication.java     # Classe principal Spring Boot
│   │   │   ├── controller/                  # 5 Controllers REST cobrindo todas as APIs ESG
│   │   │   ├── exception/                   # Tratamento global de erros (400, 404, 409)
│   │   │   ├── model/                       # Modelos de domínio e validações Bean Validation
│   │   │   └── service/                     # Regras de negócio ESG e armazenamento NoSQL em memória
│   │   └── resources/
│   │       └── application.yml              # Configurações da aplicação
│   └── test/
│       ├── java/br/com/ecosmart/
│       │   ├── CucumberTestRunner.java      # Runner JUnit 5 Platform Suite para o Cucumber
│       │   └── steps/
│       │       ├── CucumberSpringConfiguration.java
│       │       └── EcoSmartApiSteps.java    # Step Definitions (RestAssured + JSON Schema)
│       └── resources/
│           ├── features/                    # 5 arquivos .feature em Gherkin (20 cenários BDD)
│           │   ├── 01_pontos_coleta.feature
│           │   ├── 02_coletas_agendadas.feature
│           │   ├── 03_usuarios_cidadaos.feature
│           │   ├── 04_cooperativas_parceiras.feature
│           │   └── 05_auditoria_ambiental.feature
│           └── schemas/                     # 11 contratos JSON Schema (Draft-07)
│               ├── ponto-coleta-schema.json
│               ├── pontos-coleta-lista-schema.json
│               ├── coleta-agendada-schema.json
│               ├── coletas-agendadas-lista-schema.json
│               ├── usuario-cidadao-schema.json
│               ├── usuarios-cidadaos-lista-schema.json
│               ├── cooperativa-parceira-schema.json
│               ├── cooperativas-parceiras-lista-schema.json
│               ├── auditoria-ambiental-schema.json
│               ├── auditorias-ambientais-lista-schema.json
│               └── erro-api-schema.json
├── Dockerfile                               # Build multi-stage com execução de testes
├── docker-compose.yml                       # Orquestração API + MongoDB 7.0
└── pom.xml                                  # Dependências Maven e configuração de relatórios
```

---

## 📋 3. Cumprimento dos Requisitos Obrigatórios

### 3.1 Escrita de Cenários de Teste BDD (Gherkin)
O requisito exige **no mínimo 3 cenários**. O projeto entrega **30 cenários BDD completos (180 passos)** escritos em português (`# language: pt` — `Dado` / `Quando` / `Então` / `E`), distribuídos em 5 arquivos `.feature`, cobrindo **100% dos endpoints** tanto em **caminhos felizes (sucesso)** quanto em **cenários negativos (tratamento de falhas)**:

| Arquivo `.feature` | Cenário BDD | Tipo | Status Esperado | Contrato JSON Schema Validado |
| :--- | :--- | :---: | :---: | :--- |
| `01_pontos_coleta.feature` | 1. Consultar ponto de coleta ativo equipado com sensor IoT | Positivo | `200 OK` | `ponto-coleta-schema.json` |
| `01_pontos_coleta.feature` | 2. Filtrar pontos de coleta ativos com ocupação crítica > 80% | Positivo | `200 OK` | `pontos-coleta-lista-schema.json` |
| `01_pontos_coleta.feature` | 3. Cadastrar um novo ponto de coleta inteligente com sucesso | Positivo | `201 Created` | `ponto-coleta-schema.json` |
| `01_pontos_coleta.feature` | 4. Atualizar telemetria e ocupação de lixeira inteligente | Positivo | `200 OK` | `ponto-coleta-schema.json` |
| `01_pontos_coleta.feature` | 5. Remover um ponto de coleta desativado com sucesso | Positivo | `204 No Content` | — |
| `01_pontos_coleta.feature` | 6. Falha ao cadastrar ponto com ocupação acima de 100% | **Negativo** | `400 Bad Request` | `erro-api-schema.json` |
| `01_pontos_coleta.feature` | 7. Falha ao buscar ponto de coleta com código inexistente | **Negativo** | `404 Not Found` | `erro-api-schema.json` |
| `02_coletas_agendadas.feature` | 8. Listar rotas de coleta agendadas | Positivo | `200 OK` | `coletas-agendadas-lista-schema.json` |
| `02_coletas_agendadas.feature` | 9. Agendar nova rota seletiva com estimativa de $\text{CO}_2$ | Positivo | `201 Created` | `coleta-agendada-schema.json` |
| `02_coletas_agendadas.feature` | 10. Concluir uma rota de coleta em andamento | Positivo | `200 OK` | `coleta-agendada-schema.json` |
| `02_coletas_agendadas.feature` | 11. Falha ao cancelar rota sem informar motivo obrigatório | **Negativo** | `400 Bad Request` | `erro-api-schema.json` |
| `02_coletas_agendadas.feature` | 12. Falha ao agendar rota vinculada a ponto inativo | **Negativo** | `400 Bad Request` | `erro-api-schema.json` |
| `03_usuarios_cidadaos.feature` | 13. Consultar cidadãos com pontuação superior a 500 pontos | Positivo | `200 OK` | `usuarios-cidadaos-lista-schema.json` |
| `03_usuarios_cidadaos.feature` | 14. Registrar descarte reciclável e incrementar pontuação | Positivo | `200 OK` | `usuario-cidadao-schema.json` |
| `03_usuarios_cidadaos.feature` | 15. Cadastrar novo cidadão no programa de reciclagem | Positivo | `201 Created` | `usuario-cidadao-schema.json` |
| `03_usuarios_cidadaos.feature` | 16. Falha ao registrar descarte em ponto em manutenção | **Negativo** | `400 Bad Request` | `erro-api-schema.json` |
| `03_usuarios_cidadaos.feature` | 17. Falha ao buscar cidadão com e-mail não cadastrado | **Negativo** | `404 Not Found` | `erro-api-schema.json` |
| `04_cooperativas_parceiras.feature` | 18. Filtrar cooperativas que processam plástico (> 10.000 kg) | Positivo | `200 OK` | `cooperativas-parceiras-lista-schema.json` |
| `04_cooperativas_parceiras.feature` | 19. Cadastrar nova cooperativa parceira conveniada | Positivo | `201 Created` | `cooperativa-parceira-schema.json` |
| `04_cooperativas_parceiras.feature` | 20. Falha ao cadastrar cooperativa com CNPJ duplicado | **Negativo** | `409 Conflict` | `erro-api-schema.json` |
| `05_auditoria_ambiental.feature` | 21. Consultar auditorias não conformes e plano de ação | Positivo | `200 OK` | `auditorias-ambientais-lista-schema.json` |
| `05_auditoria_ambiental.feature` | 22. Registrar nova auditoria ambiental em conformidade | Positivo | `201 Created` | `auditoria-ambiental-schema.json` |
| `05_auditoria_ambiental.feature` | 23. Falha de compliance: auditoria não conforme sem plano de ação | **Negativo** | `400 Bad Request` | `erro-api-schema.json` |

### 3.2 Implementação de Testes de API e Contrato
* **Validação de Status Code:** Verificação rigorosa dos códigos HTTP `200`, `201`, `204`, `400`, `404` e `409`.
* **Validação de Corpo JSON:** Inspeção de atributos raiz, objetos aninhados (`sensor_iot.modelo`, `caminhao.placa`, `plano_acao_corretiva.descricao`) e tamanho de listas com **RestAssured** e **Hamcrest**.
* **Testes de Contrato (JSON Schema):** Utilização do `io.rest-assured:json-schema-validator` (`matchesJsonSchemaInClasspath`) validando tipos, campos obrigatórios (`required`), limites numéricos (`minimum`/`maximum`), listas (`minItems`) e enumerações (`enum`) em **todas** as respostas da API.

---

## 🚀 4. Passo a Passo para Configurar e Executar os Testes

### Pré-requisitos
* **Java JDK 17** ou superior instalado (`java -version`)
* **Apache Maven 3.8+** instalado (`mvn -version`)
* *(Opcional)* **Docker** e **Docker Compose** para execução containerizada

> 💡 **Execução Zero-Config:** Durante os testes (`mvn test`), o Spring Boot inicializa automaticamente em uma porta HTTP aleatória (`RANDOM_PORT`) com a base de dados ESG pré-carregada em memória (`EcoSmartDataStore`), **não exigindo nenhuma configuração externa prévia** para rodar todos os testes!

### Opção 1: Executar os Testes Localmente via Maven (Recomendado)

1. Abra o terminal na pasta raiz do projeto (`ecosmart-waste-management`) e execute:
   ```bash
   mvn clean test
   ```
2. Ao final da execução, o console exibirá o resumo dos **30 cenários BDD (180 passos — 100% aprovados)** e gerará automaticamente os relatórios visuais de evidência em:
   * **Relatório HTML interativo:** `target/cucumber-reports/cucumber.html`
   * **Relatório JSON:** `target/cucumber-reports/cucumber.json`

### Opção 2: Executar a Aplicação e Testes via Docker

1. Para construir a imagem Docker (que executa todos os testes automatizados durante o estágio de build) e subir o ambiente completo com MongoDB:
   ```bash
   docker compose up --build -d
   ```
2. A API ficará disponível em `http://localhost:8080` (ex.: `http://localhost:8080/api/pontos-coleta` e `http://localhost:8080/actuator/health`).

### Opção 3: Pipeline CI/CD (GitHub Actions)
O projeto inclui o workflow `.github/workflows/ci-tests.yml`, que executa automaticamente `mvn clean test` a cada `push` ou `pull_request` na branch `main` e publica o relatório HTML do Cucumber como artefato da execução.

---

## 🔌 5. Resumo dos Endpoints da API

| Método | Endpoint | Descrição |
| :---: | :--- | :--- |
| `GET` | `/api/pontos-coleta` | Lista pontos de coleta (filtros opcionais: `status`, `ocupacaoMinima`, `tipoResiduo`) |
| `GET` | `/api/pontos-coleta/{codigoPonto}` | Busca um ponto de coleta pelo código (ex.: `PC-001`) |
| `POST` | `/api/pontos-coleta` | Cadastra um novo ponto de coleta inteligente ou convencional |
| `PATCH` | `/api/pontos-coleta/{codigoPonto}/telemetria` | Atualiza nível de ocupação e bateria do sensor IoT |
| `DELETE` | `/api/pontos-coleta/{codigoPonto}` | Remove um ponto de coleta |
| `GET` | `/api/coletas-agendadas` | Lista rotas de coleta (filtro opcional: `status`) |
| `GET` | `/api/coletas-agendadas/{idRota}` | Busca detalhes de uma rota agendada (ex.: `ROTA-001`) |
| `POST` | `/api/coletas-agendadas` | Agenda nova rota de coleta validando pontos ativos e $\text{CO}_2$ |
| `PATCH` | `/api/coletas-agendadas/{idRota}/status` | Atualiza status da rota (`agendado`, `em_andamento`, `concluido`, `cancelado`) |
| `DELETE` | `/api/coletas-agendadas/{idRota}` | Remove registro de rota de coleta |
| `GET` | `/api/usuarios-cidadaos` | Lista cidadãos cadastrados (filtro opcional: `pontuacaoMinima`) |
| `GET` | `/api/usuarios-cidadaos/{email}` | Consulta perfil, badges e histórico de descarte do cidadão |
| `POST` | `/api/usuarios-cidadaos` | Cadastra novo morador no programa de recompensas |
| `POST` | `/api/usuarios-cidadaos/{email}/descartes` | Registra novo descarte reciclável e credita pontos |
| `GET` | `/api/cooperativas-parceiras` | Lista cooperativas (filtros: `material`, `capacidadeMinima`) |
| `GET` | `/api/cooperativas-parceiras/{codigo}` | Busca cooperativa parceira pelo código (ex.: `COOP-001`) |
| `POST` | `/api/cooperativas-parceiras` | Cadastra nova cooperativa validando unicidade de CNPJ |
| `GET` | `/api/auditorias-ambientais` | Lista auditorias ambientais (filtro: `statusConformidade`) |
| `GET` | `/api/auditorias-ambientais/{codigo}` | Consulta auditoria ambiental por código (ex.: `AUD-001`) |
| `POST` | `/api/auditorias-ambientais` | Registra auditoria calculando atingimento e exigindo plano de ação se não conforme |

