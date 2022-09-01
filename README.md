# apPOLLo

## Status CI/CD

[![Continuous Integration](https://github.com/mMosiur/appollo/actions/workflows/ci.yml/badge.svg)](https://github.com/mMosiur/appollo/actions/workflows/ci.yml)

[![Deploy backend](https://github.com/mMosiur/appollo/actions/workflows/deploy-backend.yml/badge.svg)](https://github.com/mMosiur/appollo/actions/workflows/deploy-backend.yml)

[![Deploy frontend](https://github.com/mMosiur/appollo/actions/workflows/deploy-frontend.yml/badge.svg)](https://github.com/mMosiur/appollo/actions/workflows/deploy-frontend.yml)
:arrow_forward:
[![pages-build-deployment](https://github.com/mMosiur/appollo/actions/workflows/pages/pages-build-deployment/badge.svg)](https://github.com/mMosiur/appollo/actions/workflows/pages/pages-build-deployment)

## Wstęp

### Opis

Jest to aplikacja zaliczeniowa na przedmiot "Systemy klasy Enterprise" w ramach
projektu zaliczeniowego. Aplikacja jest oparta na Spring Framework oraz
REST Api. Początkowo pracowaliśmy na bazie h2 która była zapisywana do pliku/pamięci.
Aktualnie posiadamy zdeployowaną aplikacje jak i bazę Postgres [na serwerach Azure](https://github.com/mMosiur/appollo/deployments/activity_log?environment=Production). Dodatkowo
stworzyliśmy aplikację webową wykorzystującą Angular w celach prezentacji naszego REST Api,
która z kolei deployowana jest na [Github Pages](https://github.com/mMosiur/appollo/deployments/activity_log?environment=github-pages).
Cały kontrolowany proces tworzenia aplikacji odbywał się za pośrednictwem Github z skonfigurowanym
CI oraz CD oraz wykorzystując system zarządzania gałęziami Gitflow. CI zostało również zintegrowane z testami jednostkowymi do konwerterów i serwisów z naszej aplikacji, a także testami jednostkowymi aplikacji Angularowej.

### Przeznaczenie działania naszego REST Api

Głównym zadaniem naszego Api jest obsługa  potencjalnej aplikacji webowej której zadaniem byłoby tworzenie ankiet, wypełnianie ich i tym samym dostarczenie informacji o podanych odpowiedziach do każdej ankiety. Wszystko jest możliwe dla podstawowych użytkowników zalogowanych którzy mogą wypełniać ankiety zaś administratorzy aplikacji są w stanie tworzyć nowe ankiety, modyfikować je bąć też usuwać.

Więcej szczegółowych informacji o naszej aplikacji w folderze z dokumentacją.

## Środowiska produkcyjne

- ~~Frontend zdeployowany do Github Pages~~

- ~~Backend zdeployowany do Azure Web Apps~~

- ~~Baza danych (PostgreSQL) zdeployowana do Azure~~

## Środowisko developerskie

### Backend

Backend napisany jest w **Java 11** z użyciem frameworku **Spring Boot**.

Po przejściu do folderu źródeł backendu:

``` bash
cd backend
```

Można zbudować projekt za pomocą:

``` bash
# Dla ustawień dev (lokalna baza danych):
mvn clean package
# Dla ustawień prod (baza PostgreSQL na Azure, NOTE - wymagane hasło w zmiennej środowiskowe):
mvn clean package -Denvironment=prod
```

Budowanie projektu przeprowadza również zawarte w nim testy jednostkowe.

Następnie można go uruchomić:

``` bash
# Dla ustawień dev (lokalna baza danych):
mvn spring-boot:run
# Dla ustawień prod (baza PostgreSQL na Azure, NOTE - wymagane hasło w zmiennej środowiskowe):
mvn spring-boot:run -Denvironment=prod
# Można także wykorzystać wcześniej zbudowany plik .jar
java -jar ./target/*.jar
```

### Frontend

Frontend jest napisany w **TypeScript** z użyciem frameworku **Angular**.

Po przejściu do folderu źródeł backendu:

``` bash
cd frontend
```

należy zainstalować zdefiniowane tam paczki za pomocą:

``` bash
npm install
```

Następnie można uruchomić dewelopersko projekt za pomocą:

``` bash
npm start
```

Lub można również budować projekt do plików statycznych za pomocą:

``` bash
npm run build
```

W międzyczasie można także uruchomić testy jednostkowe za pomocą:

``` bash
npm run test
```

Więcej informacji [w podfolderze `frontend`](./frontend/)
