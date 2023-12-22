import {ProductDetails} from "./type/ProductDetails";
import {Cache} from "react-native-cache";
import AsyncStorage from "@react-native-async-storage/async-storage";
import Violation from "./type/Violation";
import Problem from "./type/Problem";
import {ProductSummary} from "./type/ProductSummary";

const ProductModel = () => {

  const API_URL: string = 'http://192.168.1.114:8080';

  const cache: Cache = new Cache({
    namespace: "products",
    policy: {
      maxEntries: 50000,
      stdTTL: 10
    },
    backend: AsyncStorage
  });

  const fetchProduct = async (productId: string): Promise<ProductDetails> =>
    fetchAndCache<ProductDetails>(`product-${productId}`, `${API_URL}/v1/products/${productId}`);

  const fetchProducts = async (cached: boolean = true): Promise<ProductSummary[]> =>
    fetchAndCache<ProductSummary[]>('products-list', `${API_URL}/v1/products`, cached);

  const fetchAndCache = async <T>(cacheKey: string, url: string, cached: boolean = true): Promise<T> => {
    const dataFromCache: string | undefined = await cache.get(cacheKey);

    if (dataFromCache && cached) {
      return JSON.parse(dataFromCache);
    }

    const response: Response = await fetch(url);

    if (response.status === 200) {
      const fetchedData: T = await response.json() as T;
      await cache.set(cacheKey, JSON.stringify(fetchedData));
      return fetchedData;
    } else {
      throw errorResponseToStr(await response.json() as Problem);
    }
  };

  const createProduct = async (product: ProductDetails): Promise<ProductDetails> => {
    const response: Response = await fetch(`${API_URL}/v1/products`, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(product),
    });

    if (response.status == 201) {
      const createdProduct: ProductDetails = await response.json() as unknown as ProductDetails;
      await cache.set(`product-${product.id}`, JSON.stringify(createdProduct));
      return createdProduct;
    } else {
      throw errorResponseToStr(await response.json() as unknown as Problem);
    }
  };

  const updateProduct = async (product: ProductDetails): Promise<ProductDetails> => {
    const response: Response = await fetch(`${API_URL}/v1/products/${product.id}`, {
      method: 'PUT',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(product),
    });

    if (response.status == 200) {
      const updatedProduct: ProductDetails = await response.json() as unknown as ProductDetails;
      await cache.set(`product-${product.id}`, JSON.stringify(updatedProduct));
      return updatedProduct;
    } else {
      throw errorResponseToStr(await response.json() as unknown as Problem);
    }
  };

  const deleteProduct = async (productId: string): Promise<void> => {
    const response: Response = await fetch(`${API_URL}/v1/products/${productId}`, {method: 'DELETE'});
    if (response.status === 204) {
      return Promise.resolve();
    }
    throw errorResponseToStr(await response.json() as Problem);
  };

  const errorResponseToStr = (problem: Problem): string => {
    const violations: string = problem.violations
      ? problem.violations
        .map((violation: Violation) => `${violation.field}: ${violation.message}`)
        .join('\n')
      : '';

    return `${problem.details}\n${violations}`;
  }

  return {
    fetchProduct,
    fetchProducts,
    createProduct,
    updateProduct,
    deleteProduct
  }
}

export default ProductModel;