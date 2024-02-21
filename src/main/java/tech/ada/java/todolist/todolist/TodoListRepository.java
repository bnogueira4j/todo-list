package tech.ada.java.todolist.todolist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.java.todolist.todolist.TodoList;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {

}
