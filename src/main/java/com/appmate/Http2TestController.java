package com.appmate;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by uujc0207 on 2017. 3. 28..
 */
@RestController
public class Http2TestController {

    @RequestMapping("/test")
    public String test(){
        return "@Test@Test@Test@Test@Test@Test@Test@Test@Test@Test@Test@Test@Test@Test@Test@Test@Test\n";
    }
}
