# Opis komponentów

Aplikacja została zaimplementowana w języku TypeScript, z wykorzystaniem frameworka Angular. W celu utworzenia w miare przystępnie wyglądającego interfejsu użytkownika niewielkim kosztem sylowania elementów HTML skorzystano z biblioteki Bootsrap.

- ## `ProductListComponent`
  - Komponent odpowiedzialny za dynamiczne rysowanie komponentów "ProductCardComponent", na podstawie otrzymanej z serwera listy produktów
  - Punk wejściowy do aplikacji, jest to domyślny komponent, który wyświetlany jest przy starcie, bądź w momencie gdy aplikacja nie będzie w stanie prawidłowo rozwiązać zadanej ścieżki.

- ## `ProductCardComponent`
  - Komponent będący 'kafelkiek', przedstawiający podstawowe informacje o produkcie takie jak nazwa oraz cena.
  - Zawiera dwa przyciski 'Edit' oraz 'Delete', pozwalające odpowiednio: edytować wybrany produkt (nastąpi przekierowanie do formularza z produktem) oraz usuwać wskazany produkt
  - Dodatkowo kliknięcie na kafelek poza wspomnianymi przyciskami przekierowuje na stronę pozwalającą podejrzeć detale produktu, aczkolwiek bez możliwości edycji.

- ## `ProductFormComponent`
  - Najbardziej skomplikowany, zawierający najwięcej logiki biznesowej, komponent w projekcie
  - Jest to uniwersalny formularz, umożliwiający tworzenie nowego, edycję instniejącego bądź podgląd detali produktu.
  - W przypadku gdy komponent ten jest wyświetlony w trybie podglądu, dodatkowo zawiera przycisk 'Delete' umożliwiający usunięcie aktualnie przeglądanego produktu

- ## `InputErrorComponent`
  - Komponent odpowiedzialny za wyświetlanie błędów walidacji, jeśli takowe zostaną wykryte

- ## `ErrorPageComponent`
  - Komponent wyświetlany na ekranie w przypadku wystąpienia problemów z połączeniem do serwera.

- ## `ProducService`
  - Serwis odpowiedzialny za komunikację z serwerem
  - Udostępnia prosty interfejs umożliwiający:
    - pobieranie listy produktów
    - pobieranie szczegółów wybranego produktu
    - tworzenie nowego produktu
    - edytowanie istniejącego produktu
    - usuwanie wskazanego produktu
  - W przypadku wystąpienia problemów z łącznością serwera przekierowywyje na stronę 'error-page'

- ### Klasy znajdujące się w pakiecie `model`
  - Reprezentują DTO, wykorzystywane są do czystej i ustrukturyzowanej wymiany danych pomiędzy komponentami
___

Każdy komponent Angulara, podzielony jest na 4 pliki:
- plik HTML będący szablonem który jest rysoway użytkownikowi
- plik CSS w którym znajdują się wszystkie style dla komponentu
- plik TypesScript/JavaScript będący kontrolerem realizującym logikę biznesową
- plik 'Spec', który zawiera testy dla danego komponentu
