import { Routes } from '@angular/router';
import {ProductListComponent} from "./controller/product-list/product-list.component";
import {ProductFormComponent} from "./controller/product-form/product-form.component";
import {ErrorPageComponent} from "./view/error-page/error-page.component";

export const routes: Routes = [
  {
    path: 'products',
    component: ProductListComponent,
  },
  {
    path: 'products/:productId/details',
    component: ProductFormComponent,
    data: {title: 'Product details'}
  },
  {
    path: 'products/new',
    component: ProductFormComponent,
    data: {title: 'Create new product', action: 'CREATE'}
  },
  {
    path: 'products/:productId/edit',
    component: ProductFormComponent,
    data: {title: 'Update product', action: 'EDIT'}
  },
  {
    path: 'error-page',
    component: ErrorPageComponent,
  },
  {
    path: '**',
    redirectTo: 'products'
  },
];
