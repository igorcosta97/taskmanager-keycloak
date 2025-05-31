package br.com.igorsilva.taskmanager.repositories;


import br.com.igorsilva.taskmanager.entities.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, String> {

}

