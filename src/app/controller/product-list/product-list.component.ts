import { Component } from '@angular/core';
import {Observable} from "rxjs";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {ProductCardComponent} from "../product-card/product-card.component";
import {Product} from "../../model/Product";
import {ProductService} from "../../service/product.service";

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    AsyncPipe,
    ProductCardComponent,
    NgForOf,
    NgIf,
  ],
  templateUrl: '../../view/product-list/product-list.component.html',
  styleUrl: '../../view/product-list//product-list.component.css'
})
export class ProductListComponent {

  products$: Observable<Product[]> = this.productService.getProducts();

  constructor(private productService: ProductService) {}

}
