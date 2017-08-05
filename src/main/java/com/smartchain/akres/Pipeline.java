package com.smartchain.akres;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by adrian on 8/1/17.
 */
public class Pipeline {
    static String QUOTE = "\"";
    public static void main(String[] args) {
        populateComposer();


    }

    public static void populateComposer(){

        int total = 50;
        String fileName = "/home/adrian/IdeaProjects/smartchain-core/src/main/resources/datagovbldgrexus.csv";
        FileReader fileReader = null;
        CSVParser csvParser = null;
        try {
            fileReader = new FileReader(fileName);
            csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
            List<CSVRecord> csvRecords = csvParser.getRecords();
            Iterator<String> recordIterator = csvRecords.get(0).iterator();
            List<String> headers = new LinkedList<>();
            while(recordIterator.hasNext()) {
                String columnName = recordIterator.next().trim();
                columnName = columnName.replace(" ", "_");
                columnName = columnName.replace("/", "_");
                headers.add(columnName);
            }
            csvRecords = csvRecords.subList(1, csvRecords.size());
            Iterator<CSVRecord> recordsIterator = csvRecords.iterator();

            int count = 0;
            while(recordsIterator.hasNext() && count < total){

                CSVRecord record = recordsIterator.next();
                StringBuilder body = new StringBuilder("{").append("\"$class\"").append(":").append("\"com.akres.LandTitle\"").append(",")
                        .append("\"TitleOwner\"").append(":").append("\"").append(System.currentTimeMillis()).append("\",");
                for (int i = 0 ; i < headers.size() ; i++)
                    body.append(QUOTE).append(headers.get(i).trim()).append(QUOTE).append(":").append(QUOTE).append(record.get(i)).append(QUOTE).append(",");
                body.deleteCharAt(body.length() - 1);
                body.append("}");
                HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:3000/api/LandTitle")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .body(body.toString())
                    .asJson();
//                HttpResponse<JsonNode> jsonResponse = Unirest.delete("http://localhost:3000/api/LandTitle/" + record.get(0))
//                        .header("accept", "application/json")
//                        .header("Content-Type", "application/json")
//                        .header("Accept-Encoding", "gzip, deflate, br")
//                        .body("{\"Location_Code\":\"" + record.get(0) + "\"}")
//                        .asJson();
                count++;
            System.out.println(body.toString());
            System.out.println(jsonResponse.getBody());
            }
            System.out.println(Unirest.get("http://localhost:3000/api/LandTitle").asJson().getBody().toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(fileReader);
            closeResource(csvParser);
        }
    }

    public static void createModel(){
        //TODO: change this to grab from resources folder
        String fileName = "/home/adrian/IdeaProjects/smartchain-core/src/main/resources/datagovbldgrexus.csv";
        FileReader fileReader = null;
        CSVParser csvParser = null;
        try {
            fileReader = new FileReader(fileName);
            csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
            List<CSVRecord> csvRecords = csvParser.getRecords();
            CSVRecord headers = csvRecords.get(0);
            StringBuilder output = new StringBuilder("{\n");
            for (String header : headers) {
                header = header.replace(" ", "_");
                output.append("o String ").append(header).append("\n");
            }
            output.append("}");
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(fileReader);
            closeResource(csvParser);
        }
    }

    public static void closeResource(Closeable closeable) {
        if (closeable != null) try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/*
 StringBuilder value =  new StringBuilder()
                    .append("{")
                    .append("\"$class\"").append(":").append("\"com.akres.LandTitle\",")
                    .append("\"Location_Code\": ").append("\"999\",")
                    .append("\"TitleOwner\": ").append("\"2\",")
                    .append("\"Region_Code\": ").append("\"3\",")
                    .append("\"Bldg_Address1\": ").append("\"4\",")
                    .append("\"Bldg_Address2\": ").append("\"5\",")
                    .append("\"Bldg_City\": ").append("\"6\",")
                    .append("\"Bldg_County\": ").append("\"7\",")
                    .append("\"Bldg_State\": ").append("\"8\",")
                    .append("\"Bldg_Zip\": ").append("\"9\",")
                    .append("\"Congressional_District\": ").append("\"0\",")
                    .append("\"Bldg_Status\": ").append("\"11\",")
                    .append("\"Property_Type\": ").append("\"11\",")
                    .append("\"Bldg_ANSI_Usable\": ").append("\"1\",")
                    .append("\"Total_Parking_Spaces\": ").append("\"1\",")
                    .append("\"Owned_Leased\": ").append("\"1\",")
                    .append("\"Construction_Date\": ").append("\"1\",")
                    .append("\"Historical_Type\": ").append("\"1\",")
                    .append("\"Historical_Status\": ").append("\"1\",")
                    .append("\"ABA_Accessibility_Flag\": ").append("\"1\"")
                    .append("}");
 */