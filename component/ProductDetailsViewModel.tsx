import {useState} from 'react';
import {ToastAndroid} from 'react-native';
import {ProductDto} from '../model/type/ProductDto';
import Violation from "../model/type/Violation";

const ProductDetailsView = (productId: number, action: string) => {
  const [product, setProduct] = useState<ProductDto>({
    name: '',
    description: '',
    price: 0,
    availableQuantity: 0,
  } as ProductDto);

  const onInit = (): void => {
    if (action !== 'CREATE') {
      fetchProduct(productId);
    }
  };
  
  const onNameChange = (name: string) => {
    setProduct({ ...product, name: name} as ProductDto);
  }

  const onDescriptionChange = (description: string) => {
    setProduct({ ...product, description: description} as ProductDto);
  }

  const onPriceChange = (price: number) => {
    setProduct({ ...product, price: price} as ProductDto);
  }

  const onAvailableQuantityChange = (availableQuantity: number) => {
    setProduct({ ...product, availableQuantity: availableQuantity} as ProductDto);
  }

  const onSubmit = (): void => {
    switch (action) {
      case 'CREATE':
        createProduct();
        break;
      case 'UPDATE':
        updateProduct();
        break;
      default:
        throw Error(`Unknown action: ${action}`);
    }
  };

  const isEditable = (): boolean => {
    return action === 'CREATE' || action === 'UPDATE';
  };

  const fetchProduct = (productId: string): void => {
    fetch(`http://192.168.0.186:8080/v1/products/${productId}`)
      .then(response => response.json())
      .then((data: ProductDto) => setProduct(data))
      .catch(error => console.error(`Error fetching product with ID: ${productId}`, error));
  };

  const updateProduct = (): void => {
    fetch(`http://192.168.0.186:8080/v1/products/${product.id}`, {
      method: 'PUT',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(product),
    })
      .then(response => (response.status === 200
        ? navigation.navigate('ProductList')
        : response.json().then(data => handleErrorResponse(data))))
      .catch(error => console.error(`Error updating product with ID: ${product.id}`, error));
  };

  const createProduct = (): void => {
    fetch(`http://192.168.0.186:8080/v1/products`, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body: JSON.stringify(product),
    })
      .then(response => (response.status === 201
        ? navigation.navigate('ProductList')
        : response.json().then(data => handleErrorResponse(data))))
      .catch(error => console.error(`Error creating product with ID: ${product}`, error));
  };

  const handleErrorResponse = (data: { violations: Violation[] }): any => {
    const violations: string = data.violations
      .map((violation: Violation) => `${violation.field}: ${violation.message}`)
      .join('\n');

    ToastAndroid.show(`${data.details}\n${violations}`, ToastAndroid.LONG);
  }

  return {
    product,
    onInit,
    onNameChange,
    onDescriptionChange,
    onPriceChange,
    onAvailableQuantityChange,
    onSubmit,
    isEditable
  };
};

export default ProductDetailsView;