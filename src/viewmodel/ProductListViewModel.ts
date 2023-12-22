import {useNavigation} from '@react-navigation/native';
import {useState} from 'react';
import {ToastAndroid} from 'react-native';
import {ProductSummary} from '../model/type/ProductSummary';
import ProductModel from "../model/ProductModel";

const ProductListViewModel = () => {
  const navigation = useNavigation();
  const [products, setProducts] = useState([] as ProductSummary[]);

  const fetchProducts = (cached: boolean = true): void => {
    ProductModel().fetchProducts(cached)
      .then((products: ProductSummary[]) => setProducts(products))
      .catch(error => showError(error));
  };

  const deleteProduct = (productId: string): void => {
    ProductModel().deleteProduct(productId)
      .then(() => showToast('Product successfully deleted'))
      .then(() => fetchProducts(false))
      .catch(error => showError(error));
  }

  const showDetails = (productId: string): void => {
    navigation.navigate('ProductDetails', { action: 'PREVIEW', productId: productId });
  };

  const editProduct = (productId: string): void => {
    navigation.navigate('ProductDetails', { action: 'UPDATE', productId: productId });
  };

  const addProduct = (): void => {
    navigation.navigate('ProductDetails', { action: 'CREATE' });
  };

  const showToast = (message: string): any => {
    ToastAndroid.show(message, ToastAndroid.SHORT);
  };

  const showError = (message: string): any => {
    ToastAndroid.show(message, ToastAndroid.LONG);
  }

  return {
    products,
    showToast,
    fetchProducts: fetchProducts,
    deleteProduct,
    showDetails,
    editProduct,
    addProduct
  };

};

export default ProductListViewModel;