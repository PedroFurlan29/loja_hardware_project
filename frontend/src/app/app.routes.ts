import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { ProdutosComponent } from './features/produtos/produtos.component';
import { ProdutoDetalheComponent } from './features/produto-detalhe/produto-detalhe.component';
import { CarrinhoComponent } from './features/carrinho/carrinho.component';
import { CheckoutComponent } from './features/checkout/checkout.component';
import { LoginComponent } from './features/auth/login.component';
import { AdminComponent } from './features/admin/admin.component';
import { authGuard } from './core/guards/auth.guard';
import { adminGuard } from './core/guards/admin.guard';
import { vendedorGuard } from './core/guards/vendedor.guard';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'produtos', component: ProdutosComponent },
  { path: 'produtos/:id', component: ProdutoDetalheComponent },
  { path: 'carrinho', component: CarrinhoComponent },
  { path: 'checkout', component: CheckoutComponent, canActivate: [authGuard] },
  { path: 'admin', component: AdminComponent, canActivate: [adminGuard] },
  { path: 'admin/vendas', component: AdminComponent, canActivate: [vendedorGuard] },
  { path: 'admin/estoque', component: AdminComponent, canActivate: [adminGuard] },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: '' }
];