package tech.ada.java.todolist.exemplo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.java.todolist.todoitem.TodoItem;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Long> {
}
