# E-Commerce Backend API Documentation

## For Angular Frontend Developers

**Version:** 1.0.0  
**Base URL:** `http://localhost:8081/api`  
**Date:** January 2026

---

## Table of Contents

1. [Overview](#overview)
2. [Authentication](#authentication)
3. [CORS Configuration](#cors-configuration)
4. [Error Handling](#error-handling)
5. [Pagination](#pagination)
6. [API Endpoints](#api-endpoints)
   - [Users](#users)
   - [Categories](#categories)
   - [Products](#products)
   - [Orders](#orders)
   - [Order Items](#order-items)
7. [Business Rules](#business-rules)
8. [Angular Integration Examples](#angular-integration-examples)

---

## Overview

This is a RESTful API for an E-Commerce application built with Spring Boot 3.5.6 and Java 17. The API follows REST best practices and uses JSON for request/response bodies.

### Technologies Used
- Java 17
- Spring Boot 3.5.6
- Spring Data JPA
- Spring Security
- MySQL Database
- Lombok
- Jakarta Validation

---

## Authentication

**Current Status:** The API endpoints are publicly accessible (no authentication required).

> **Note:** For production, implement JWT-based authentication. CORS is configured to accept requests from `http://localhost:4200` (Angular default port).

---

## CORS Configuration

The API is configured to accept requests from Angular applications running on `http://localhost:4200`.

**Allowed Methods:** `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`  
**Allowed Headers:** All headers  
**Credentials:** Enabled

---

## Error Handling

All errors return a consistent JSON structure:

### Error Response Format

```json
{
  "timestamp": "2026-01-19T21:43:35.439Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Description of the error",
  "path": "/api/products",
  "fieldErrors": [
    {
      "field": "name",
      "message": "Product name is required"
    }
  ]
}
```

### HTTP Status Codes

| Status Code | Description |
|-------------|-------------|
| `200` | OK - Request successful |
| `201` | Created - Resource created successfully |
| `204` | No Content - Resource deleted successfully |
| `400` | Bad Request - Validation errors |
| `404` | Not Found - Resource not found |
| `409` | Conflict - Business rule violation (e.g., insufficient stock) |
| `500` | Internal Server Error - Unexpected error |

---

## Pagination

List endpoints support pagination with the following query parameters:

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | int | 0 | Page number (0-indexed) |
| `size` | int | 10 | Number of items per page |
| `sortBy` | string | "id" | Field to sort by |
| `direction` | string | "asc" | Sort direction: "asc" or "desc" |

### Paginated Response Format

```json
{
  "content": [...],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    }
  },
  "totalElements": 100,
  "totalPages": 10,
  "last": false,
  "first": true,
  "numberOfElements": 10,
  "size": 10,
  "number": 0,
  "empty": false
}
```

---

## API Endpoints

---

### Users

#### Get All Users (Paginated)

**GET** `/api/users`

**Query Parameters:**
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | int | 0 | Page number |
| `size` | int | 10 | Items per page |
| `sortBy` | string | "id" | Sort field |
| `direction` | string | "asc" | Sort direction |

**Response:** `200 OK`
```json
{
  "content": [
    {
      "id": 1,
      "userName": "john_doe",
      "email": "john@example.com",
      "roles": ["USER"],
      "orderIds": [1, 2, 3]
    }
  ],
  "totalElements": 50,
  "totalPages": 5,
  "number": 0
}
```

---

#### Get All Users (Non-Paginated)

**GET** `/api/users/all`

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "userName": "john_doe",
    "email": "john@example.com",
    "roles": ["USER"],
    "orderIds": [1, 2, 3]
  }
]
```

---

#### Get User by ID

**GET** `/api/users/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | long | User ID |

**Response:** `200 OK`
```json
{
  "id": 1,
  "userName": "john_doe",
  "email": "john@example.com",
  "roles": ["USER"],
  "orderIds": [1, 2, 3]
}
```

**Error Response:** `404 Not Found`
```json
{
  "timestamp": "2026-01-19T21:43:35.439Z",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: 1",
  "path": "/api/users/1"
}
```

---

#### Create User

**POST** `/api/users`

**Request Body:**
```json
{
  "userName": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Validation Rules:**
| Field | Required | Constraints |
|-------|----------|-------------|
| `userName` | Yes | 3-50 characters |
| `email` | Yes | Valid email format |
| `password` | Yes | 6-100 characters |

**Response:** `201 Created`
```json
{
  "id": 1,
  "userName": "john_doe",
  "email": "john@example.com",
  "roles": [],
  "orderIds": []
}
```

**Error Response:** `400 Bad Request`
```json
{
  "timestamp": "2026-01-19T21:43:35.439Z",
  "status": 400,
  "error": "Validation Failed",
  "message": "One or more fields have invalid values",
  "path": "/api/users",
  "fieldErrors": [
    {
      "field": "email",
      "message": "Invalid email format"
    }
  ]
}
```

---

#### Update User

**PUT** `/api/users/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | long | User ID |

**Request Body:**
```json
{
  "userName": "john_updated",
  "email": "john_updated@example.com",
  "password": "newPassword123"
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "userName": "john_updated",
  "email": "john_updated@example.com",
  "roles": ["USER"],
  "orderIds": [1, 2, 3]
}
```

---

#### Delete User

**DELETE** `/api/users/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | long | User ID |

**Response:** `204 No Content`

---

### Categories

#### Get All Categories (Paginated)

**GET** `/api/categories`

**Query Parameters:**
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | int | 0 | Page number |
| `size` | int | 10 | Items per page |
| `sortBy` | string | "id" | Sort field |
| `direction` | string | "asc" | Sort direction |

**Response:** `200 OK`
```json
{
  "content": [
    {
      "id": 1,
      "name": "Electronics",
      "description": "Electronic devices and accessories",
      "productIds": [1, 2, 3]
    }
  ],
  "totalElements": 10,
  "totalPages": 1,
  "number": 0
}
```

---

#### Get All Categories (Non-Paginated)

**GET** `/api/categories/all`

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "Electronics",
    "description": "Electronic devices and accessories",
    "productIds": [1, 2, 3]
  }
]
```

---

#### Get Category by ID

**GET** `/api/categories/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | long | Category ID |

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "Electronics",
  "description": "Electronic devices and accessories",
  "productIds": [1, 2, 3]
}
```

---

#### Create Category

**POST** `/api/categories`

**Request Body:**
```json
{
  "name": "Electronics",
  "description": "Electronic devices and accessories"
}
```

**Validation Rules:**
| Field | Required | Constraints |
|-------|----------|-------------|
| `name` | Yes | 2-100 characters |
| `description` | No | Max 500 characters |

**Response:** `201 Created`
```json
{
  "id": 1,
  "name": "Electronics",
  "description": "Electronic devices and accessories",
  "productIds": []
}
```

---

#### Update Category

**PUT** `/api/categories/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | long | Category ID |

**Request Body:**
```json
{
  "name": "Updated Electronics",
  "description": "Updated description"
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "Updated Electronics",
  "description": "Updated description",
  "productIds": [1, 2, 3]
}
```

---

#### Delete Category

**DELETE** `/api/categories/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | long | Category ID |

**Response:** `204 No Content`

> **Note:** Deleting a category will also delete all products in that category (cascade delete).

---

### Products

#### Get All Products (Paginated)

**GET** `/api/products`

**Query Parameters:**
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | int | 0 | Page number |
| `size` | int | 10 | Items per page |
| `sortBy` | string | "id" | Sort field |
| `direction` | string | "asc" | Sort direction |

**Response:** `200 OK`
```json
{
  "content": [
    {
      "id": 1,
      "name": "iPhone 15",
      "description": "Latest Apple smartphone",
      "price": 999.99,
      "stock": 50,
      "categoryId": 1,
      "categoryName": "Electronics"
    }
  ],
  "totalElements": 100,
  "totalPages": 10,
  "number": 0
}
```

---

#### Get All Products (Non-Paginated)

**GET** `/api/products/all`

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "iPhone 15",
    "description": "Latest Apple smartphone",
    "price": 999.99,
    "stock": 50,
    "categoryId": 1,
    "categoryName": "Electronics"
  }
]
```

---

#### Get Product by ID

**GET** `/api/products/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | long | Product ID |

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "iPhone 15",
  "description": "Latest Apple smartphone",
  "price": 999.99,
  "stock": 50,
  "categoryId": 1,
  "categoryName": "Electronics"
}
```

---

#### Get Products by Category

**GET** `/api/products/category/{categoryId}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `categoryId` | long | Category ID |

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "iPhone 15",
    "description": "Latest Apple smartphone",
    "price": 999.99,
    "stock": 50,
    "categoryId": 1,
    "categoryName": "Electronics"
  }
]
```

---

#### Create Product

**POST** `/api/products`

**Request Body:**
```json
{
  "name": "iPhone 15",
  "description": "Latest Apple smartphone",
  "price": 999.99,
  "stock": 50,
  "categoryId": 1
}
```

**Validation Rules:**
| Field | Required | Constraints |
|-------|----------|-------------|
| `name` | Yes | 2-200 characters |
| `description` | No | Max 2000 characters |
| `price` | Yes | Must be greater than 0 |
| `stock` | No | Must be >= 0 (default: 0) |
| `categoryId` | No | Must exist if provided |

**Response:** `201 Created`
```json
{
  "id": 1,
  "name": "iPhone 15",
  "description": "Latest Apple smartphone",
  "price": 999.99,
  "stock": 50,
  "categoryId": 1,
  "categoryName": "Electronics"
}
```

---

#### Update Product

**PUT** `/api/products/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | long | Product ID |

**Request Body:**
```json
{
  "name": "iPhone 15 Pro",
  "description": "Updated description",
  "price": 1199.99,
  "stock": 30,
  "categoryId": 1
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "iPhone 15 Pro",
  "description": "Updated description",
  "price": 1199.99,
  "stock": 30,
  "categoryId": 1,
  "categoryName": "Electronics"
}
```

---

#### Delete Product

**DELETE** `/api/products/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | long | Product ID |

**Response:** `204 No Content`

---

### Orders

#### Get All Orders (Paginated)

**GET** `/api/orders`

**Query Parameters:**
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | int | 0 | Page number |
| `size` | int | 10 | Items per page |
| `sortBy` | string | "id" | Sort field |
| `direction` | string | "desc" | Sort direction |

**Response:** `200 OK`
```json
{
  "content": [
    {
      "id": 1,
      "date": "2026-01-19T21:43:35.439Z",
      "total": 1999.98,
      "status": "PENDING",
      "userId": 1,
      "userName": "john_doe",
      "orderItems": [
        {
          "id": 1,
          "quantity": 2,
          "subtotal": 1999.98,
          "orderId": 1,
          "productId": 1,
          "productName": "iPhone 15",
          "productPrice": 999.99
        }
      ]
    }
  ],
  "totalElements": 50,
  "totalPages": 5,
  "number": 0
}
```

---

#### Get All Orders (Non-Paginated)

**GET** `/api/orders/all`

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "date": "2026-01-19T21:43:35.439Z",
    "total": 1999.98,
    "status": "PENDING",
    "userId": 1,
    "userName": "john_doe",
    "orderItems": [...]
  }
]
```

---

#### Get Order by ID

**GET** `/api/orders/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | long | Order ID |

**Response:** `200 OK`
```json
{
  "id": 1,
  "date": "2026-01-19T21:43:35.439Z",
  "total": 1999.98,
  "status": "PENDING",
  "userId": 1,
  "userName": "john_doe",
  "orderItems": [
    {
      "id": 1,
      "quantity": 2,
      "subtotal": 1999.98,
      "orderId": 1,
      "productId": 1,
      "productName": "iPhone 15",
      "productPrice": 999.99
    }
  ]
}
```

---

#### Get Orders by User ID

**GET** `/api/orders/user/{userId}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `userId` | long | User ID |

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "date": "2026-01-19T21:43:35.439Z",
    "total": 1999.98,
    "status": "PENDING",
    "userId": 1,
    "userName": "john_doe",
    "orderItems": [...]
  }
]
```

---

#### Create Order

**POST** `/api/orders`

**Request Body:**
```json
{
  "userId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

**Validation Rules:**
| Field | Required | Constraints |
|-------|----------|-------------|
| `userId` | Yes | Must exist |
| `items` | Yes | At least one item |
| `items[].productId` | Yes | Must exist |
| `items[].quantity` | Yes | Must be >= 1 |

**Response:** `201 Created`
```json
{
  "id": 1,
  "date": "2026-01-19T21:43:35.439Z",
  "total": 2999.97,
  "status": "PENDING",
  "userId": 1,
  "userName": "john_doe",
  "orderItems": [
    {
      "id": 1,
      "quantity": 2,
      "subtotal": 1999.98,
      "orderId": 1,
      "productId": 1,
      "productName": "iPhone 15",
      "productPrice": 999.99
    },
    {
      "id": 2,
      "quantity": 1,
      "subtotal": 999.99,
      "orderId": 1,
      "productId": 2,
      "productName": "MacBook Pro",
      "productPrice": 999.99
    }
  ]
}
```

**Error Response (Insufficient Stock):** `409 Conflict`
```json
{
  "timestamp": "2026-01-19T21:43:35.439Z",
  "status": 409,
  "error": "Conflict",
  "message": "Insufficient stock for product 'iPhone 15'. Requested: 100, Available: 50",
  "path": "/api/orders"
}
```

---

#### Update Order Status

**PATCH** `/api/orders/{id}/status`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | long | Order ID |

**Query Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `status` | string | New status value |

**Possible Status Values:**
- `PENDING`
- `CONFIRMED`
- `PROCESSING`
- `SHIPPED`
- `DELIVERED`
- `CANCELLED`

**Example:** `PATCH /api/orders/1/status?status=SHIPPED`

**Response:** `200 OK`
```json
{
  "id": 1,
  "date": "2026-01-19T21:43:35.439Z",
  "total": 1999.98,
  "status": "SHIPPED",
  "userId": 1,
  "userName": "john_doe",
  "orderItems": [...]
}
```

---

#### Delete Order

**DELETE** `/api/orders/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | long | Order ID |

**Response:** `204 No Content`

> **Note:** Deleting an order will restore the stock for all products in the order.

---

### Order Items

#### Get All Order Items (Paginated)

**GET** `/api/order-items`

**Query Parameters:**
| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `page` | int | 0 | Page number |
| `size` | int | 10 | Items per page |
| `sortBy` | string | "id" | Sort field |
| `direction` | string | "asc" | Sort direction |

**Response:** `200 OK`
```json
{
  "content": [
    {
      "id": 1,
      "quantity": 2,
      "subtotal": 1999.98,
      "orderId": 1,
      "productId": 1,
      "productName": "iPhone 15",
      "productPrice": 999.99
    }
  ],
  "totalElements": 100,
  "totalPages": 10,
  "number": 0
}
```

---

#### Get Order Item by ID

**GET** `/api/order-items/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | long | Order Item ID |

**Response:** `200 OK`
```json
{
  "id": 1,
  "quantity": 2,
  "subtotal": 1999.98,
  "orderId": 1,
  "productId": 1,
  "productName": "iPhone 15",
  "productPrice": 999.99
}
```

---

#### Get Order Items by Order ID

**GET** `/api/order-items/order/{orderId}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `orderId` | long | Order ID |

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "quantity": 2,
    "subtotal": 1999.98,
    "orderId": 1,
    "productId": 1,
    "productName": "iPhone 15",
    "productPrice": 999.99
  }
]
```

---

#### Delete Order Item

**DELETE** `/api/order-items/{id}`

**Path Parameters:**
| Parameter | Type | Description |
|-----------|------|-------------|
| `id` | long | Order Item ID |

**Response:** `204 No Content`

---

## Business Rules

### Order Processing

1. **Stock Validation:** When creating an order, the system validates that sufficient stock is available for each product.
2. **Stock Deduction:** Upon successful order creation, stock is automatically deducted from products.
3. **Stock Restoration:** When an order is deleted, stock is automatically restored to products.
4. **Total Calculation:** Order total is automatically calculated based on product prices and quantities.
5. **Initial Status:** New orders are created with `PENDING` status.
6. **Subtotal Calculation:** Each order item's subtotal is calculated as `price × quantity`.

### User Management

1. **Password Hashing:** Passwords are securely hashed using BCrypt before storage.
2. **Email Uniqueness:** Each user must have a unique email address.

### Category Management

1. **Cascade Delete:** Deleting a category will delete all associated products.

### Product Management

1. **Category Assignment:** Products can optionally be assigned to a category.
2. **Stock Default:** If stock is not provided, it defaults to 0.

---

## Angular Integration Examples

### Service Configuration

```typescript
// environment.ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8081/api'
};
```

### HTTP Interceptor for Error Handling

```typescript
// error.interceptor.ts
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = 'An error occurred';
        
        if (error.error?.message) {
          errorMessage = error.error.message;
        }
        
        if (error.error?.fieldErrors) {
          const fieldMessages = error.error.fieldErrors
            .map((e: any) => `${e.field}: ${e.message}`)
            .join(', ');
          errorMessage = fieldMessages;
        }
        
        console.error('API Error:', errorMessage);
        return throwError(() => new Error(errorMessage));
      })
    );
  }
}
```

### Product Service Example

```typescript
// product.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

export interface Product {
  id?: number;
  name: string;
  description?: string;
  price: number;
  stock?: number;
  categoryId?: number;
  categoryName?: string;
}

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = `${environment.apiUrl}/products`;

  constructor(private http: HttpClient) {}

  getProducts(page = 0, size = 10, sortBy = 'id', direction = 'asc'): Observable<Page<Product>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', sortBy)
      .set('direction', direction);

    return this.http.get<Page<Product>>(this.apiUrl, { params });
  }

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/all`);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/${id}`);
  }

  getProductsByCategory(categoryId: number): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/category/${categoryId}`);
  }

  createProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product);
  }

  updateProduct(id: number, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.apiUrl}/${id}`, product);
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
```

### Order Service Example

```typescript
// order.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

export interface OrderItem {
  id?: number;
  quantity: number;
  subtotal?: number;
  orderId?: number;
  productId: number;
  productName?: string;
  productPrice?: number;
}

export interface Order {
  id?: number;
  date?: Date;
  total?: number;
  status?: string;
  userId: number;
  userName?: string;
  orderItems?: OrderItem[];
}

export interface CreateOrderRequest {
  userId: number;
  items: { productId: number; quantity: number }[];
}

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = `${environment.apiUrl}/orders`;

  constructor(private http: HttpClient) {}

  createOrder(order: CreateOrderRequest): Observable<Order> {
    return this.http.post<Order>(this.apiUrl, order);
  }

  getOrderById(id: number): Observable<Order> {
    return this.http.get<Order>(`${this.apiUrl}/${id}`);
  }

  getOrdersByUserId(userId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiUrl}/user/${userId}`);
  }

  updateOrderStatus(id: number, status: string): Observable<Order> {
    const params = new HttpParams().set('status', status);
    return this.http.patch<Order>(`${this.apiUrl}/${id}/status`, null, { params });
  }

  deleteOrder(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
```

### Component Usage Example

```typescript
// product-list.component.ts
import { Component, OnInit } from '@angular/core';
import { ProductService, Product, Page } from '../services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html'
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  totalElements = 0;
  currentPage = 0;
  pageSize = 10;
  loading = false;
  error: string | null = null;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.loading = true;
    this.error = null;

    this.productService.getProducts(this.currentPage, this.pageSize).subscribe({
      next: (page: Page<Product>) => {
        this.products = page.content;
        this.totalElements = page.totalElements;
        this.loading = false;
      },
      error: (err: Error) => {
        this.error = err.message;
        this.loading = false;
      }
    });
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadProducts();
  }
}
```

### Creating an Order Example

```typescript
// checkout.component.ts
import { Component } from '@angular/core';
import { OrderService, CreateOrderRequest } from '../services/order.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html'
})
export class CheckoutComponent {
  cartItems = [
    { productId: 1, quantity: 2 },
    { productId: 2, quantity: 1 }
  ];
  userId = 1; // From authentication
  processing = false;
  error: string | null = null;

  constructor(private orderService: OrderService) {}

  placeOrder(): void {
    this.processing = true;
    this.error = null;

    const order: CreateOrderRequest = {
      userId: this.userId,
      items: this.cartItems
    };

    this.orderService.createOrder(order).subscribe({
      next: (createdOrder) => {
        console.log('Order created:', createdOrder);
        this.processing = false;
        // Navigate to order confirmation
      },
      error: (err: Error) => {
        this.error = err.message;
        this.processing = false;
      }
    });
  }
}
```

---

## Date Formats

All dates are returned in ISO 8601 format: `YYYY-MM-DDTHH:mm:ss.sssZ`

Example: `2026-01-19T21:43:35.439Z`

---

## Recommended API Call Sequence

1. **Initial Load:**
   - Load categories: `GET /api/categories/all`
   - Load products with pagination: `GET /api/products?page=0&size=10`

2. **Product Filtering:**
   - Filter by category: `GET /api/products/category/{categoryId}`

3. **User Registration/Login:**
   - Create user: `POST /api/users`

4. **Shopping Cart to Order:**
   - Create order with cart items: `POST /api/orders`

5. **Order Management:**
   - Get user's orders: `GET /api/orders/user/{userId}`
   - Update order status (admin): `PATCH /api/orders/{id}/status?status=SHIPPED`

---

## Support

For any issues or questions about this API, please contact the backend development team.
