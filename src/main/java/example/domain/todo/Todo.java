package example.domain.todo;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

import lombok.*;

@Data
@Builder()
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity(immutable = true)
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String desc;
    private Boolean completed;

    public Todo changeTitle(String title) {
        return withTitle(title);
    }

    public Todo changeDesc(String desc) {
        return withDesc(desc);
    }

    public Todo toggleTodo() {
        return withCompleted(!completed);
    }

    public Boolean isActive() {
        return !completed;
    }

    public Boolean isCompleted() {
        return completed;
    }
}
