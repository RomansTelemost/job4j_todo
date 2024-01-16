create table task
(
id SERIAL PRIMARY KEY,
title varchar,
description TEXT,
created TIMESTAMP,
done BOOLEAN
);