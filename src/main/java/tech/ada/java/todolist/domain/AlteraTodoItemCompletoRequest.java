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
        LocalDateTime dataHora,
        LocalDate prazoFinal
) {

    public AlteraTodoItemCompletoRequest(String titulo, String descricao, Boolean concluida, LocalDateTime dataHora, LocalDate prazoFinal) {
        this.titulo = Objects.requireNonNull(titulo, "Titulo é obrigatorio");
        this.descricao = Objects.requireNonNull(descricao);
        this.concluida = Objects.requireNonNull(concluida);
        this.dataHora = Objects.requireNonNull(dataHora);
        this.prazoFinal = Objects.requireNonNull(prazoFinal);
    }

/*    public <T>T myNotNull(T objeto) {
        if(objeto == null)
            throw new NullPointerException();
        return objeto;
    }*/
}
