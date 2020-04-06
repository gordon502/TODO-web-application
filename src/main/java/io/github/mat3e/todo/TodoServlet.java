package io.github.mat3e.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mat3e.lang.LangServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Todo", urlPatterns = "/api/todos/*")
public class TodoServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(TodoServlet.class);

    private TodoRepository repository;
    private ObjectMapper mapper;

    /**
     * Servlet container needs it.
     */
    public TodoServlet(){
        this(new TodoRepository(), new ObjectMapper());
    }

    public TodoServlet(TodoRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Got 'todos' GETrequest with parameters: " + req.getParameterMap());
        mapper.writeValue(resp.getOutputStream(), repository.findAll());
        resp.setContentType("application/json;charset=UTF-8");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Got 'todos' PUTrequest");
        Todo todo = null;
        try {
            var path = req.getPathInfo();
            var id = Integer.valueOf(path.substring(1, path.length()));
            mapper.writeValue(resp.getOutputStream(), repository.toggleTodo(id));
            resp.setContentType("application/json;charset=UTF-8");
        }
        catch (NumberFormatException e) {
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Todo newTodo = mapper.readValue(req.getInputStream(), Todo.class);
        Todo savedTodo = repository.addTodo(newTodo);
        resp.setContentType("application/json;charset=UTF-8");
        mapper.writeValue(resp.getOutputStream(), savedTodo);
    }
}
