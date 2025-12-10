# **Inventory Management API**

This is a  Spring Boot REST API, I built for managing products and categories with validation and exception handling.

## **Features**

* CRUD operations for Products and Categories

* Custom exception handling.

* Input validation

* Product filtering by category, min/max price

* Automatic timestamps(createdAt, updatedAt)

* DTOs for clean requests.

* Global exception handler for error responses

## **Technologies Used**

* Java 17+ (i used 21 in this project)

* Spring Boot 3.x

* Spring Data JPA

* H2/MySQL Database

* Lombok

* Jakarta Validation

* Maven

## **Project Structure**

src/main/java/com/Inventory\_Management\_API/Inventory\_API/

├── controller/

│   ├── CategoryController.java

│   └── ProductController.java

├── service/

│   ├── CategoryService.java

│   └── ProductService.java

├── repository/

│   ├── CategoryRepository.java

│   └── ProductRepository.java

├── entities/

│   ├── Category.java

│   └── Products.java

├── dto/

│   ├── CategoryRequest.java

│   ├── CategoryResponse.java

│   ├── ProductRequest.java

│   ├── ProductResponse.java

│   └── ErrorResponse.java

└── Exception/

    ├── GlobalExceptionHandler.java
    ├── CategoryNotFoundException.java
    ├── ProductNotFoundException.java
    └── DuplicateNameException.java

## **Setup Instructions**

### **Prerequisites**

* Java 17 or higher

* Maven 3.6+

* IDE (IntelliJ IDEA, Eclipse, or VS Code)

### **Installation Steps**

**Clone/Extract the project :** cd Inventory-Management-API

**Configure Database** (Optional \- H2 runs by default for development purpose only)

 The project uses H2 in-memory database by default. Configuration in `application.properties`:

 \# H2 Database (Default \- In-Memory)

spring.datasource.url=jdbc:h2:mem:inventorydb

spring.datasource.driverClassName=org.h2.Driver

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

spring.h2.console.enabled=true

spring.jpa.hibernate.ddl-auto=update


\# For Mysql setup using Workbench

spring.application.name=Inventory\_API

spring.datasource.url=jdbc:mysql://localhost:3306/inventory\_store

spring.datasource.username=root

spring.datasource.password=123456

spring.jpa.hibernate.ddl-auto=update

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true


for production use invironment variables instead of using direct url and password

**Build the project :** mvn clean install

**Run the application :** mvn spring-boot:run

 The API will start at: `http://localhost:8080`

**Verify the application**  
 Check the console for:  
 Started InventoryApiApplication in X.XXX seconds

## **API Endpoints**

### **Category Endpoints**

| Method | Endpoint | Description | Request Body | Response |
| ----- | ----- | ----- | ----- | ----- |
| POST | `/api/categories` | Create a new category | CategoryRequest | 201 Created |
| GET | `/api/categories` | Get all categories | \- | 200 OK |
| GET | `/api/categories/{id}` | Get category by ID | \- | 200 OK |
| DELETE | `/api/categories/{id}` | Delete category | \- | 204 No Content |

### **Product Endpoints**

| Method | Endpoint | Description | Request Body | Response |
| ----- | ----- | ----- | ----- | ----- |
| POST | `/api/products` | Create a new product | ProductRequest | 201 Created |
| GET | `/api/products` | Get all products (with filters) | \- | 200 OK |
| GET | `/api/products/{id}` | Get product by ID | \- | 200 OK |
| PUT | `/api/products/{id}` | Update product | ProductRequest | 200 OK |
| DELETE | `/api/products/{id}` | Delete product | \- | 204 No Content |

### **Query Parameters for GET `/api/products`**

* `categoryId`\- Filter by category ID (e.g., `categoryId=2`)

* `minPrice` \-Minimum price filter (e.g., `minPrice=10`)

* `maxPrice` \-Maximum price filter (e.g., `maxPrice=25`)

**Examples:**

* All products:`/api/products`

* By category: `/api/products?categoryId=2`

* By price range:`/api/products?minPrice=10&maxPrice=25`

* Combined filters: `/api/products?categoryId=2&minPrice=10&maxPrice=25`

## **API Request/Response Examples**

### **1\. Category Operations**

#### **Create Category (POST `/api/categories`)**

**Request:**

{

  "name": "Electronics",

  "description": "Electronic devices and gadgets"

}

**Response (201 Created):**

{

  "id": 1,

  "name": "Electronics",

  "description": "Electronic devices and gadgets"

}

#### **Get All Categories (GET `/api/categories`)**

**Response (200 OK):**

\[

  {

    "id": 1,

    "name": "Electronics",

    "description": "Electronic devices and gadgets"

  },

  {

    "id": 2,

    "name": "Furniture",

    "description": "Home and office furniture"

  }

\]

#### **Get Category by ID (GET `/api/categories/1`)**

**Response (200 OK):**

{

  "id": 1,

  "name": "Electronics",

  "description": "Electronic devices and gadgets"

}

#### **Delete Category (DELETE `/api/categories/1`)**

**Response:** 204 No Content

---

### **2\. Product Operations**

#### **Create Product (POST `/api/products`)**

**Request:**

{

  "name": "Laptop",

  "price": 899.99,

  "quantity": 50,

  "categoryId": 1

}

**Response (201 Created):**

{

  "id": 1,

  "name": "Laptop",

  "price": 899.99,

  "quantity": 50,

  "category\_id": 1,

  "category\_name": "Electronics",

  "createdAt": "2025-12-10T10:30:00",

  "updatedAt": "2025-12-10T10:30:00"

}

#### **Get Product by ID (GET `/api/products/1`)**

**Response (200 OK):**

{

  "id": 1,

  "name": "Laptop",

  "price": 899.99,

  "quantity": 50,

  "category\_id": 1,

  "category\_name": "Electronics",

  "createdAt": "2025-12-10T10:30:00",

  "updatedAt": "2025-12-10T10:30:00"

}

#### **Update Product (PUT `/api/products/1`)**

**Request:**

{

  "name": "Gaming Laptop",

  "price": 1299.99,

  "quantity": 30,

  "categoryId": 1

}

**Response (200 OK):**

{

  "id": 1,

  "name": "Gaming Laptop",

  "price": 1299.99,

  "quantity": 30,

  "category\_id": 1,

  "category\_name": "Electronics",

  "createdAt": "2025-12-10T10:30:00",

  "updatedAt": "2025-12-10T14:20:00"

}

#### **Delete Product (DELETE `/api/products/1`)**

**Response:** 204 No Content

---

### **3\. Product Filtering**

#### **Get All Products (GET `/api/products`)**

**Response (200 OK):**

\[

  {

    "id": 1,

    "name": "Laptop",

    "price": 899.99,

    "quantity": 50,

    "category\_id": 1,

    "category\_name": "Electronics",

    "createdAt": "2025-12-10T10:30:00",

    "updatedAt": "2025-12-10T10:30:00"

  },

  {

    "id": 2,

    "name": "Office Chair",

    "price": 249.99,

    "quantity": 25,

    "category\_id": 2,

    "category\_name": "Furniture",

    "createdAt": "2025-12-10T10:35:00",

    "updatedAt": "2025-12-10T10:35:00"

  }

\]

#### **Filter by Category and Max Price (GET `/api/products?categoryId=2&maxPrice=25`)**

Returns only products in category 2 with price ≤ 25

#### **Filter with All Parameters (GET `/api/products?categoryId=1&minPrice=500&maxPrice=1000`)**

Returns products in category 1 with price between 500 and 1000

---

## **Error Responses & Exception Handling**

The API uses a global exception handler (`@ControllerAdvice`) to provide consistent error responses.

### **Error Response Format**

{

  "timestamp": "2025-12-10T10:15:30",

  "message": "Error description",

  "path": "/api/endpoint",

  "status": 404

}

### **HTTP Status Codes**

* `200 OK` \- Successful GET, PUT requests

* `201 Created` \- Successful POST requests

* `204 No Content` \- Successful DELETE requests

* `400 Bad Request` \- Validation errors

* `404 Not Found` \- Resource not found

* `409 Conflict` \- Duplicate name violation

* `500 Internal Server Error` \- Unexpected errors

### **Common Error Scenarios Tested**

#### **1\. Resource Not Found (404)**

**Scenario:** Fetching a category after deleting it

**Request:** `GET /api/categories/1` (after deletion)

**Response (404 Not Found):**

{

  "timestamp": "2025-12-10T10:15:30",

  "message": "Category with Id 1 not found",

  "path": "/api/categories/1",

  "status": 404

}

#### **2\. Duplicate Name Error (409)**

**Scenario:** Creating a category with duplicate name

**Request:** `POST /api/categories`

{

  "name": "Electronics",

  "description": "Duplicate test"

}

**Response (409 Conflict):**

{

  "timestamp": "2025-12-10T10:16:00",

  "message": "This Category Electronics already exists.",

  "path": "/api/categories",

  "status": 409

}

**Scenario:** Creating a product with duplicate name

**Request:** `POST /api/products`

{

  "name": "Laptop",

  "price": 799.99,

  "quantity": 20,

  "categoryId": 1

}

**Response (409 Conflict):**

{

  "timestamp": "2025-12-10T10:17:00",

  "message": "Duplicate product Laptop already exists",

  "path": "/api/products",

  "status": 409

}

#### **3\. Validation Errors (400)**

**Scenario A:** Category name is null/blank

**Request:** `POST /api/categories`

{

  "name": "",

  "description": "Missing name"

}

**Response (400 Bad Request):**

{

  "timestamp": "2025-12-10T10:18:00",

  "message": "name: Name of Category is required",

  "path": "/api/categories",

  "status": 400

}

**Scenario B:** Product name is null

**Request:** `POST /api/products`

{

  "name": "",

  "price": 100.0,

  "quantity": 10,

  "categoryId": 1

}

**Response (400 Bad Request):**

{

  "timestamp": "2025-12-10T10:19:00",

  "message": "name: Product name is required",

  "path": "/api/products",

  "status": 400

}

**Scenario C:** Product price is null

**Request:** `POST /api/products`

{

  "name": "Mouse",

  "price": null,

  "quantity": 10,

  "categoryId": 1

}

**Response (400 Bad Request):**

{

  "timestamp": "2025-12-10T10:20:00",

  "message": "price: Price is required",

  "path": "/api/products",

  "status": 400

}

**Scenario D:** Product price is negative

**Request:** `POST /api/products`

{

  "name": "Keyboard",

  "price": \-50.0,

  "quantity": 10,

  "categoryId": 1

}

**Response (400 Bad Request):**

{

  "timestamp": "2025-12-10T10:21:00",

  "message": "price: Price must be \> 0",

  "path": "/api/products",

  "status": 400

}

## **Validation Rules**

### **Category Validation**

* **name**: Required, cannot be blank, must be unique

* **description**: Optional

### **Product Validation**

* **name**: Required, cannot be blank, must be unique

* **price**: Required, must be greater than 0

* **quantity**: Must be greater than or equal to 0

* **categoryId**: Required, must reference an existing category

## **Testing Documentation**

### **Test Coverage**

All endpoints have been thoroughly tested with Postman. See `Postman_Screenshots.pdf` for detailed test results including:

- **Category Endpoints:**

  * Creating categories

  * Fetching all categories

  * Fetching category by ID

  * Deleting categories

  * Error handling for deleted categories

  * Duplicate category validation

- **Product Endpoints:**

  * Creating products

  * Fetching products by ID

  * Updating products

  * Deleting products

  * Filtering with all combinations:

* All products (no filters)  
* By category ID  
* By max price  
* By category and price range  
* All three filters combined

- **Validation & Exception Handling:**

  * Category name validation (null/empty)

  * Product name validation (null/empty)

  * Price validation (null/negative)

  * Duplicate name detection

  * Resource not found scenarios

## **Database Console (H2)**

Access the H2 console at: `http://localhost:8080/h2-console`

**Credentials:**

* JDBC URL: `jdbc:h2:mem:inventorydb`

* Username: `sa`

* Password: (leave empty)

## **Architecture Highlights**

* **Layered Architecture**: Clear separation of concerns (Controller → Service → Repository)

* **DTO Pattern**: Prevents entity exposure

* **Global Exception Handling**: Consistents error responses

* **Validation**: Input validation at DTO level

* **JPA Relationships**: Proper ManyToOne relationship between Product and Category

* **Automatic Timestamps**: Using `@CreationTimestamp` and `@UpdateTimestamp`

## **Dependencies**

Key dependencies used in `pom.xml`:

\<dependencies\>

    \<\!-- Spring Boot Starter Web \-\>

    \<dependency\>

        \<groupId\>org.springframework.boot\</groupId\>

        \<artifactId\>spring-boot-starter-web\</artifactId\>

    \</dependency\>

    

    \<\!-- Spring Boot Starter Data JPA \-\>

    \<dependency\>

        \<groupId\>org.springframework.boot\</groupId\>

        \<artifactId\>spring-boot-starter-data-jpa\</artifactId\>

    \</dependency\>

    

    \<\!-- Spring Boot Starter Validation \-\>

    \<dependency\>

        \<groupId\>org.springframework.boot\</groupId\>

        \<artifactId\>spring-boot-starter-validation\</artifactId\>

    \</dependency\>

    

    \<\!-- H2 Database \-\>

    \<dependency\>

        \<groupId\>com.h2database\</groupId\>

        \<artifactId\>h2\</artifactId\>

        \<scope\>runtime\</scope\>

    \</dependency\>

\<\!--MySQL-\>

\<dependency\>

     \<groupId\>com.mysql\</groupId\>

	\<artifactId\>mysql-connector-j\</artifactId\>

	\<scope\>runtime\</scope\>

\<dependency\>

    \<\!-- Lombok \-\>

    \<dependency\>

        \<groupId\>org.projectlombok\</groupId\>

        \<artifactId\>lombok\</artifactId\>

        \<optional\>true\</optional\>

    \</dependency\>

\</dependencies\>

