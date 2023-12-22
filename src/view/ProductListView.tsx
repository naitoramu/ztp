import {NavigationProp, useFocusEffect} from '@react-navigation/native';
import React, {useEffect} from 'react';
import {ScrollView, StyleSheet} from 'react-native';
import {DataTable, IconButton} from 'react-native-paper';
import ProductListViewModel from '../viewmodel/ProductListViewModel';

interface ProductListProps {
  navigation: NavigationProp<any>;
}

const ProductListView: React.FC<ProductListProps> = ({ navigation }) => {
  const {
    products,
    fetchProducts,
    addProduct,
    editProduct,
    deleteProduct,
    showDetails
  } = ProductListViewModel();

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

export default ProductListView;

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
