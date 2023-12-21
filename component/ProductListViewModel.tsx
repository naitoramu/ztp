import {useNavigation} from '@react-navigation/native';
import {useState} from 'react';
import {ToastAndroid} from 'react-native';
import {ProductSummaryDto} from '../model/type/ProductSummaryDto';

const ProductListViewModel = () => {
  const API_URL: string = 'http://192.168.0.186:8080';
  const navigation = useNavigation();
  const [products, setProducts] = useState<ProductSummaryDto>([] as ProductSummaryDto[]);

  const showToast = (message: string): any => {
    ToastAndroid.show(message, ToastAndroid.SHORT);
  };

  const fetchProducts = (): void => {
    fetch(`${API_URL}/v1/products`)
      .then(response => response.json())
      .then((data: ProductSummaryDto[]) => setProducts(data))
      .catch(error => console.error('Error fetching products:', error));
  };

  const deleteProduct = (productId: string): void => {
    fetch(`${API_URL}/v1/products/${productId}`, { method: 'DELETE' })
      .then(response => (response.status === 204 ? showToast('Product successfully deleted') : showToast('Cannot delete product')))
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
    fetchProducts,
    deleteProduct,
    showDetails,
    editProduct,
    addProduct
  };

};

export default ProductListViewModel;