# Opis komponentów

## `ProductServlet` oraz `OrderServlet`
 - Dopasowywanie przychodzących zapytań HTTP i wywoływanie odpowiednich metod kontrolera
 - Ekstrakcja query parametrów
 - Deserializacja treści zapytań oraz serializacja treści odpowiedzi
 - Ustawianie "status code" oraz nagłówków odpowiedzi takich "Content-Type"
 - Przechwytywanie wyjątków i translację ich na odpowiedzi

## `ProductController` oraz `OrderController`
 - Warstwa abstrakcji odzielająca obsługę żądań od serwisów realizujących logikę biznesową
 - Napisane w taki sposób aby umożliwić łatwe, ewentualne wdrożenie do aplikacji frameworku takiego jak Spring Boot
 - Walidowanie przekazanych zdeserializowanych już treści żądania

## Serwisy
 - Warstwa abstrakcji oddzielająca kontrolery od repozytorów
 - Realizują logikę biznesową aplikacji

### `ProductService`
Udostępnia metody umożliwiające:
 - pobieranie listy dostępnych produktów
 - pobieranie pojedynczego produktu na bazie jego ID
 - edycję produktu ze wskazanym ID
 - tworzenie nowego produktu
 - usuwanie istniejącego produktu na podstawie jego ID

### `OrderService`
Udostępnia metody umożliwiające:
 - pobieranie wszystkich zamówień
 - pobieranie pojedynczego zamówienia na podstawie jego ID
 - tworzenie nowego zamówienia

Podczas tworzenia nowego zamówienia waliduje, czy ID produktów zamówinia istnieją w bazie danych, a jeżeli nie to wyrzuca wyjątek.
Sprawdza również czy ilość produktów na zamówieniu nie przekracza ilości dostępnych produktów w bazie danych, jeżeli tak to wyrzuca wyjątek.
Dodatkowo sumuje koszty wszystkich produktów na zamówieniu oraz kosztów dostawy.

## Repozytoria
Oddzielają serwisy od bazy danych i odpoiwadają za komunikację i pobieranie danych z bazy.

### `ProductRepository`
Udostępnia metody pozwalające: 
 - pobrać pojedynczy produk ze wskazanym ID, którego wartość w kolumnie `is_deleted` wynosi `false`
 - pobrać wszystkie produkty, których wartość w kolumnie `is_deleted` wynosi `false`
 - pobrać listę zawierającą ID produktu wraz z jego dostępną ilością oraz ceną
 - stworzyć nowy produkt
 - edytować istniejący produkt ze wskazanym ID
 - edytować dostępną ilość produktów ze wskazanymi ID
 - usunąć (soft delete) produkt w bazie, poprzez ustawienie jego wartości w kolumnie `is_deleted` na `true`

### `OrderRepository`
Udostępnia metody pozwalające:
 - pobrać wszystkie zamówienia
 - pobrać pojedyncze zamówienie ze wskazanym ID
 - tworzyć nowe zamówienie

## Wzorzec Strategii
### `DeliveryStrategy`
Konieczne do utworzenia noweg zamówienia jest określenie przez użytkownika metody dostawy z dwóch dotępnych: Pocztą lub Kurierem.
Sposób dostawy różni się kosztem, który wyznaczany jest na podstawie adresu dostawy. Utworzone zostały dwie klasy `Postal` oraz `Courier`, które implementują interfejs `DeliveryStrategy`.
Podczas mapowania obiektu DTO zawierającego informację o nowym zamówieniu, na obiekt domenowy wywoływana jest metoda `DeliveryStrategy.fromMethod()`, która przyjmuje jako parametr wartość Enuma określającego sposób dostawy wybrany przez klienta i na jej podstawie zwraca instancję klasy implementującej interfejs `DeliveryStrategy`.

## Servlet Filter
### `CorsFilter`
W aplikacji został zaimplementowany `CorsFilter`. Filtr ten przechwytuje każde żądanie HTTP i ustawia w jego odpowiedzi trzy nagłówki:
 - "Access-Control-Allow-Origin": "*"
 - "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, OPTIONS"
 - "Access-Control-Allow-Headers": "Content-Type, Authorization"

## Pozostałe
 - Komponenty znajdujące się w pakiecie `model.dto` służą do translacji treści żądań na obiekty w języku Java oraz w drugą stronę.
 - Komponenty znajdujące się w pakiecie `model.domain` służą do reprezentowania danych otrzymanych od użytkownika lub z bazy danych i wykonywanie na nich operacji związanych z logiką biznesową.
 - Komponenty znajdujące się w pakiecie `model.mapper` służą do translacji obiektów pomiędzy bazą danych, serwisami oraz kontrolerami.
 - Komponenty znajdujące się w pakiecie `problem` wykorzystywane są do tworzenia własnych wyjątków i przekazywania do nich informacji, które następnie zwracane są do użytkownika.
 - Komponenty znajdujące się w pakiecie `util` oraz `api.servlet.util` to klasy pomocnicze