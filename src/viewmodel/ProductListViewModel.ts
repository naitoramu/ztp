import {useNavigation} from '@react-navigation/native';
import {useState} from 'react';
import {ToastAndroid} from 'react-native';
import {ProductSummary} from '../model/type/ProductSummary';
import ProductModel from "../model/ProductModel";

const ProductListViewModel = () => {
  const navigation = useNavigation();
  const [products, setProducts] = useState([] as ProductSummary[]);

  const showToast = (message: string): any => {
    ToastAndroid.show(message, ToastAndroid.SHORT);
  };

  const fetchProducts = (): void => {
    ProductModel().fetchProducts()
      .then(response => response.json())
      .then((data: ProductSummary[]) => setProducts(data))
      .catch(error => console.error('Error fetching products:', error));
  };

  const deleteProduct = (productId: string): void => {
    ProductModel().deleteProduct(productId)
      .then(response => (response.status === 204
        ? showToast('Product successfully deleted')
        : showToast('Cannot delete product')))
      .then(fetchProducts)
      .catch(error => console.error('Error deleting products:', error));
  };

  const showDetails = (productId: string): void => {
    navigation.navigate('ProductDetails', { action: 'PREVIEW', productId: productId });
  };

  const editProduct = (productId: string): void => {
    navigation.navigate('ProductDetails', { action: 'UPDATE', productId: productId });
  };

  const addProduct = (): void => {
    navigation.navigate('ProductDetails', { action: 'CREATE' });
  };

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