DELETE FROM task;
ALTER TABLE task ADD user_id int not null references users(id)