import {useState} from 'react';
import {ToastAndroid} from 'react-native';
import {ProductDetails} from '../model/type/ProductDetails';
import Violation from "../model/type/Violation";
import {useNavigation} from "@react-navigation/native";
import ProductModel from "../model/ProductModel";

const ProductDetailsView = (productId: string, action: string) => {
  const navigation = useNavigation();
  const [product, setProduct] = useState<ProductDetails>({
    name: '',
    description: '',
    price: 0,
    availableQuantity: 0,
  } as ProductDetails);

  const onInit = (): void => {
    if (action !== 'CREATE') {
      ProductModel().fetchProduct(productId)
        .then(response => response.json())
        .then((data: ProductDetails) => setProduct(data))
        .catch(error => console.error(`Error fetching product with ID: ${productId}`, error));
    }
  };

  const onNameChange = (name: string) => {
    setProduct({...product, name: name} as ProductDetails);
  }

  const onDescriptionChange = (description: string) => {
    setProduct({...product, description: description} as ProductDetails);
  }

  const onPriceChange = (price: number) => {
    setProduct({...product, price: price} as ProductDetails);
  }

  const onAvailableQuantityChange = (availableQuantity: number) => {
    setProduct({...product, availableQuantity: availableQuantity} as ProductDetails);
  }

  const onSubmit = (): void => {
    switch (action) {
      case 'CREATE':
        ProductModel().createProduct(product)
          .then(response => (response.status === 201
            ? navigation.navigate('ProductList')
            : response.json().then(data => handleErrorResponse(data))))
          .catch(error => console.error(`Error creating product with ID: ${product}`, error));
        break;
      case 'UPDATE':
        ProductModel().updateProduct(product)
          .then(response => (response.status === 200
            ? navigation.navigate('ProductList')
            : response.json().then(data => handleErrorResponse(data))))
          .catch(error => console.error(`Error updating product with ID: ${product.id}`, error));
        break;
      default:
        throw Error(`Unknown action: ${action}`);
    }
  };

  const isEditable = (): boolean => {
    return action === 'CREATE' || action === 'UPDATE';
  };


  const handleErrorResponse = (data: { details: string, violations: Violation[] }): any => {
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