package com.Inventory_Management_API.Inventory_API.Exception;

public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException(String message) {
        super(message);
    }
}