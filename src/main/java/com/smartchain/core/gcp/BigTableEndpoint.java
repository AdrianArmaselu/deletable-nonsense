package com.smartchain.core.gcp;

import com.google.cloud.bigtable.hbase.BigtableConfiguration;
import java.io.IOException;

import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Connection;

public class BigTableEndpoint {

    private static final String PROJECT_ID = "alysia-172200";
    private static final String INSTANCE_ID = "alysia-data";

    private static Connection connection = null;

    public static void connect() throws IOException {
        connection = BigtableConfiguration.connect(PROJECT_ID, INSTANCE_ID);
    }

    public static void main(String[] args) {
        try {
            connect();
//            HTableDescriptor hTableDescriptor =
//            connection.getAdmin().createTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
