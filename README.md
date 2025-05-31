# 🚧 Status do Projeto

Este projeto ainda está em desenvolvimento e pode conter funcionalidades incompletas ou em progresso.

# Gerenciador de Tarefas

Este projeto é uma aplicação de **gerenciamento de tarefas**, desenvolvida como parte do **trabalho final da disciplina de Arquitetura Java**, no curso de **Pós-Graduação em Arquitetura de Software** da **PUC Minas**.

## 🎯 Objetivo

A aplicação foi projetada para demonstrar conceitos de arquitetura de software com Java, utilizando autenticação com Keycloak, persistência de dados com PostgreSQL e administração do banco via PGAdmin. Todos os serviços são orquestrados com Docker Compose.

## 🛠️ Tecnologias Utilizadas

- **Java / Spring Boot**
- **PostgreSQL**
- **Keycloak**
- **PGAdmin**
- **Docker e Docker Compose**

## ▶️ Como Executar o Projeto

Certifique-se de que você tem o **Docker** e o **Docker Compose** instalados em sua máquina.

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/seu-repo.git
cd seu-repo
```

### 2. Suba os containers com Docker Compose
```
docker-compose up
```

Este comando irá iniciar os seguintes serviços:

🔐 Keycloak – Gerenciador de autenticação e identidade

🛢️ PostgreSQL – Banco de dados relacional

📊 PGAdmin – Interface web para gerenciamento do PostgreSQL

### 3. Acessos padrão
Keycloak: http://localhost:8080

PGAdmin: http://localhost:5050

PostgreSQL: localhost:5432

Aplicação (Spring Boot): http://localhost:8081 (ou porta configurada no projeto)

⚠️ Verifique o arquivo docker-compose.yml para confirmar as portas mapeadas e credenciais utilizadas.



