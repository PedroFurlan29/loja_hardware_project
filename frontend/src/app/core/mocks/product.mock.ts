import { Produto } from '../models/produto.models';

export const MOCK_PRODUCT: Produto = {
  sku: 'HW-CPU-5600',
  nome: 'Processador Ryzen 5 5600X',
  precoVenda: 899.9,
  qtdEstoque: 24,
  qtdMinima: 2,
  tipoProduto: 'CPU',
  descricaoTecnica: 'Processador AMD Ryzen 5 5600X com 6 núcleos e 12 threads. Excelente para games e trabalho multitarefa.',
  arquitetura: 'Zen 3',
  nucleos: 6
};
