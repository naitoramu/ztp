# Opis komponentów

## `ProductController`
 - Warstwa abstrakcji odzielająca obsługę żądań od serwisów realizujących logikę biznesową
 - Ekstrakcja parametrów oraz treści żądania z zapytań
 - Zwracanie odpowiedzi na żądania
 - Walidowanie danych przekazanych w żądaniach

## `ProductService`
Udostępnia metody umożliwiające:
 - pobieranie listy dostępnych produktów
 - pobieranie pojedynczego produktu na podstawie jego ID
 - tworzenie nowego produktu
 - edycję produktu ze wskazanym ID
 - usuwanie istniejącego produktu ze wskazanym ID
 - pobranie logów audytowych dla produktu ze wskazanym ID

Poza podstawowymi operacjami CRUD na produktach, podczas tworzenia nowego, modyfikacji, bądź usuwania istniejącego produktu, dodaje do bazy wpisy audytowe, z odpowiednim typem operacji:
 - CREATE
 - UPDATE
 - DELETE

Następnie poprzez endpoint: `/v1/products/{productId}/audit` możliwe jest pobranie wszystkich wpisów audytowys dla produktu ze wskazanym `productId`.

## `ProductRepository`
Odpowiada za komunikację z bazą danych MongoDB. Udostępnia metody pozwalające: 
 - pobrać pojedynczy produk ze wskazanym ID
 - pobrać wszystkie istniejące produkty
 - stworzyć nowy produkt
 - edytować istniejący produkt ze wskazanym ID
 - pobrać wpisy audytowe dla produktu ze wskazanym ID

## Pozostałe
- Komponenty znajdujące się w pakiecie `model.dto` służą do translacji treści żądań na obiekty w języku Java oraz w drugą stronę.
- Komponenty znajdujące się w pakiecie `model.domain` służą do reprezentowania danych otrzymanych od użytkownika lub z bazy danych i wykonywanie na nich operacji związanych z logiką biznesową.
- Komponenty znajdujące się w pakiecie `model.mapper` służą do translacji obiektów pomiędzy bazą danych, serwisami oraz kontrolerami.
- Komponenty znajdujące się w pakiecie `problem` wykorzystywane są do tworzenia własnych wyjątków i przekazywania do nich informacji, które następnie zwracane są do użytkownika.

# Testy BDD
W celu implementacji testów BDD, wykorzystano popularną w języku Java bibliotekę **Cucumber**.

## `CucumberIntegrationTest`
Klasa ta zawiera podstawową konfigurację biblioteki *Cucumber*, oraz wskazuje lokalizację plików `*.feature`, zawierających scenariusze testowe.

### `ProductCrud` oraz `ProductAudit`
Pliki te zawierają scenariusze testowe odpowiednio dla testowania podstawowych operacji CRUD wykorywanych na produktach, oraz sprawdzające poprawność dodawania wpisów audytowych podczas wykonywania operacji na produktach.
Opisują one słownie kroki jakie należy w teście wykonać oraz oczekiwane rezultaty.

### `ProductSteps`
Klasa ta zawiera implementację wszystkich kroków, które znajdują się w plikach `ProductCrud` oraz `ProductAudit`. Tutaj znajduje się cała logika testów oraz wszelkie asercje.
Przed i po każdym teście usuwane są dane znajdujące się w bazie, tak aby żaden z testów nie był zależny od innego. Wszelkie tworzenie danych testowych w bazy danych odbywa się poprzez API aplikacji (zapytania HTTP).