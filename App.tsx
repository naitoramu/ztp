import React from 'react';
import {StyleSheet} from 'react-native';

import {PaperProvider} from 'react-native-paper';
import {SafeAreaView} from 'react-native-safe-area-context';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import ProductListView from './src/view/ProductListView';
import ProductDetailsView from './src/view/ProductDetailsView';

const Stack = createNativeStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <SafeAreaView style={styles.androidSafeArea}>
        <PaperProvider>
          <Stack.Navigator>
            <Stack.Screen
              name="ProductList"
              component={ProductListView}
              options={{ title: 'Product List' }}
            />
            <Stack.Screen
              name="ProductDetails"
              component={ProductDetailsView}
              options={{ title: 'Product Details' }}
            />
          </Stack.Navigator>
        </PaperProvider>
      </SafeAreaView>
    </NavigationContainer>

  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff'
  },
  androidSafeArea: {
    flex: 1,
  }
});
