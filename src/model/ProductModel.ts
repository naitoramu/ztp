import {ProductDetails} from "./type/ProductDetails";

const ProductModel = () => {

  const API_URL: string = 'http://192.168.1.114:8080';

  const fetchProduct = (productId: string): Promise<Response> => {
    return fetch(`${API_URL}/v1/products/${productId}`);
  };

  const fetchProducts = (): Promise<Response> => {
    return fetch(`${API_URL}/v1/products`);
  };

  const createProduct = (product: ProductDetails): Promise<Response> => {
    return fetch(`${API_URL}/v1/products`, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(product),
    });
  };

  const updateProduct = (product: ProductDetails): Promise<Response> => {
     return fetch(`${API_URL}/v1/products/${product.id}`, {
      method: 'PUT',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(product),
    });
  };

  const deleteProduct = (productId: string): Promise<Response> => {
    return fetch(`${API_URL}/v1/products/${productId}`, { method: 'DELETE' });
  };

  return {
    fetchProduct,
    fetchProducts,
    createProduct,
    updateProduct,
    deleteProduct
  }
}

export default ProductModel;