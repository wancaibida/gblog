CREATE ROLE blog WITH ENCRYPTED PASSWORD 'blog' CREATEDB LOGIN;
CREATE DATABASE blog WITH OWNER = blog;
