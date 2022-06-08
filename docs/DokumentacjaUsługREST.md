# Usługi
Wszystkie endpoint'y przy tej aplikacji zostały wykonane zgodnie z obowiązującymi zasadami architektury REST. W sumie stworzyliśmy 12 usług z tego 5 z nich obsługuje operacje związane bezpośrednio z użytkownikiem natomiast pozostałe 7 obsługuje ankiety. Dostęp do możlwości wykonania zadania danej usługi jest organizowany poprzez role przydzielane każdemu użytkownikowi. Poniżej opiszę w skrócie przeznaczenie każdej z usług. W przypadku błędnych informacji zwracany jest stosowny kod błędu z przyczyną w wiadomości. Cały wykaz usług oraz ich konfiguracji jest również dostępny pod adresem naszego REST Api w narzędziu swagger ui. (url-aplikacji/swagger-ui)
## Polls
### GET:
- /api/polls/{id} - pozwala każdemu zalogowanemu użytkownikowi na pobiernie wszystkich informacji o danej ankiecie(nazwa ankiety oraz jej pytania) o podanym id w url.
- /api/polls - pozwala każdemu zalogowanemu użytkownikowi pobranie listy wszystkich aktualnie znajdujących się ankiet w bazie danych w formie id oraz nazwy ankiety dla kazdej z nich.
- /api/polls/{id}/answers - pozwala każdemu zalogowanemu użytkownikowi na pobranie z bazy danych aktualnych wszystkich odpowiedzi na pytania należące do ankiety o podanym id.
### POST:
- /api/polls - pozwala adminowi na dodanie nowej ankiety o sprecyzowanych pytaniach do bazy danych.
- /api/polls/{id}/answers - pozwala każdemu zalogowanemu użytkownikowi na dodanie odpowiedzi na konkretne pytanie do ankiety po podanym id.
### DELETE:
- /api/polls/{id} - pozwala adminowi na usunięcie ankiety o podanym id z bazy danych aplikacji.
### PUT:
- /api/polls/{id} - pozwala adminowi na modyfikacje nazwy ankiety oraz pytań przypisanych do niej w bazie danych aplikacji.
## Users
### GET:
- /api/users/{id} - pozwala na pobranie przez admina infomracji o użytkowniku o podanym id lub zwykłego użytkownika swoich infromacji o koncie.
### PUT:
- /api/users/{id} - pozwala na modyfikacje danych użytkownika o podanym id przez admina lub modyfikacje danych swojego konta zwykłego użytkownika.
### POST:
- /api/users - pozwala na rejestracje użytkownika w oparciu o przesłane dane w treści żądania.
- /api/users/login - pozwala na logowanie użytkownika w opraciu o podany username oraz password.
### DELETE:
- /api/users/{id} - pozwala adminowi na usunięcie danego użytkownika z bazy danych wraz z jego wszystkimi informacjami, ankietami oraz odpowiedziami.
### Spring Security
Nasza aplikacja używa Spring Security skonfigurowanego z biblioteką JWT w celu sprawnej autoryzacji oraz autentykacji. Każda próba logowania jest przechwycana przez service z Spring Security który sprawdza czy podane dane użytkownika są poprawne z tymi zapisanymi w bazie danych. Jeżeli są poprawne to przy odpowiedzi logowania potencjalna aplikacja webowa otrzyma token JWT w którym zaszyfrowane są dane o użytkowniku: jego username, jego rola, oraz czas wygaśniecia tokenu. Na podstawie tego czy token istnieje w kolejnych żądaniach oraz czy rola która jest w tokenie pozwala na to, realizowany jest dostęp do innych usług oraz ich prawidłowego wykonania. Dzieje się to za sprawą filtru który automatycznie przechwyca każde żądanie i sprawdza token JWT. W naszej aplikacji istnieją dwie zapisane role Admin oraz User. Admin jest w stanie wykonywać wszystkie operacje natomiast User tylko te które nie modyfikują lub usuwają zasoby z nim nie związane.
