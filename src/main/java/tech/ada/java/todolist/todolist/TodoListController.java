package tech.ada.java.todolist.todolist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.java.todolist.todoitem.TodoItem;
import tech.ada.java.todolist.todoitem.TodoItemRepository;
import tech.ada.java.todolist.todoitem.TodoItemRequest;

@RestController
public class TodoListController {

    private TodoListRepository todoListRepository;
    private TodoItemRepository todoItemRepository;

    public TodoListController(TodoListRepository todoListRepository, TodoItemRepository todoItemRepository) {
        this.todoListRepository = todoListRepository;
        this.todoItemRepository = todoItemRepository;
    }

    @PostMapping("/todo-list")
    public ResponseEntity<TodoList> cadastrarLista(@RequestBody TodoListRequest request) {
       var todoList = new TodoList();
       todoList.setNome(request.getNome());

//
//       var todoItems =  todoItemRepository.findAll();
//
//       todoList.setTodoItems(todoItems);

       var todoListSalvo =  todoListRepository.save(todoList);

       return ResponseEntity.ok(todoListSalvo);
    }
}
