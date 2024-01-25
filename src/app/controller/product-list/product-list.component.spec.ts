import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ProductListComponent} from './product-list.component';
import {Product} from "../../model/Product";
import {ProductService} from "../../service/product.service";
import {of} from "rxjs";
import {provideHttpClient} from "@angular/common/http";
import {provideToastr} from "ngx-toastr";
import {provideRouter} from "@angular/router";

describe('ProductListComponent', () => {
  let component: ProductListComponent;
  let fixture: ComponentFixture<ProductListComponent>;

  beforeEach(async (): Promise<any> => {

    await TestBed.configureTestingModule({
      imports: [ProductListComponent],
      providers: [
        ProductService,
        provideRouter([]),
        provideToastr(),
        provideHttpClient()
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch products on initialization', () => {
    const products: Product[] = [
      {id: `1`, name: 'Product 1', description: 'Description 1', price: 21.37, availableQuantity: 11},
      {id: `2`, name: 'Product 2', description: 'Description 2', price: 420.69, availableQuantity: 12},
    ];

    spyOn(component['productService'], 'getProducts').and.returnValue(of(products))

    fixture.detectChanges();

    fixture.whenStable().then(() => {
      expect(component.products$).toBeTruthy();
      component.products$.subscribe(
        (data: Product[]): void => expect(data).toEqual(products)
      );
    });
  });
});
