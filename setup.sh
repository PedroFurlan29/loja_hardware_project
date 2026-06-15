#!/bin/bash
set -e

echo "🚀 Hardware Store - Setup Inicial"

if ! command -v docker &> /dev/null; then
    echo "❌ Docker não encontrado. Instale em https://docker.com"
    exit 1
fi

echo "🔨 Buildando Docker image..."
docker-compose build

echo "🎯 Iniciando stack..."
docker-compose up -d

echo "⏳ Aguardando aplicação inicializar..."
sleep 20

echo "🏥 Verificando saúde dos serviços..."
if curl -s http://localhost:8080/api/swagger-ui.html > /dev/null; then
    echo "✅ API rodando em http://localhost:8080/api"
    echo "📖 Swagger em http://localhost:8080/api/swagger-ui.html"
else
    echo "❌ API não respondendo"
    docker-compose logs backend
    exit 1
fi

echo "🎉 Setup concluído com sucesso!"
