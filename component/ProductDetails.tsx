import { NavigationProp, RouteProp } from '@react-navigation/native';
import React, { useEffect, useState } from 'react';
import { ToastAndroid, View } from 'react-native';
import { Button, TextInput } from 'react-native-paper';
import { ProductDto } from '../dto/ProductDto';

interface ProductListProps {
  navigation: NavigationProp<any>;
  route: RouteProp<{
    params: {
      product: ProductDto,
      productId: string,
      action: string
    }
  }>
}

const ProductDetails: React.FC<ProductListProps> = ({ route, navigation }) => {
  const { productId, action } = route.params;
  const [product, setProduct] = useState<ProductDto>({
    name: '',
    description: '',
    price: 0,
    availableQuantity: 0,
  } as ProductDto);

  const isEditable = (): boolean => {
    return action === 'CREATE' || action === 'UPDATE';
  };


  const fetchProduct = (productId: string): void => {
    console.log('Fetching products');
    fetch(`http://192.168.0.186:8080/v1/products/${productId}`)
      .then(response => response.json())
      .then((data: ProductDto) => setProduct(data))
      .catch(error => console.error(`Error fetching product with ID: ${productId}`, error));
  };

  const updateProduct = (): void => {
    console.log('Updating product');
    fetch(`http://192.168.0.186:8080/v1/products/${product.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(product),
    })
      .then(response => (response.status === 200
        ? navigation.navigate('ProductList')
        : response.json().then(data => handleErrorResponse(data))))
      .catch(error => console.error(`Error updating product with ID: ${product.id}`, error));
  };

  const createProduct = (): void => {
    console.log('Creating product', JSON.stringify(product));
    fetch(`http://192.168.0.186:8080/v1/products`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(product),
    })
      .then(response => (response.status === 201
        ? navigation.navigate('ProductList')
        : response.json().then(data => handleErrorResponse(data))))
      .catch(error => console.error(`Error creating product with ID: ${product}`, error));
  };

  const onSubmit = (): void => {
    console.log('Submitting form');
    switch (action) {
      case 'CREATE':
        console.log('Action:', action);
        createProduct();
        break;
      case 'UPDATE':
        console.log('Action:', action);
        updateProduct();
        break;
      default:
        throw Error(`Unknown action: ${action}`);
    }
  };

  useEffect(() => {
    if (action !== 'CREATE') {
      fetchProduct(productId);
    }
  }, [productId, action]);

  return (
    <View>
      <TextInput
        label="Name"
        value={product.name}
        editable={isEditable()}
        onChangeText={text => setProduct({ ...product, name: text } as ProductDto)}
      />
      <TextInput
        label="Description"
        value={product.description}
        multiline={true}
        editable={isEditable()}
        onChangeText={text => setProduct({ ...product, description: text } as ProductDto)}
      />
      <TextInput
        label="Price"
        value={product.price?.toString()}
        editable={isEditable()}
        onChangeText={text => setProduct({ ...product, price: +text } as ProductDto)}
      />
      <TextInput
        label="Available quantity"
        value={product.availableQuantity?.toString()}
        editable={isEditable()}
        onChangeText={text => setProduct({ ...product, availableQuantity: +text } as ProductDto)}
      />
      {isEditable() ? <Button
        icon="check"
        buttonColor="green"
        textColor="black"
        onPress={onSubmit}
        children={undefined}
      /> : ''}
    </View>
  );
};

export default ProductDetails;

type Violation = {
  field: string;
  message: string;
};

function handleErrorResponse(data: any): any {
  const violations: string = data.violations
    .map((violation: Violation) => `${violation.field}: ${violation.message}`)
    .join('\n');

  ToastAndroid.show(`${data.details}\n${violations}`, ToastAndroid.LONG);
}
