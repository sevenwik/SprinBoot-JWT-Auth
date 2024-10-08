package com.api.jwt_auth.controller;

import com.api.jwt_auth.models.Customer;
import com.api.jwt_auth.models.Token;

import org.json.JSONObject;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AuthController {
    @PostMapping("/token")
    public ResponseEntity<Object> getToken(@RequestBody Customer cust) {
        JSONObject jo = new JSONObject();

        jo.put("name", cust.getName());
        jo.put("email", cust.getName());
        jo.put("password", cust.getPassword());
        jo.put("id", cust.getID());

        String out = jo.toString();

        ResponseEntity<Object> output = null;
        try {
            output = checkCustomerCreds(out, cust.getName());
            return output;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody Customer cust) throws IOException {
        if(cust.getEmail() ==  null || cust.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        JSONObject jo = new JSONObject();

        jo.put("name", cust.getName());
        jo.put("email", cust.getEmail());
        jo.put("password", cust.getPassword());
        jo.put("id", cust.getID());

        String out = jo.toString();

        ResponseEntity<Object> output = null;
        try {
            output = postCustomerDetailsRequest(out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
       if(output.getStatusCode().value() == 200){
           return new ResponseEntity<>("User created and you can sign-in",HttpStatusCode.valueOf(200));

       }
       if(output.getStatusCode().value() == 409) {
           return new ResponseEntity<>("User already exists", HttpStatusCode.valueOf(output.getStatusCode().value()));
       }
        return new ResponseEntity<>("Something went wrong", HttpStatusCode.valueOf(output.getStatusCode().value()));

    }

    private ResponseEntity<Object> postCustomerDetailsRequest(String json_string) throws Exception {
        //try{
        URL url = new URL("http://localhost:8080/api/customers");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        Token token = TokenAPI.getAppUserToken("admin");
        conn.setRequestProperty("authorization", "Bearer " + token.getToken());

        OutputStream os = conn.getOutputStream();
        os.write(json_string.getBytes());
        os.flush();

        String msg = conn.getResponseMessage();
        int statusCode = conn.getResponseCode();
        conn.disconnect();

        if (statusCode != 200) {
            return new ResponseEntity<>( HttpStatusCode.valueOf(statusCode));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(statusCode));
    }

    private ResponseEntity<Object> checkCustomerCreds(String json_string, String userName) throws Exception {
        //try{
        URL url = new URL("http://localhost:8080/api/customers/byEmail");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        Token token = TokenAPI.getAppUserToken(userName);
        conn.setRequestProperty("authorization", "Bearer " + token.getToken());

        OutputStream os = conn.getOutputStream();
        os.write(json_string.getBytes());
        os.flush();

        String msg = conn.getResponseMessage();
        int statusCode = conn.getResponseCode();

        if (statusCode != 200) {
            return new ResponseEntity<>("customer does not exist", HttpStatusCode.valueOf(statusCode));
        }
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String output = "";
        String out = "";
        while ((out = br.readLine()) != null) {
            output += out;
        }
        conn.disconnect();
        JSONObject jo = new JSONObject(output);
        JSONObject formData = new JSONObject(json_string);
        if (jo.get("password").equals(formData.get("password"))) {
            return new ResponseEntity<>(token, HttpStatusCode.valueOf(200));
        } else {
            return new ResponseEntity<>("Forbidden access, incorrect password", HttpStatusCode.valueOf(403));
        }
    }

}
