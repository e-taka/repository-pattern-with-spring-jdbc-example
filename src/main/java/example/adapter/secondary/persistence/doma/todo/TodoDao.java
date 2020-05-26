package example.adapter.secondary.persistence.doma.todo;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;
import org.springframework.transaction.annotation.Transactional;

import example.domain.todo.Todo;

@ConfigAutowireable
@Dao
public interface TodoDao {
	@Select
	List<Todo> findAll();
	@Select
	Optional<Todo> findById(Long id);
	@Select
	List<Todo> filterBy(Boolean completed);

	@Insert
	@Transactional
	Result<Todo> insert(Todo todo);

	@Update
	@Transactional
	Result<Todo> update(Todo todo);

	@Delete
	@Transactional
	Result<Todo> remove(Todo todo);
}
