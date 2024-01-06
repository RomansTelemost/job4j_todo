create table task
(
id SERIAL PRIMARY KEY,
description TEXT,
created TIMESTAMP,
done BOOLEAN
);