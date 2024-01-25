ALTER TABLE task ADD COLUMN priority_id int REFERENCES priorities(id);

UPDATE task SET priority_id = (SELECT id FROM priorities WHERE name = 'urgently');