package com.pany.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores and handles records
 *
 * @author Sobek
 *
 */
public class RecordMonitor {
    private static List<Record> records = new ArrayList<>();

    private static int totalRecords = 0;

    public static List<Record> getRecords() {
        return records;
    }

    public static void setRecords(List<Record> records) {
        RecordMonitor.records = records;
    }

    public static int getTotalRecords() {
        return totalRecords;
    }

    public static void setTotalRecords(int totalRecords) {
        RecordMonitor.totalRecords = totalRecords;
    }

}
