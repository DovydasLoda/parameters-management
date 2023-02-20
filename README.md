# parameters-management
### How to run?

1. Start postgres:14.7 with 
```
docker-compose up
```
2. Start frontend
```agsl
cp ./app
npm i
npm run serve
```

3. Start backend 
```
cd ./bacend
mvn spring-boot:run
```

4. Use "2023-02-20T17:50:00.000" or similar format for "galioja" fields

### TODO
- some refactoring for frontend and backend;
- implement translations and date-time picker for frontend (at the moment use "2023-02-20T17:50:00.000" for "galioja" fields);
- make table as configurable vuetify component;
- write some tests;
