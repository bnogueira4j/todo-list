package tech.ada.java.todolist.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public record AlteraTodoItemCompletoRequest(
        String titulo,
        String descricao,
        Boolean concluida,
        LocalDate prazoFinal
) {

    public AlteraTodoItemCompletoRequest(String titulo, String descricao, Boolean concluida, LocalDate prazoFinal) {
        this.titulo = Objects.requireNonNull(titulo, "Titulo é obrigatorio");
        this.descricao = Objects.requireNonNull(descricao, "Descricao é obrigatorio");
        this.concluida = Objects.requireNonNull(concluida, "Concluida é obrigatorio");
        this.prazoFinal = Objects.requireNonNull(prazoFinal, "Prazo Final é obrigatorio");
    }

/*    public <T>T myNotNull(T objeto) {
        if(objeto == null)
            throw new NullPointerException();
        return objeto;
    }*/
}
