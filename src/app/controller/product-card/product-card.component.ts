import {Component, Input} from '@angular/core';
import {AsyncPipe, NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {HttpErrorResponse} from "@angular/common/http";
import {ProductService} from "../../service/product.service";
import {Product} from "../../model/Product";

@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [
    NgIf,
    AsyncPipe,
    RouterLink,
  ],
  templateUrl: '../../view/product-card/product-card.component.html',
  styleUrl: '../../view/product-card/product-card.component.css'
})
export class ProductCardComponent {

  @Input()
  product!: Product;

  constructor(
    private productService: ProductService,
    private toastService: ToastrService,
  ) {
  }

  deleteProduct(productId: string): void {
    this.productService.deleteProduct(productId)
      .subscribe({
        next: () => this.toastService.success('Product successfully deleted'),
        error: (response: HttpErrorResponse) => this.toastService.error(response.error.error, response.error.details)
      });
  }
}
