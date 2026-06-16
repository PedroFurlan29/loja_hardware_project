import { Routes } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { CategoriaComponent } from './features/categories/categoria.component';
import { ProductComponent } from './features/products/product.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'categoria/:nome', component: CategoriaComponent },
  { path: 'produto/:id', component: ProductComponent },
  { path: 'produtos/:id', component: ProductComponent },
  { path: 'categorias', component: CategoriaComponent },
  { path: '**', redirectTo: '' }
];
