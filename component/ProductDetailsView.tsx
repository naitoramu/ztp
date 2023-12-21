import {NavigationProp, RouteProp} from '@react-navigation/native';
import React, {useEffect} from 'react';
import {View} from 'react-native';
import {Button, TextInput} from 'react-native-paper';
import ProductDetailsViewModel from "./ProductDetailsViewModel";

interface ProductListProps {
  navigation: NavigationProp<any>;
  route: RouteProp<{
    params: {
      productId: string,
      action: string
    }
  }>
}

const ProductDetailsView: React.FC<ProductListProps> = ({ route }) => {

  const {
    product,
    onInit,
    onNameChange,
    onDescriptionChange,
    onPriceChange,
    onAvailableQuantityChange,
    onSubmit,
    isEditable
  } = ProductDetailsViewModel(route.params.productId, route.params.action);

  useEffect(() => {
    onInit();
  });

  return (
    <View>
      <TextInput
        label="Name"
        value={product.name}
        editable={isEditable()}
        onChangeText={text => onNameChange(text)}
      />
      <TextInput
        label="Description"
        value={product.description}
        multiline={true}
        editable={isEditable()}
        onChangeText={text => onDescriptionChange(text)}
      />
      <TextInput
        label="Price"
        value={product.price?.toString()}
        editable={isEditable()}
        onChangeText={text => onPriceChange(+text)}
      />
      <TextInput
        label="Available quantity"
        value={product.availableQuantity?.toString()}
        editable={isEditable()}
        onChangeText={text => onAvailableQuantityChange(+text)}
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

export default ProductDetailsView;