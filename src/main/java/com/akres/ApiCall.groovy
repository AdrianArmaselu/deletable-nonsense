package com.akres

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.JsonNode
import com.mashape.unirest.http.Unirest
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord

class Pipeline1 {
    static void main(String[] args) {
        populateComposer()
    }

    static void closeResource(Closeable c) {
        if (c != null) try {
            c.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void populateComposer() {
        String fileName = "/home/adrian/IdeaProjects/smartchain-core/src/main/resources/datagovbldgrexus.csv";
        FileReader fileReader = null;
        CSVParser csvParser = null;
        try {
            fileReader = new FileReader(fileName);
            csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
            List<CSVRecord> csvRecords = csvParser.getRecords();
            Iterator<String> recordIterator = csvRecords[0].iterator();
            List<String> columnNames = new LinkedList<>();
            while (recordIterator.hasNext()) {
                def header = recordIterator.next()
                if(header == null) break
                columnNames.add(header);
            }
            csvRecords = csvRecords.subList(1, csvRecords.size());
            Iterator<CSVRecord> recordsIterator = csvRecords.iterator();

            while(recordsIterator.hasNext()){
                CSVRecord record = recordsIterator.next();
                String body = """{"\$class":"com.akres.LandTitle","""
                (0..columnNames.size() - 1).forEach{int index ->
                    def header = columnNames[index].trim()
                    def record_value = record[index]
                    body += """"$header":"$record_value","""
                }
                body = body.substring(0, body.length() - 1)
                body += "}"
                print(body)
                HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:3000/api/LandTitle")
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .body(body.toString())
                    .asJson();
                println()
                print jsonResponse.body.toString()
                break
            }

//            HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:3000/api/LandTitle")
//            .header("accept", "application/json")
//                    .header("Content-Type", "application/json")
//                    .header("Accept-Encoding", "gzip, deflate, br")
//                    .body(value.toString())
//                    .asJson();
//            System.out.println(jsonResponse.getBody());
//            System.out.println("lasd");
            System.out.println(Unirest.get("http://localhost:3000/api/LandTitle").asJson().getBody().toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(fileReader);
            closeResource(csvParser);
        }
    }
}


