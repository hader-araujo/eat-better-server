
CREATE TABLE USERS(
   ID   INT              NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   LOGIN VARCHAR (20)    NOT NULL,
   NAME VARCHAR (60)     NOT NULL
);

INSERT INTO USERS (LOGIN, NAME) VALUES('ADMIN', 'ADMINISTRATOR NAME');