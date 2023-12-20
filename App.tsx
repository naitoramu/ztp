import React from 'react';
import { StyleSheet, View, Platform, StatusBar } from 'react-native';

import { PaperProvider } from 'react-native-paper';
import ProductList from './component/ProductList';
import { SafeAreaView } from 'react-native-safe-area-context';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import ProductDetails from './component/ProductDetails';

const Stack = createNativeStackNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <SafeAreaView style={styles.androidSafeArea}>
        <PaperProvider>
          <Stack.Navigator>
            <Stack.Screen
              name="ProductList"
              component={ProductList}
              options={{ title: 'Product List' }}
            />
            <Stack.Screen
              name="ProductDetails"
              component={ProductDetails}
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
    // backgroundColor: "white",
    // paddingTop: Platform.OS === "android" ? StatusBar.currentHeight : 0
  }
});
