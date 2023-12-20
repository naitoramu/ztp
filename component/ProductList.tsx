import { NavigationProp, useFocusEffect } from '@react-navigation/native';
import React, { useEffect, useState } from 'react';
import { ScrollView, StyleSheet, ToastAndroid } from 'react-native';
import { DataTable, IconButton } from 'react-native-paper';
import { ProductSummaryDto } from '../dto/ProductSummaryDto';

interface ProductListProps {
  navigation: NavigationProp<any>;
}

const ProductList: React.FC<ProductListProps> = ({ navigation }) => {
  const [products, setProducts] = useState<ProductSummaryDto[]>([]);

  const showToast = (message: string): any => {
    ToastAndroid.show(message, ToastAndroid.SHORT);
  };

  const fetchProducts = (): void => {
    fetch('http://192.168.0.186:8080/v1/products')
      .then(response => response.json())
      .then((data: ProductSummaryDto[]) => setProducts(data))
      .catch(error => console.error('Error fetching products:', error));
  };

  const deleteProduct = (productId: string): void => {
    fetch(`http://192.168.0.186:8080/v1/products/${productId}`, { method: 'DELETE' })
      .then(response => (response.status === 204 ? showToast('Product successfully deleted') : showToast('Cannot delete product')))
      .then(fetchProducts)
      .catch(error => console.error('Error deleting products:', error));
  };

  const showDetails = (productId: string): void => {
    console.log('Preview');
    navigation.navigate('ProductDetails', { action: 'PREVIEW', productId: productId });
  };

  const editProduct = (productId: string): void => {
    console.log('Edit');
    navigation.navigate('ProductDetails', { action: 'UPDATE', productId: productId });
  };

  const addProduct = (): void => {
    console.log('Add');
    navigation.navigate('ProductDetails', { action: 'CREATE' });
  };

  useFocusEffect(() => {
    fetchProducts();
  });

  useEffect(() => {
    navigation.setOptions({
      headerRight: () => <IconButton icon="plus-box" iconColor="green" onPress={addProduct} />,
    });
  }, [navigation]);

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <DataTable>
        <DataTable.Header>
          <DataTable.Title style={styles.nameColumn}>Name</DataTable.Title>
          <DataTable.Title style={styles.priceColumn} numeric>
            Price
          </DataTable.Title>
          <DataTable.Title style={styles.iconColumn}> </DataTable.Title>
          <DataTable.Title style={styles.iconColumn}> </DataTable.Title>
          <DataTable.Title style={styles.iconColumn}> </DataTable.Title>
        </DataTable.Header>

        {products.map(product => (
          <DataTable.Row key={product.id} onPress={() => showDetails(product.id)}>
            <DataTable.Cell style={styles.nameColumn}>{product.name}</DataTable.Cell>
            <DataTable.Cell style={styles.priceColumn}>{product.price}</DataTable.Cell>
            <DataTable.Cell style={styles.iconColumn}>
              <IconButton icon="magnify" iconColor="white" onPress={() => showDetails(product.id)} />
            </DataTable.Cell>
            <DataTable.Cell style={styles.iconColumn}>
              <IconButton icon="square-edit-outline" iconColor="blue" onPress={() => editProduct(product.id)} />
            </DataTable.Cell>
            <DataTable.Cell style={styles.iconColumn}>
              <IconButton icon="delete" iconColor="red" onPress={() => deleteProduct(product.id)} />
            </DataTable.Cell>
          </DataTable.Row>
        ))}
      </DataTable>
    </ScrollView>
  );
};

export default ProductList;

const styles = StyleSheet.create({
  container: {
    alignItems: 'center',
    paddingVertical: 15,
  },
  nameColumn: {
    flex: 6,
  },
  priceColumn: {
    justifyContent: 'flex-end',
    flex: 3,
    paddingRight: 30,
  },
  iconColumn: {
    justifyContent: 'flex-end',
    flex: 1,
    paddingHorizontal: 5,
  },
});
