package pl.devzyra.mvccontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexMvcController {

    @GetMapping({"", "/", "/index"})
    public String index() {

        return "index";
    }

}
