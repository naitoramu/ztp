import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ProductCardComponent} from './product-card.component';
import {ToastrService} from 'ngx-toastr';
import {NO_ERRORS_SCHEMA} from '@angular/core';
import {provideRouter} from "@angular/router";
import {HttpTestingController, provideHttpClientTesting} from "@angular/common/http/testing";
import {provideHttpClient} from "@angular/common/http";
import {ErrorResponse} from "../../model/ErrorResponse";

describe('ProductCardComponent', () => {
  const BASE_URL: string = 'http://localhost:8080';
  let component: ProductCardComponent;
  let fixture: ComponentFixture<ProductCardComponent>;
  let httpClient: HttpTestingController;
  let toastrService: jasmine.SpyObj<ToastrService>;

  beforeEach(() => {
    toastrService = jasmine.createSpyObj('ToastrService', ['success', 'error']);

    TestBed.configureTestingModule({
      imports: [ProductCardComponent],
      providers: [
        provideRouter([]),
        provideHttpClient(),
        provideHttpClientTesting(),
        { provide: ToastrService, useValue: toastrService },
      ],
      schemas: [NO_ERRORS_SCHEMA],
    });

    fixture = TestBed.createComponent(ProductCardComponent);
    component = fixture.componentInstance;
    httpClient = TestBed.inject(HttpTestingController);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should delete product and show success toast on deleteProduct', () => {
    const productId: string = '1';
    toastrService.success.and.stub();

    component.deleteProduct(productId);

    expectHttpRequestAndRespond(productId, {}, 'DELETE');
    expect(toastrService.success).toHaveBeenCalledWith('Product successfully deleted');
  });

  function expectHttpRequestAndRespond(productId: string, data: any, method: string = 'GET', statusCode: number = 200): void {
    httpClient.expectOne({
      url: `${BASE_URL}/v1/products/${productId}`,
      method: method
    }).flush(data, {status: statusCode, statusText: ''});
  }
});

