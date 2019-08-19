package hello;

import org.springframework.boot.*;
import org.springframework.stereotype.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    public String home(){
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleController.class, args);
    }

}
