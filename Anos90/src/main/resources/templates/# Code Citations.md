# Code Citations

## License: Apache_2_0  
https://github.com/fr1nge333/fileSys/tree/8cfcfb2027e9e010ac80d7e1f78ad98dbc4a3eda/src/main/java/cn/lhs/filesys/controller/PageController.java

```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Add more mappings as needed
}
```

