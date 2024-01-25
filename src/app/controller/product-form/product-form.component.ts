import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ActivatedRoute, Data, Router, RouterLink} from "@angular/router";
import {Subscription} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";
import {NgIf} from "@angular/common";
import {InputErrorComponent} from "../input-error/input-error.component";
import {ProductService} from "../../service/product.service";
import {Product} from "../../model/Product";

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,
    RouterLink,
    InputErrorComponent,
  ],
  templateUrl: '../../view/product-form/product-form.component.html',
  styleUrl: '../../view/product-form/product-form.component.css'
})
export class ProductFormComponent implements OnInit, OnDestroy {

  private action?: 'EDIT' | 'CREATE';
  private productSub?: Subscription;
  private dataSub!: Subscription;

  formTitle!: string;
  productId?: string;
  productForm: FormGroup<{
    availableQuantity: FormControl<number | null>;
    price: FormControl<number | null>;
    name: FormControl<string | null>;
    description: FormControl<string | null>
  }> = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.maxLength(256)]),
    description: new FormControl('', [Validators.required, Validators.maxLength(1024)]),
    price: new FormControl(0.0, [Validators.required, Validators.min(0.0)]),
    availableQuantity: new FormControl(0, [Validators.required, Validators.min(0)]),
  });

  constructor(
    private productService: ProductService,
    private toastService: ToastrService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.dataSub = this.route.data.subscribe((data: Data): void => {
      this.formTitle = data['title'];
      this.action = data['action'];
      this.action !== 'CREATE' ? this.fetchProduct() : 'do nothing';
    });
  }

  ngOnDestroy(): void {
    this.dataSub.unsubscribe();
    this.productSub?.unsubscribe();
  }

  private fetchProduct(): void {
    this.productId = this.route.snapshot.paramMap.get('productId')!;
    this.productSub = this.productService.getProduct(this.productId)
      .subscribe((response: Product): void => {
        this.productForm.patchValue(response);
      });
  }

  submitForm(): void {
    this.productForm.markAllAsTouched();

    if (this.productForm.invalid) {
      this.toastService.error('Correct data and try again', 'Invalid form');
      return;
    }

    switch(this.action) {
      case 'CREATE':
        this.createNewProduct();
        break;
      case 'EDIT':
        this.updateExistingProduct();
        break;
    }
  }

  private createNewProduct(): void {
    this.productService.createProduct(this.productForm.getRawValue() as Product)
      .subscribe({
        next: (response: Product): void =>
          this.showToastAndNavigateToProductDetails('Product created successfully!', response.id),
        error: (errorResponse: HttpErrorResponse) =>
          this.toastService.error(errorResponse.error?.details, errorResponse.error?.error)
      });
  }

  private updateExistingProduct(): void {
    this.productService.updateProduct(this.productId!, this.productForm.getRawValue() as Product)
      .subscribe({
        next: (response: Product): void =>
          this.showToastAndNavigateToProductDetails('Product updated successfully!', response.id),
        error: (errorResponse: HttpErrorResponse) =>
          this.toastService.error(errorResponse.error?.details, errorResponse.error?.error)
      });
  }

  private showToastAndNavigateToProductDetails(message: string, productId: string): void {
    this.toastService.success(message);
    this.router.navigate([`products/${productId}/details`]);
  }

  deleteProduct(): void {
    this.productService.deleteProduct(this.productId!).subscribe({
      next: () => this.router.navigate(['products']),
      error: (response: HttpErrorResponse) => this.toastService.error(response.error.details, response.error.error)
    });
  }

  get formIsEditable(): boolean {
    return this.action === 'CREATE' || this.action === 'EDIT';
  }
}
