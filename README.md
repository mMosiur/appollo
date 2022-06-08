# Wstęp
 
Jest to aplikacja zaliczeniowa na przedmiot "Systemy klasy Enterprise" w ramach
projektu zaliczeniowego. Aplikacja jest oparta na Spring Framework oraz 
REST Api. Początkowo pracowaliśmy na bazie h2 która była zapisywana do pliku/pamięci.
Aktualnie posiadamy zdeployowaną aplikacje jak i bazę Postgres na serwerach Azure. Dodatkowo
stworzyliśmy aplikację webową wykorzystującą Angular w celach prezentacji naszego REST Api.
Cały kontrolowany proces tworzenia aplikacji odbywał się za pośrednictwem Github z skonfigurowanym
CI oraz CD oraz wykorzystując system zarządzania gałęziami Gitflow. CI zostało również zintegrowane z testami jednostkowymi do konwerterów i serwisów z naszej aplikacji.

# Przeznaczenie działania naszego REST Api
Głównym zadaniem naszego Api jest obsługa  potencjalnej aplikacji webowej której zadaniem byłoby tworzenie ankiet, wypełnianie ich i tym samym dostarczenie informacji o podanych odpowiedziach do każdej ankiety. Wszystko jest możliwe dla podstawowych użytkowników zalogowanych którzy mogą wypełniać ankiety zaś administratorzy aplikacji są w stanie tworzyć nowe ankiety, modyfikować je bąć też usuwać.

Więcej szczegółowych informacji o naszej aplikacji w folderze z dokumentacją.
