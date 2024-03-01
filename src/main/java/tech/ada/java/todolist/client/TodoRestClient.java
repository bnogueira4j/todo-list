package tech.ada.java.todolist.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "todoRest", url = "https://dummyjson.com/todos")
public interface TodoRestClient {

    @GetMapping
    Result getAllTodos();
}
