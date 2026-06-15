#!/bin/bash
set -e

echo "Hardware Store - Setup Inicial"

if ! command -v docker &> /dev/null; then
    echo "Docker nao encontrado. Instale em https://docker.com"
    exit 1
fi

echo "Buildando Docker image..."
docker-compose build

echo "Iniciando stack..."
docker-compose up -d

echo "Aguardando aplicacao inicializar..."
sleep 20

echo "Verificando saude dos servicos..."
if curl -s http://localhost:8080/api/swagger-ui.html > /dev/null; then
    echo "API rodando em http://localhost:8080/api"
    echo "Swagger em http://localhost:8080/api/swagger-ui.html"
else
    echo "API nao respondendo"
    docker-compose logs backend
    exit 1
fi

echo "Setup concluido com sucesso!"
