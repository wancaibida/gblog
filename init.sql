CREATE ROLE blog WITH ENCRYPTED PASSWORD 'blog' CREATEDB LOGIN;
CREATE DATABASE blog_test WITH OWNER = blog;
