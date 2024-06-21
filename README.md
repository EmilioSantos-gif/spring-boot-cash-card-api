# Spring Boot Cash Card API

## Overview
The Spring Boot Cash Card API provides a robust and efficient way to manage cash card operations, including creating, retrieving, updating, and deleting cash cards. This API is designed to facilitate seamless integration with financial applications, ensuring secure and scalable management of cash card data.

## Features
- **Create Cash Card**: Issue new cash cards with unique identifiers and predefined balance limits.
- **Retrieve Cash Card**: Fetch details of a specific cash card using its unique identifier.
- **Update Cash Card**: Modify the details of an existing cash card, including balance updates and card status.
- **Delete Cash Card**: Remove a cash card from the system securely.

## Endpoints

### 1. Create Cash Card
- **URL**: `/api/cashcards`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
      "cardHolderName": "John Doe",
      "initialBalance": 100.00
  }
