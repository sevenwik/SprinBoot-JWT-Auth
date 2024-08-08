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
    @GetMapping("/token")
    public void getToken(@RequestBody String username, @RequestBody String password) {

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
            output = postNewCustomerToCustomerAPI(out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
       if(output.getStatusCode().value() == 200){
           return new ResponseEntity<>("User created and you can Sign in",HttpStatusCode.valueOf(200));

       }
           return new  ResponseEntity<>("",HttpStatusCode.valueOf(output.getStatusCode().value()));


    }

    private ResponseEntity<Object> postNewCustomerToCustomerAPI(String json_string) throws Exception {
        //try{
        URL url = new URL("http://localhost:8080/api/customers");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        Token token = TokenAPI.getAppUserToken();
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

    /**} catch (MalformedURLException e) {
        return new  ResponseEntity<>(e.toString(),HttpStatusCode.valueOf(500));
    } catch (IOException e) {
            return new  ResponseEntity<>(e.toString(),HttpStatusCode.valueOf(500));
    }catch (Exception e){
            return new  ResponseEntity<>(e.toString(),HttpStatusCode.valueOf(statusCode));
        }*/
    }

}
