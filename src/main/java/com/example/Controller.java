package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sali on 12/5/16.
 */
@RestController
public class Controller
{
    @RequestMapping(value="/v1/info", method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    Info getInfo()
    {
        Info info = new Info();
        //
        info.setName("John Doe");

        return info;
    }
}
