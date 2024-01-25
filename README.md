# Project 3
Simple mobile application written in React Native that implements MVVM architecture

### Usage
In order to launch the application it is necessary to have npm, npx and expo go installed

Install dependencies:
```
npm install
```

Run the expo server on your local machine:
```
npx expo start
```

Then on your mobile device run Expo Go application, and scan generated QR code. Next change URL in the `src.model.ProductModel.ts` file to the IP address of the backend API.
```TypeScript
  const API_URL: string = 'http://<your-backend-server-ip>:8080';
```
