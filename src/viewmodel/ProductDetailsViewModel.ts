import {useState} from 'react';
import {ToastAndroid} from 'react-native';
import {ProductDetails} from '../model/type/ProductDetails';
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
        .then((product: ProductDetails) => setProduct(product))
        .catch(error => showError(error));
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
          .then(_ => navigation.navigate('ProductList'))
          .catch(error => showError(error));
        break;
      case 'UPDATE':
        ProductModel().updateProduct(product)
          .then(_ => navigation.navigate('ProductList'))
          .catch(error => showError(error));
        break;
      default:
        throw Error(`Unknown action: ${action}`);
    }
  };

  const isEditable = (): boolean => {
    return action === 'CREATE' || action === 'UPDATE';
  };

  const showError = (message: string): any => {
    ToastAndroid.show(message, ToastAndroid.LONG);
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