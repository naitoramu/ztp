# Opis komponentów

 - ## `ProductListView` oraz `ProductDetailsView`
   - Warstwa aplikacji odpowiedzialna za interfejs użytkownika i prezentację danych
   - Możliwie najprostsze componenty bez logiki biznesowej
   - Przechwytują akcje wykonywane przez użytkownika i wywołują odpowiednie metody z wartswy domenowej
   - Napisane przy wykorzystaniu biblioteki React Native
   - `ProductListView` wyświetla listę produktów wraz z podstawowymi informacjami, natomiast 
   - `ProductDetailsView` wyświetla widok zawierający szczegółowe informacje o produckcie a także umożliwia tworzenie nowego oraz edycję istniejącego produktu.

 - ## `ProductListViewMode` oraz `ProductDetailsViewModel`
   - Warstwa domenowa odpowiedzialna, za logikę biznesową aplikacji
   - Zawiera reguły, które umożliwają wyświetlenie prawidłowych danych oraz wykonywanie określonych akcji
   - Komunikują się z warstwą infrastrukturalną, pobierają od niej dane, a także przekazują dane wprowadzone przez użytkownika
   - W przypadku pojawienia się błędów wyświetlają o nich komunikaty

 - ## `ProductModel`
   - Warstwa infrastrukturalna odpowiedzialna za połączenie z serwerem i zarządzająca dostępem do danych
   - Komunikuje się z serwerem poprzez zapytania i odpowiedzi HTTP
   - Pobiera dane z serwera i przekazuje je w "dół" do warstwy domenowej
   - Otrzymuje dane z warstwy domenowej i wysyła je do backendu
   - Serwuje dane z pamięci podręcznej, a jeżeli ich tam nie znajdzie, to wykonuje odpowiednie zapytania do serwera

 - ### Komponenty znajdujące się w pakiecie `model.type`
   - Klasy pomocnicze, służące do przechowywania i przekazywania ustrukturyzowanych danych