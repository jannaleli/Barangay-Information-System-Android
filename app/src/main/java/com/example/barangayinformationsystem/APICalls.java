package com.example.barangayinformationsystem;


import android.os.Message;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.MessageFormat;

import okhttp3.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.callback.Callback;

public class APICalls {

    public static void callLogin(String username, String password, residentCallback callback) {

        OkHttpClient client = new OkHttpClient();
        String url = String.format("https://mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com/prod/user/%s", username);



        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("User-Agent", "PostmanRuntime/7.15.2")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "c8b41cc5-f95a-4860-8c01-2f5a225ffa8b,4b1cb874-3443-4b1b-851b-23ddf2c78fa4")
                .addHeader("Host", "mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("cache-control", "no-cache")
                .build();


        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseBody response = client.newCall(request).execute().body();
            ResidentModel event = objectMapper.readValue(response.string(), ResidentModel.class);
            callback.onSuccess(event);


        } catch(Exception e){
                     // Always must return something
            callback.onError("Sorry");
        }


    }

    public static void callDocuments(String username, clearanceCallback callbackNow) {
        OkHttpClient client = new OkHttpClient();
        String url = String.format("https://mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com/prod/document/%s", username);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("User-Agent", "PostmanRuntime/7.15.2")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "27cce534-4d37-4148-bf78-1a97189be52c,7373e9ae-a55f-4882-a9dd-e70e54a49053")
                .addHeader("Host", "mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("cache-control", "no-cache")
                .build();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseBody response = client.newCall(request).execute().body();
            ClearanceModel[] clearance = objectMapper.readValue(response.string(), ClearanceModel[].class);


            callbackNow.onSuccess(clearance);

        } catch(Exception e){
            // Always must return something
            callbackNow.onError("Error");
        }
    }


    public static void callPermit(String username, permitCallback callback){
        OkHttpClient client = new OkHttpClient();
        String url = String.format("https://mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com/prod/permit/%s", username);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("User-Agent", "PostmanRuntime/7.15.2")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "7580346d-6b6e-4393-8f71-339cae587e49,23667d63-2839-467c-9875-8b3cad50b565")
                .addHeader("Host", "mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("cache-control", "no-cache")
                .build();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseBody response = client.newCall(request).execute().body();
            PermitModel[] event = objectMapper.readValue(response.string(), PermitModel[].class);
            callback.onSuccess(event);
        } catch(Exception e){
            // Always must return something
            callback.onError("Error");
        }
    }

    public static void sendDocuments(String user_id, String username, String expiration_date, String government_id,
                                     String attachment_id, String reason, String status, generalCallback callback){
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        String content = String.format("{\n    \"user_id\": \""+user_id+"\",\n    \"username\": \""+username+"\",\n    \"expiration_date\": \""+expiration_date+"\",\n    \"government_id\": \""+government_id+"\",\n    \"attachment_id\": \""+attachment_id+"\",\n    \"reason\": \""+reason+"\",\n    \"status\":\""+status+"\"\n}");
               /* user_id, username, expiration_date, government_id, attachment_id, reason, status);*/

        RequestBody body = RequestBody.create(mediaType, content);

        Request request = new Request.Builder()
                .url("https://mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com/prod/document")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Content-Length", "682")
                .addHeader("Connection", "keep-alive")
                .build();

        try{
            Response response = client.newCall(request).execute();
           if(response.code() == 200) {
               callback.onSuccess();
           }else {
               callback.onError("Sorry");
           }
        } catch(Exception e){
            // Always must return something
            callback.onError("Sorry");

        }

    }

    public static void sendPermit(String user_id, String username, String attachment_id, String sec_no, String business_building_no,
                                  String business_street, String business_activity, String business_name, String capitalization, String ctc_no,
                                  String lessor_barangay, String lessor_bldg_no, String lessor_city, String lessor_emailaddr, String lessor_name,
                                  String lessor_province, String lessor_street, String lessor_subdv, String monthly_rental, String status, String gross_sale,
                                  String approval_date, generalCallback callback){
        OkHttpClient client = new OkHttpClient();
        String content = String.format("{\n    \"user_id\": \""+user_id+"\",\n    \"username\": \""+username+"\",\n    \"attachment_id\":\""+attachment_id+"\",\n    \"sec_no\": \""+sec_no+"\",\n    \"business_building_no\": \""+business_building_no+"\",\n    \"business_street\": \""+business_street+"\",\n    \"business_activity\": \""+business_activity+"\",\n    \"business_name\": \""+business_name+"\",\n    \"capitalization\": \""+capitalization+"\",\n    \"ctc_no\": \""+ctc_no+"\",\n    \"lessor_barangay\": \""+lessor_barangay+"\",\n    \"lessor_bldg_no\": \""+lessor_bldg_no+"\",\n    \"lessor_city\": \""+lessor_city+"\",\n    \"lessor_emailaddr\": \""+lessor_emailaddr+"\",\n    \"lessor_name\": \""+lessor_name+"\",\n    \"lessor_province\": \""+lessor_province+"\",\n    \"lessor_street\": \""+lessor_street+"\",\n    \"lessor_subdv\": \""+lessor_subdv+"\",\n    \"monthly_rental\": \""+monthly_rental+"\",\n    \"status\": \""+status+"\",\n    \"gross_sale\": \""+gross_sale+"\",\n    \"approval_date\": \""+approval_date+"\"\n}");/*,
                user_id, username, attachment_id, sec_no, business_building_no, business_street, business_activity, business_name, capitalization, ctc_no, lessor_barangay,
                lessor_bldg_no, lessor_city, lessor_emailaddr, lessor_province, lessor_street, lessor_subdv, monthly_rental, status, gross_sale, approval_date);*/
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("https://mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com/prod/permit")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Content-Length", "682")
                .addHeader("Connection", "keep-alive")
                .build();


        try{
            Response response = client.newCall(request).execute();
            if(response.code() == 200) {
                callback.onSuccess();
            }else {
                callback.onError("Sorry");
            }
        } catch(Exception e){
            // Always must return something
            callback.onError("Sorry");

        }
    }

    public static void postComplaint(String complaint_id,
                                        String attachment_id,
                                        String complaint_desc,
                                        String latitude,
                                        String longitude,
                                        String type,
                                        String user_id,
                                        String status,
                                        String create_date,
                                        generalCallback callback){
        OkHttpClient client = new OkHttpClient();
        String content = String.format("{\n    \"complaint_id\": \""+complaint_id+"\",\n    \"attachment_id\": \""+attachment_id+"\",\n    \"complaint_desc\": \""+complaint_desc+"\",\n    \"latitude\":\""+latitude+"\",\n    \"longitude\": \""+longitude+"\",\n    \"type\": \""+type+"\",\n    \"user_id\": \""+user_id+"\",\n    \"status\": \""+status+"\",\n    \"create_date\": \""+create_date+"\"\n}");
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("https://mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com/prod/complaint")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Host", "mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Content-Length", "682")
                .addHeader("Connection", "keep-alive")
                .build();




        try{
            Response response = client.newCall(request).execute();
            if(response.code() == 200) {
                callback.onSuccess();
            }else {
                callback.onError("Sorry");
            }
        } catch(Exception e){
            // Always must return something
            callback.onError("Sorry");

        }
    }

    public static void getComplaints(complaintCallback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com/prod/complaint")
                .get()
                .addHeader("User-Agent", "PostmanRuntime/7.15.2")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "9c1b332a-9f6e-4179-9c2c-8a7f42bf80f3,49052a36-ccaa-47c6-9f61-ed8e65657346")
                .addHeader("Host", "mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("cache-control", "no-cache")
                .build();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseBody response = client.newCall(request).execute().body();
            ComplaintModel[] complaint = objectMapper.readValue(response.string(), ComplaintModel[].class);
            callback.onSuccess(complaint);
        } catch(Exception e){
            // Always must return something
           callback.onError("Error");
        }

    }

    public static void registerResident(String user_id, String username, String password, String address, String birthdate,
                                        String create_date, String firstname, String lastname, String mobilenumber, String zipcode,
                                        String civil_status, String ctc_no, String tin_no, String place_of_birth, String weight,
                                        String height, String control_no, String profession, String gross_income, String attachment_id,
                                        String gender, String status, generalCallback callback){
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        String content = String.format("{\n    \"user_id\": \""+user_id+"\",\n    \"username\": \""+username+"\",\n    \"password\": \""+password+"\",\n    \"address\": \""+address+"\",\n    \"birthdate\": \""+birthdate+"\",\n    \"createdate\": \""+create_date+"\",\n    \"firstname\":\""+firstname+"\",\n    \"lastname\": \""+lastname+"\",\n    \"mobilenumber\": \""+mobilenumber+"\",\n    \"zipcode\": \""+zipcode+"\",\n    \"civil_status\":\""+civil_status+"\",\n    \"ctc_no\": \""+ctc_no+"\",\n    \"tin_no\": \""+tin_no+"\",\n    \"place_of_birth\": \""+place_of_birth+"\",\n    \"weight\": \""+weight+"\",\n    \"height\":\""+height+"\",\n    \"control_no\": \""+control_no+"\",\n    \"contact_no\":\""+mobilenumber+"\",\n    \"profession\": \""+profession+"\",\n    \"gross_income\":\""+gross_income+"\",\n    \"attachment_id\": \""+attachment_id+"\",\n    \"gender\": \""+gender+"\",\n    \"status\": \""+status+"\"\n}");

                /*,user_id, username, password, address, birthdate, create_date, firstname, lastname, mobilenumber, zipcode, civil_status,
                ctc_no, tin_no, place_of_birth, weight, height, control_no, profession, gross_income, attachment_id, gender, status);*/

//        RequestBody body = RequestBody.create(mediaType, "{\n    \"user_id\": user_id,\n    " +
//                "\"username\": username,\n   " +
//                " \"password\": password,\n   " +
//                " \"address\": address,\n    " +
//                "\"birthdate\": birthdate,\n    " +
//                "\"createdate\": create_date,\n   " +
//                " \"firstname\": firstname,\n    " +
//                "\"lastname\": lastname,\n    " +
//                "\"mobilenumber\": mobilenumber,\n   " +
//                " \"zipcode\": zipcode,\n    " +
//                "\"civil_status\": civil_status,\n    " +
//                "\"ctc_no\": ctc_no,\n   " +
//                " \"tin_no\": tin_no,\n    " +
//                "\"place_of_birth\": place_of_birth,\n    " +
//                "\"weight\": weight,\n    " +
//                "\"height\": height,\n    " +
//                "\"control_no\": control_no,\n    " +
//                "\"contact_no\":mobilenumber,\n    " +
//                "\"profession\": profession,\n   " +
//                " \"gross_income\": gross_income,\n    " +
//                "\"attachment_id\": attachment_id,\n    " +
//                "\"gender\": gender,\n    " +
//                "\"status\": status\n}");
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("https://mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com/prod/user")
                .post(body)
                .addHeader("Accept", "*/*")
                .addHeader("Host", "mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .build();


        try{
            Response response = client.newCall(request).execute();
            if(response.code() == 200) {
                callback.onSuccess();
            }else {
                callback.onError("Sorry");
            }
        } catch(Exception e){
            // Always must return something
            callback.onError("Sorry");

        }

    }

    public static void getEvents(eventCallback callback){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com/prod/event")
                .get()
                .addHeader("User-Agent", "PostmanRuntime/7.15.2")
                .addHeader("Accept", "*/*")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "fd0456a0-e056-4ec4-bdab-8dc3089d5e36,cb94d5ad-20cf-4eb0-b7f9-f46412ff536f")
                .addHeader("Host", "mjdjlvb5x9.execute-api.ap-southeast-1.amazonaws.com")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("cache-control", "no-cache")
                .build();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseBody response = client.newCall(request).execute().body();
            EventModel[] event = objectMapper.readValue(response.string(), EventModel[].class);
            callback.onSuccess(event);
        } catch(Exception e){
            // Always must return something
            callback.onError("Sorry");
        }

    }
}
