package com.example.newMock.Controller;


import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MainController {
    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();
    public long start_time = 0L;

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO){
      try {
            String clientid = requestDTO.getClientId();
            char firstDigit = clientid.charAt(0);
            BigDecimal maxLimit;
            String currency = null;
            double balance;

            if (firstDigit == '8') {
                maxLimit = new BigDecimal(2000);
                currency = new String("US");
                balance = (int)(Math.random()*2000);

            } else if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000);
                currency = new String("EU");
                double balance_1 = (int)(Math.random()*1000);
                balance = Math.floor(balance_1);
            } else {
                maxLimit = new BigDecimal(10000);
                currency = new String("RUB");
                double balance_2 = (int)(Math.random()*10000);
                balance = Math.round(balance_2);

            }

            String RqUID = requestDTO.getRqUID(); //первый способ получения ID

            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(RqUID); //второй способ получения ID
            responseDTO.setClientId(clientid);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxLimit);

            log.error("***** Запрос ***** " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("***** Запрос ***** " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            long pacing = ThreadLocalRandom.current().nextLong(100,500);
            long end_time = System.currentTimeMillis();
            if (end_time - start_time < pacing)
                Thread.sleep(pacing - (end_time - start_time));

            return responseDTO;
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }

    }
    @GetMapping(
            value = "/info/getBalances",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Object getBalances(@RequestBody RequestDTO requestDTO){
        try {
            String clientid = requestDTO.getClientId();
            char firstDigit = clientid.charAt(0);
            BigDecimal maxLimit;
            String currency = null;
            double balance;

            if (firstDigit == '8') {
                maxLimit = new BigDecimal(2000);
                currency = new String("US");
                balance = (int)(Math.random()*2000);
            } else if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000);
                currency = new String("EU");
                balance = (int)(Math.random()*1000);
            } else {
                maxLimit = new BigDecimal(10000);
                currency = new String("RUB");
                balance = (int)(Math.random()*10000);
            }

            String RqUID = requestDTO.getRqUID(); //первый способ получения ID

            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(RqUID); //второй способ получения ID
            responseDTO.setClientId(clientid);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxLimit);

            log.error("***** Запрос ***** " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("***** Запрос ***** " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
