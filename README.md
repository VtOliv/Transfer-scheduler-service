# Transfer Scheduler Service

Sistema de agendamento de transferências financeiras desenvolvido em **Java 11** com **Spring Boot 2.7.18**.

## 📌 Objetivo

Permitir que usuários agendem transferências financeiras com cálculo automático de taxa baseado na data da transferência.

---

## 🛠️ Tecnologias utilizadas

- Java 11
- Spring Boot 2.7.18
- Spring Web
- Spring Data JPA
- H2 Database (em memória)
- Bean Validation (javax.validation)
- Lombok
- JUnit 5 + Mockito
- SLF4J (logs)
- Swagger / OpenAPI

---

## 🚀 Como executar o projeto

### Pré-requisitos
- Java 11
- Maven 3+

### Executando

    mvn spring-boot:run

A aplicação será iniciada em:

    http://localhost:8097

---

## 🗄️ Banco de dados (H2)

Console disponível em:

    http://localhost:8097/h2-console

### Configuração:
- JDBC URL: jdbc:h2:mem:dbtransfers
- User: root
- Password: root

---

## 📡 Endpoints da API

### 🔹 Agendar transferência

    POST /transfers

#### Request
    {
      "sourceAccount": "1234567890",
      "destinationAccount": "0987654321",
      "amount": 100.00,
      "transferDate": "2026-04-10"
    }

#### Response (201 Created)
    {
      "id": 1,
      "sourceAccount": "1234567890",
      "destinationAccount": "0987654321",
      "amount": 100.00,
      "fee": 12.00,
      "schedulingDate": "2026-04-01",
      "transferDate": "2026-04-10"
    }

---

### 🔹 Listar transferências (Extrato)

    GET /transfers

#### Response
    [
      {
        "id": 1,
        "sourceAccount": "1234567890",
        "destinationAccount": "0987654321",
        "amount": 100.00,
        "fee": 12.00,
        "schedulingDate": "2026-04-01",
        "transferDate": "2026-04-10"
      }
    ]

---

## 💰 Regras de cálculo da taxa

A taxa depende da diferença entre a data de agendamento e a data da transferência:

| Dias | Taxa fixa | Percentual |
|------|----------|------------|
| 0    | R$ 3,00  | 2,5%       |
| 1–10 | R$ 12,00 | 0%         |
| 11–20| R$ 0,00  | 8,2%       |
| 21–30| R$ 0,00  | 6,9%       |
| 31–40| R$ 0,00  | 4,7%       |
| 41–50| R$ 0,00  | 1,7%       |

➡️ Caso não exista taxa aplicável, a transferência é bloqueada.

---

## ✅ Validações aplicadas

- Conta deve possuir **10 dígitos numéricos**
- Valor deve ser **maior que zero**
- Data da transferência não pode ser no passado
- Conta de origem deve ser diferente da conta de destino

---

## ⚠️ Tratamento de erros

Erros são tratados globalmente via `ControllerAdvice`.

Exemplo:

    {
      "timestamp": "2026-04-01T12:00:00",
      "status": 400,
      "error": "Bad Request",
      "message": "A conta deve conter 10 dígitos",
      "path": "/transfers"
    }

---

## 📊 Logs

O sistema utiliza SLF4J para registrar:

- requisições recebidas
- transferências criadas
- taxa aplicada
- listagem de registros

---

## 🧪 Testes

- Testes unitários do `TransferService`
- Cobertura das regras de taxa (todas as faixas)
- Testes de exceções e validações

---

## 📘 Swagger / OpenAPI

Documentação disponível em:

    http://localhost:8097/swagger-ui.html

---

## 🧠 Decisões arquiteturais

- Separação em camadas: Controller, Service, Repository
- Uso de DTO único para simplificação (escopo de teste)
- Regra de negócio centralizada no Service
- Tratamento de exceções global
- Banco em memória para facilitar execução
- Logs para rastreabilidade

---

## 📌 Observações

Este projeto foi desenvolvido como parte de um teste técnico, com foco em:

- clareza de código
- aderência aos requisitos
- boas práticas
- organização
