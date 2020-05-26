package example.adapter.secondary.persistence.jdbc.todo;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import example.domain.todo.NewTodo;
import example.domain.todo.Todo;
import example.domain.todo.TodoFilter;
import example.domain.todo.TodoRepository;

//@Repository
public class JdbcTodoRepository implements TodoRepository {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Todo> findById(Long id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        return jdbcTemplate.query(
            "select * from todo where id = :id",
            param,
            new BeanPropertyRowMapper<>(Todo.class)
        ).stream().findFirst();
    }

    @Override
    public List<Todo> findAll() {
        return jdbcTemplate.query(
            "select * from todo",
            new BeanPropertyRowMapper<>(Todo.class)
        );
    }

    @Override
    public List<Todo> filterBy(TodoFilter filter) {
        String query = "select * from todo"
                + (
                      filter == TodoFilter.Active ? " where completed = false"
                    : filter == TodoFilter.Completed ? " where completed = true"
                    : ""
                  );

        return jdbcTemplate.query(
                query,
                new BeanPropertyRowMapper<>(Todo.class)
        );
    }

    @Override
    public Todo save(NewTodo todo) {
        SqlParameterSource param =
                new MapSqlParameterSource()
                        .addValue("title", todo.getTitle())
                        .addValue("desc", todo.getDesc())
                        .addValue("completed", todo.getCompleted());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            "insert into todo (`title`, `desc`, `completed`) values (:title, :desc, :completed)",
            param,
            keyHolder
        );

        Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();

        return new Todo(id, todo.getTitle(), todo.getDesc(), todo.getCompleted());
    }

    @Override
    public Todo save(Todo todo) {
        SqlParameterSource param =
                new MapSqlParameterSource()
                        .addValue("id", todo.getId())
                        .addValue("title", todo.getTitle())
                        .addValue("desc", todo.getDesc())
                        .addValue("completed", todo.getCompleted());

        jdbcTemplate.update(
            "update todo set `title` = :title, `desc` = :desc, `completed` = :completed where `id` = :id",
            param
        );

        return todo;
    }

    @Override
    public void remove(Long id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

        jdbcTemplate.update(
            "delete from todo where id = :id",
            param
        );
    }
}
