# *generate jacoco test coverage report*
1. `mvn clean test`
2. `mvn jacoco:report`
3. Navigate to `target/site/jacoco/index.html`
4. Open `index.html` in a browser to view the test coverage.




# APIs

## 1. Create Book
**API:** `{url}/bookstore/books`  
**METHOD_TYPE:** `POST`  
**REQUEST_BODY:**  
```json
{
    "title": "abc",
    "author": "abc",
    "price": 2
}
```
**RESPONSE_BODY:**  
```json
{
    "title": "b",
    "author": "b",
    "price": 2
}
```

## 2. Get All Books
**API:** `{url}/bookstore/books`  
**METHOD_TYPE:** `GET`  
**RESPONSE_BODY:**  
```json
{
    "title": "b",
    "author": "b",
    "price": 2
}
```

## 3. Get Book By ID
**API:** `{url}/bookstore/books/2`  
**METHOD_TYPE:** `GET`  
**RESPONSE_BODY:**  
```json
{
    "title": "b",
    "author": "b",
    "price": 2
}
```

## 4. Update Book
**API:** `{url}/bookstore/books/2`  
**METHOD_TYPE:** `PUT`  
**REQUEST_BODY:**  
```json
{
    "title": "c",
    "author": "c",
    "price": 3
}
```
**RESPONSE_BODY:**  
```json
{
    "id": 2,
    "title": "c",
    "author": "c",
    "price": 3.0
}
```

## 5. Delete Book
**API:** `{url}/bookstore/books/2`  
**METHOD_TYPE:** `DELETE`




## Author  
**Harshit Sharma**  
- [GitHub](https://github.com/harshiteuro)  
- [LinkedIn](https://www.linkedin.com/in/harshit-sharma-94773b1b0/)  