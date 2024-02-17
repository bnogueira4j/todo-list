package tech.ada.java.todolist.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    List<TodoItem> findByTitulo(String titulo);

    @Query(value = "SELECT t FROM TodoItem t WHERE t.titulo = ?1")
    List<TodoItem> findByTituloQuery(String titulo);

    @Query(value = "SELECT * FROM todo_item WHERE titulo = ?1", nativeQuery = true)
    List<TodoItem> findByTituloQueryNativa(String titulo);
}
