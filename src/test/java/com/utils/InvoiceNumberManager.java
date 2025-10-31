package com.utils;

import java.io.*;
import java.nio.file.*;

public class InvoiceNumberManager {

    private static final String FILE_PATH = "src/test/resources/invoiceNumber.txt";

    public static String getNextInvoiceNumber() {
        try {
            String currentNumber = Files.readString(Path.of(FILE_PATH)).trim();
            int nextNumber = Integer.parseInt(currentNumber) + 1;
            Files.writeString(Path.of(FILE_PATH), String.valueOf(nextNumber));
            return String.valueOf(nextNumber);
        } catch (Exception e) {
            throw new RuntimeException("Error while reading/updating invoice number file!", e);
        }
    }
}
