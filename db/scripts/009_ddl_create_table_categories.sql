CREATE TABLE categories (
    id serial primary key,
    task_id int not null references task(id),
    category_id int not null references category(id),
    UNIQUE(task_id, category_id)
)