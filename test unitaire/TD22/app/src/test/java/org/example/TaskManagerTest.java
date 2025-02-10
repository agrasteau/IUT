package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    void addTask_validTask_taskAdded() {
        taskManager.addTask("Tâche 1", LocalDate.now());
        assertThat(taskManager.getAllTasks()).hasSize(1);
        assertThat(taskManager.getAllTasks().get(0).getName()).isEqualTo("Tâche 1");
    }

    @Test
    void addTask_emptyName_throwsException() {
        assertThatThrownBy(() -> taskManager.addTask("", LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le nom de la tâche ne peut pas être vide");
    }

    @Test
    void addTask_nullName_throwsException() {
        assertThatThrownBy(() -> taskManager.addTask(null, LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Le nom de la tâche ne peut pas être vide");
    }

    @Test
    void getAllTasks_tasksExist_returnsTaskList() {
        taskManager.addTask("Tâche 1", LocalDate.now());
        taskManager.addTask("Tâche 2", LocalDate.now());
        assertThat(taskManager.getAllTasks()).hasSize(2);
    }

    @Test
    void getAllTasks_noTasks_returnsEmptyList() {
        assertThat(taskManager.getAllTasks()).isEmpty();
    }

    @Test
    void getTasksDueBefore_tasksExist_returnsFilteredList() {
        taskManager.addTask("Tâche 1", LocalDate.now().plusDays(1));
        taskManager.addTask("Tâche 2", LocalDate.now().minusDays(1));
        List<TaskManager.Task> tasks = taskManager.getTasksDueBefore(LocalDate.now());
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getName()).isEqualTo("Tâche 2");
    }

    @Test
    void getTasksDueBefore_noMatchingTasks_returnsEmptyList() {
        taskManager.addTask("Tâche 1", LocalDate.now().plusDays(1));
        assertThat(taskManager.getTasksDueBefore(LocalDate.now())).isEmpty();
    }

    @Test
    void removeTask_taskExists_taskRemoved() {
        taskManager.addTask("Tâche 1", LocalDate.now());
        assertThat(taskManager.removeTask("Tâche 1")).isTrue();
        assertThat(taskManager.getAllTasks()).isEmpty();
    }

    @Test
    void removeTask_taskExistsCaseInsensitive_taskRemoved() {
        taskManager.addTask("Tâche 1", LocalDate.now());
        assertThat(taskManager.removeTask("tâche 1")).isTrue();
        assertThat(taskManager.getAllTasks()).isEmpty();
    }

    @Test
    void removeTask_taskDoesNotExist_returnsFalse() {
        assertThat(taskManager.removeTask("Tâche 1")).isFalse();
    }
}