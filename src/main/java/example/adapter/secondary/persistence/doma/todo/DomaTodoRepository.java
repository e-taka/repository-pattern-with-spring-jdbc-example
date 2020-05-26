package example.adapter.secondary.persistence.doma.todo;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.jdbc.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import example.domain.todo.NewTodo;
import example.domain.todo.Todo;
import example.domain.todo.TodoFilter;
import example.domain.todo.TodoRepository;

@Repository
public class DomaTodoRepository implements TodoRepository {
	@Autowired
	private TodoDao todoDao;

	@Override
	public Optional<Todo> findById(Long id) {
		return todoDao.findById(id);
	}

	@Override
	public List<Todo> findAll() {
		return todoDao.findAll();
	}

	@Override
	public List<Todo> filterBy(TodoFilter filter) {
		Boolean completed = null;
		if (TodoFilter.Active.equals(filter)) {
			completed = Boolean.FALSE;
		} else if (TodoFilter.Completed.equals(filter)) {
			completed = Boolean.TRUE;
		}
		return todoDao.filterBy(completed);
	}

	@Override
	public Todo save(NewTodo todo) {
		Todo newTodo = new Todo(null, todo.getTitle(), todo.getDesc(), todo.getCompleted());
		Result<Todo> result = todoDao.insert(newTodo);
		return result.getEntity();
	}

	@Override
	public Todo save(Todo todo) {
		Result<Todo> result = todoDao.update(todo);
		return result.getEntity();
	}

	@Override
	public void remove(Long id) {
		Todo todo = new Todo();
		todo.setId(id);
		todoDao.remove(todo);
	}
}
