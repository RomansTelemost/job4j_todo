package ru.job4j.todo.util;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.ZoneId;
import java.util.Collection;
import java.util.TimeZone;

public final class TimeFormatter {

    private TimeFormatter() {
    }

    public static Collection<Task> convertTimeByTimezone(Collection<Task> tasks, User user) {
        for (Task task : tasks) {
            task.setCreated(task.getCreated()
                    .atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(
                            ZoneId.of(user == null ? TimeZone.getDefault().getID() : user.getTimezone())
                    ).toLocalDateTime());
        }
        return tasks;
    }
}
