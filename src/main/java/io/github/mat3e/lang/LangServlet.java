package io.github.mat3e.lang;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import java.util.List;

@RestController
@RequestMapping("/api")
class LangServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(LangServlet.class);
    private LangService service;

    LangServlet(LangService service, ObjectMapper mapper) {
        this.service = service;
    }

   @GetMapping("/langs")
   ResponseEntity<List<LangDTO>> findAllLangs() {
        logger.info("Got LANGS request with parameters");
        return ResponseEntity.ok(service.findAll());
    }
}
