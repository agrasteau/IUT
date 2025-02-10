package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {

    private final List<Task> tasks = new ArrayList<>();

    // Ajouter une t�che
    public void addTask(String name, LocalDate dueDate) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom de la t�che ne peut pas �tre vide");
        }
        tasks.add(new Task(name, dueDate));
    }

    // R�cup�rer toutes les t�ches
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    // Filtrer les t�ches � compl�ter avant une certaine date
    public List<Task> getTasksDueBefore(LocalDate date) {
        return tasks.stream()
                .filter(task -> task.getDueDate().isBefore(date))
                .collect(Collectors.toList());
    }

    // Supprimer une t�che par son nom
    public boolean removeTask(String name) {
        return tasks.removeIf(task -> task.getName().equalsIgnoreCase(name));
    }

    // Classe interne pour repr�senter une t�che
    public static class Task {
        private final String name;
        private final LocalDate dueDate;

        public Task(String name, LocalDate dueDate) {
            this.name = name;
            this.dueDate = dueDate;
        }

        public String getName() {
            return name;
        }

        public LocalDate getDueDate() {
            return dueDate;
        }
    }
}
