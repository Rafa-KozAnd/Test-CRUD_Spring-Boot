import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNotFound() {
        return new ResponseEntity<>("Página não encontrada", HttpStatus.NOT_FOUND);
    }
}
