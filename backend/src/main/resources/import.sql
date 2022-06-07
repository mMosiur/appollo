INSERT INTO USERS (ID, EMAIL, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME) VALUES ( 1,'eloniasty@gmail.com','Elon','Musk','$2a$12$6wOkrXIDIa8FcyeIjwz0JeclIM7gVe.pftxjw3esD2UkbSdAGKbl2','rocketman' ); //rakieta
INSERT INTO USERS (ID, EMAIL, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME) VALUES ( 2,'plebek@gmail.com','Radek','Borowicz','$2a$12$7dnq8QZ3qFVx3ZRAYwl9au4YjynunnIw4yTkMfodCS25IuXst6SY.','plebek' ); //help123
INSERT INTO ROLE (ID, DESCRIPTION, NAME) VALUES ( 1,'Rola super uzytkownika', 'ADMIN' );
INSERT INTO ROLE (ID, DESCRIPTION, NAME) VALUES ( 2,'Rola plebsu', 'USER' );
INSERT INTO USERS_ROLES (USER_ID, ROLE_ID) VALUES( 1, 2 );
INSERT INTO USERS_ROLES (USER_ID, ROLE_ID) VALUES(2, 1 );
INSERT INTO USERS_ROLES (USER_ID, ROLE_ID) VALUES( 1, 1 );