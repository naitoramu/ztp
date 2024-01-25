import {ComponentFixture, TestBed, waitForAsync} from '@angular/core/testing';
import {ActivatedRoute, Data, Router} from '@angular/router';
import {of} from 'rxjs';
import {ToastrService} from 'ngx-toastr';
import {ProductFormComponent} from './product-form.component';
import {ProductService} from '../../service/product.service';
import {Product} from '../../model/Product';
import {InputErrorComponent} from '../input-error/input-error.component';

describe('ProductFormComponent', () => {
  let component: ProductFormComponent;
  let fixture: ComponentFixture<ProductFormComponent>;
  let productService: jasmine.SpyObj<ProductService>;
  let toastrService: jasmine.SpyObj<ToastrService>;
  let router: jasmine.SpyObj<Router>;
  let activatedRoute: ActivatedRoute;

  beforeEach(() => {
    productService = jasmine.createSpyObj('ProductService', ['getProduct', 'createProduct', 'updateProduct', 'deleteProduct']);
    toastrService = jasmine.createSpyObj('ToastrService', ['success', 'error']);
    router = jasmine.createSpyObj('Router', ['navigate']);
    activatedRoute = {
      data: of({ title: 'YourTitle', action: 'EDIT' } as Data),
      snapshot: {
        paramMap: {
          get: jasmine.createSpy('get').and.returnValue('1') // Set the desired productId for testing
        }
      }
    } as any;

    TestBed.configureTestingModule({
      imports: [ProductFormComponent, InputErrorComponent],
      providers: [
        { provide: ProductService, useValue: productService },
        { provide: ToastrService, useValue: toastrService },
        { provide: Router, useValue: router },
        { provide: ActivatedRoute, useValue: activatedRoute },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductFormComponent);
    component = fixture.componentInstance;
    productService.getProduct.and.returnValue(of({id: '1'} as Product));
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize formTitle and call fetchProduct for EDIT action', () => {
    expect(component.formTitle).toEqual('YourTitle');
    expect(component['action']).toEqual('EDIT');
    expect(component.productId).toEqual('1');
    expect(productService.getProduct).toHaveBeenCalledWith('1');
  });

  it('should call createNewProduct when form is submitted for CREATE action', () => {
    const createNewProductSpy: jasmine.Spy<never> =  spyOn<any>(component, 'createNewProduct');
    component['action'] = 'CREATE';

    fillRequiredFormFields();
    fixture.detectChanges();
    component.submitForm();

    expect(createNewProductSpy).toHaveBeenCalled();
  });

  it('should call updateExistingProduct when form is submitted for EDIT action', () => {
    const updateExistingProductSpy: jasmine.Spy<never> =  spyOn<any>(component, 'updateExistingProduct');

    fillRequiredFormFields()
    fixture.detectChanges();
    component.submitForm();

    expect(updateExistingProductSpy).toHaveBeenCalled();
  });

  it('should show error toast and not call service methods when form is invalid', () => {
    fixture.detectChanges()
    component.submitForm();

    expect(component['toastService'].error).toHaveBeenCalledWith('Correct data and try again', 'Invalid form');
    expect(productService.createProduct).not.toHaveBeenCalled();
    expect(productService.updateProduct).not.toHaveBeenCalled();
  });

  it('should call showToastAndNavigateToProductDetails after successful create', waitForAsync(() => {
    const showToastAndNavigateSpy: jasmine.Spy<any> = spyOn<any>(component, 'showToastAndNavigateToProductDetails');
    productService.createProduct.and.returnValue(of({ id: '2' } as Product));
    component['action'] = 'CREATE';

    fillRequiredFormFields();
    component.submitForm();

    fixture.whenStable().then(() => {
      expect(showToastAndNavigateSpy).toHaveBeenCalledWith('Product created successfully!', '2');
    });
  }));

  it('should call showToastAndNavigateToProductDetails after successful update', waitForAsync(() => {
    productService.updateProduct.and.returnValue(of({ id: '1' } as Product));
    const showToastAndNavigateSpy: jasmine.Spy<any> = spyOn<any>(component, 'showToastAndNavigateToProductDetails');

    fillRequiredFormFields();
    component.submitForm();

    fixture.whenStable().then(() => {
      expect(showToastAndNavigateSpy).toHaveBeenCalledWith('Product updated successfully!', '1');
    });
  }));

  it('should call router.navigate when deleteProduct is called', () => {
    productService.deleteProduct.and.returnValue(of(void 0));
    component.deleteProduct();
    expect(router.navigate).toHaveBeenCalledWith(['products']);
  });

  function fillRequiredFormFields(): void {
    component.productForm.controls.name.patchValue('product name');
    component.productForm.controls.description.patchValue('product description');
    component.productForm.controls.price.patchValue(21.37);
    component.productForm.controls.availableQuantity.patchValue(420);
  }
});

