// ============================================================================
// EcoSmart Waste Management — Script de Inicialização MongoDB (ecosmart_db)
// Aluno: Pedro Henrique De Souza Santiago
// ============================================================================

db = db.getSiblingDB("ecosmart_db");

db.createCollection("pontos_coleta", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["codigo_ponto", "tipo_residuo", "localizacao", "status"],
      properties: {
        codigo_ponto: { bsonType: "string" },
        tipo_residuo: { bsonType: "array" },
        localizacao: { bsonType: "object" },
        nivel_ocupacao_percentual: { bsonType: "number", minimum: 0, maximum: 100 },
        status: { enum: ["ativo", "manutencao", "inativo"] }
      }
    }
  },
  validationLevel: "moderate"
});

db.createCollection("coletas_agendadas");
db.createCollection("usuarios_cidadaos");
db.createCollection("cooperativas_parceiras");
db.createCollection("auditoria_ambiental");

db.pontos_coleta.createIndex({ localizacao: "2dsphere" });
db.pontos_coleta.createIndex({ codigo_ponto: 1 }, { unique: true });
db.usuarios_cidadaos.createIndex({ email: 1 }, { unique: true });
db.coletas_agendadas.createIndex({ status: 1, data_hora_agendada: 1 });
db.auditoria_ambiental.createIndex({ periodo_referencia: 1, metrica: 1 });

db.pontos_coleta.insertMany([
  {
    codigo_ponto: "PC-001",
    tipo_residuo: ["organico", "reciclavel"],
    localizacao: { type: "Point", coordinates: [-44.9903, -12.1444] },
    bairro: "Centro",
    capacidade_litros: 500,
    nivel_ocupacao_percentual: 78,
    status: "ativo",
    data_instalacao: new Date("2023-03-10"),
    sensor_iot: { modelo: "EcoSense X200", bateria_percentual: 82, temperatura_c: 27.4, ultima_leitura: new Date("2026-09-07T08:00:00Z") }
  },
  {
    codigo_ponto: "PC-002",
    tipo_residuo: ["reciclavel", "vidro"],
    localizacao: { type: "Point", coordinates: [-44.9820, -12.1390] },
    bairro: "Vila Rica",
    capacidade_litros: 700,
    nivel_ocupacao_percentual: 45,
    status: "ativo",
    data_instalacao: new Date("2023-05-22"),
    sensor_iot: { modelo: "EcoSense X200", bateria_percentual: 65, temperatura_c: 29.1, ultima_leitura: new Date("2026-09-07T08:05:00Z") },
    campanha_educativa: true
  },
  {
    codigo_ponto: "PC-003",
    tipo_residuo: ["organico"],
    localizacao: { type: "Point", coordinates: [-44.9955, -12.1520] },
    bairro: "Junco",
    capacidade_litros: 400,
    nivel_ocupacao_percentual: 92,
    status: "ativo",
    data_instalacao: new Date("2022-11-02")
  },
  {
    codigo_ponto: "PC-004",
    tipo_residuo: ["eletronico"],
    localizacao: { type: "Point", coordinates: [-45.0010, -12.1600] },
    bairro: "Sandra Regina",
    capacidade_litros: 300,
    nivel_ocupacao_percentual: 30,
    status: "ativo",
    data_instalacao: new Date("2024-01-18"),
    sensor_iot: { modelo: "EcoSense Lite", bateria_percentual: 74, temperatura_c: 26.0, ultima_leitura: new Date("2026-09-07T07:50:00Z") },
    alerta_incendio: false
  },
  {
    codigo_ponto: "PC-005",
    tipo_residuo: ["organico", "reciclavel", "vidro"],
    localizacao: { type: "Point", coordinates: [-44.9780, -12.1350] },
    bairro: "Bela Vista",
    capacidade_litros: 600,
    nivel_ocupacao_percentual: 55,
    status: "ativo",
    data_instalacao: new Date("2023-07-14"),
    campanha_educativa: true
  },
  {
    codigo_ponto: "PC-006",
    tipo_residuo: ["reciclavel"],
    localizacao: { type: "Point", coordinates: [-45.0050, -12.1700] },
    bairro: "Nova Barreiras",
    capacidade_litros: 500,
    nivel_ocupacao_percentual: 88,
    status: "manutencao",
    data_instalacao: new Date("2023-02-09"),
    sensor_iot: { modelo: "EcoSense X200", bateria_percentual: 12, temperatura_c: 31.2, ultima_leitura: new Date("2026-09-06T22:00:00Z") },
    ultima_manutencao: new Date("2026-09-08")
  },
  {
    codigo_ponto: "PC-007",
    tipo_residuo: ["organico"],
    localizacao: { type: "Point", coordinates: [-44.9700, -12.1250] },
    bairro: "São Judas Tadeu",
    capacidade_litros: 450,
    nivel_ocupacao_percentual: 20,
    status: "ativo",
    data_instalacao: new Date("2024-03-30")
  },
  {
    codigo_ponto: "PC-008",
    tipo_residuo: ["eletronico", "reciclavel"],
    localizacao: { type: "Point", coordinates: [-44.9600, -12.1200] },
    bairro: "Alto da Boa Vista",
    capacidade_litros: 350,
    nivel_ocupacao_percentual: 67,
    status: "ativo",
    data_instalacao: new Date("2024-06-11"),
    sensor_iot: { modelo: "EcoSense Lite", bateria_percentual: 58, temperatura_c: 28.3, ultima_leitura: new Date("2026-09-07T08:10:00Z") },
    alerta_incendio: false
  },
  {
    codigo_ponto: "PC-009",
    tipo_residuo: ["reciclavel", "vidro"],
    localizacao: { type: "Point", coordinates: [-44.9880, -12.1480] },
    bairro: "Multivix",
    capacidade_litros: 550,
    nivel_ocupacao_percentual: 40,
    status: "ativo",
    data_instalacao: new Date("2023-09-25"),
    sensor_iot: { modelo: "EcoSense X200", bateria_percentual: 90, temperatura_c: 27.0, ultima_leitura: new Date("2026-09-07T08:02:00Z") }
  },
  {
    codigo_ponto: "PC-010",
    tipo_residuo: ["organico", "reciclavel"],
    localizacao: { type: "Point", coordinates: [-45.0120, -12.1550] },
    bairro: "Aeroporto",
    capacidade_litros: 500,
    nivel_ocupacao_percentual: 0,
    status: "inativo",
    data_instalacao: new Date("2021-12-01"),
    data_desativacao: new Date("2026-08-15")
  }
]);

