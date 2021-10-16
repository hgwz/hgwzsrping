package org.spring.springboot.controller;

import redis.clients.jedis.Jedis;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;


/**
 */
@RestController
public class ConvertController{
    @RequestMapping(value = "/api/endcode/{str}", method = RequestMethod.GET)
    public String encoder(@PathVariable("str") String str) {
        return new sun.misc.BASE64Encoder().encode(str.getBytes());
    }

    @RequestMapping(value = "/api/decode/{str}", method = RequestMethod.GET)
    public String dencoder(@PathVariable("str") String str) {
        BASE64Decoder decoder = new BASE64Decoder();
        String result = "";
        try {
            result = new String(decoder.decodeBuffer(str));
        } catch (Exception e){
            result = "decode failed";
        }
        return result;
    }
}
