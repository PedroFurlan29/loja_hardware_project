import { Produto } from '../models/produto.models';

export const MOCK_PRODUCT: Produto = {
  id: 1,
  sku: 'HW-CPU-5600',
  nome: 'Processador Ryzen 5 5600X',
  preco: 899.9,
  quantidadeDisponivel: 24,
  estoqueMinimo: 2,
  categoria: 'CPU',
  descricao: 'Processador AMD Ryzen 5 5600X com 6 núcleos e 12 threads. Excelente para games e trabalho multitarefa.',
  ativo: true
};
