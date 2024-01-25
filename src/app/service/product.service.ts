import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {BehaviorSubject, Observable, of, Subject} from "rxjs";
import {catchError, tap} from "rxjs/operators";
import {Router} from "@angular/router";
import {Product} from "../model/Product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private static API_URL: string = 'http://localhost:8080/v1/products';

  private productsSubject: Subject<Product[]> = new BehaviorSubject([{} as Product]);

  constructor(private http: HttpClient,
              private router: Router) {}

  getProducts(): Observable<Product[]> {
    this._getProducts();
    return this.productsSubject.asObservable();
  }

  getProduct(productId: string): Observable<Product> {
    return this.http.get<Product>(`${ProductService.API_URL}/${productId}`).pipe(
      catchError(this.handleError)
    );
  }

  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(ProductService.API_URL, product).pipe(
      catchError(this.handleError)
    );
  }

  updateProduct(productId: string, product: Product): Observable<Product> {
    return this.http.put<Product>(`${ProductService.API_URL}/${productId}`, product).pipe(
      catchError(this.handleError)
    );
  }

  deleteProduct(productId: string): Observable<void> {
    return this.http.delete<void>(`${ProductService.API_URL}/${productId}`).pipe(
      tap(() => this._getProducts()),
      catchError(this.handleError)
    );
  }

  private _getProducts(): void {
    this.http.get<Product[]>(ProductService.API_URL).subscribe({
      next:  (products: Product[]) => this.productsSubject.next(products),
      error: (error) => this.handleError(error)
    });
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    if (error.error instanceof ErrorEvent || error.status === 0) {
      this.router.navigate(['/error-page']);
    }

    return of();
  }
}
