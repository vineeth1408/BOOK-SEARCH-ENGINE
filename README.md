
Book Search API
This is a course project for the LinkedIn Learning Course.

Modules Overview
Module 2: Setting Up Java, Maven, and Spring Boot
Initial setup for the project.
Module 3: Dockerizing the Project with PostgreSQL
Start container
bash
docker-compose up -d
Connect to the database
bash
docker exec -it library-db psql -U admin -d library
Verify version
SQL
SELECT version();
List databases
SQL
\l
Stop container
bash
docker-compose down
Test the connection between the Java application and PostgreSQL
bash
mvn spring-boot:run
Module 4: Designing the Database Schema and Implementing Full-Text Search
Create network
bash
docker network create db-network
Connect library-db to the network
bash
docker network connect db-network library-db
Setup PGAdmin
Pull the PGAdmin image:

bash
docker pull dpage/pgadmin4
Run the container:

bash
docker run -p 80:80 -e PGADMIN_DEFAULT_EMAIL=user@domain.com -e PGADMIN_DEFAULT_PASSWORD=SuperSecret --name pgadmin --network db-network -d dpage/pgadmin4
Alternatively, run with the default network:

bash
docker run -p 80:80 -e PGADMIN_DEFAULT_EMAIL=user@domain.com -e PGADMIN_DEFAULT_PASSWORD=SuperSecret --name pgadmin --network book-search_default -d dpage/pgadmin4
Visit http://localhost:80 in your browser.

Create tables and relationships
Copy the schema file:
bash
docker cp db/create_schema.sql library-db:/create_schema.sql
Execute the schema file:
bash
docker exec -it library-db psql -U admin -d library -f /create_schema.sql
Querying the Database
Basic Queries
Case-insensitive search
SQL
SELECT *
FROM books
WHERE title ILIKE '%algorithms%'
   OR description ILIKE '%algorithms%';
Full-text search
SQL
INSERT INTO books (title, rating, description, language, isbn, book_format, edition, pages, publisher, publish_date, first_publish_date, liked_percent, price)
VALUES
('Introduction to Algorithms', 4.5, 'A comprehensive update of the leading algorithms text, with new material on matchings in bipartite graphs, online algorithms, machine learning, and other topics.', ...),
('Clean Code: A Handbook of Agile Software Craftsmanship', 4.4, 'Even bad code can function. But if code isn''t clean, it can bring a development organization to its knees. This book is a must for any...', ...),
('Design Patterns: Elements of Reusable Object-Oriented Software', 4.2, 'Capturing a wealth of experience about the design of object-oriented software, four top-notch designers present a catalog of si...', ...),
('The Pragmatic Programmer: Your Journey to Mastery', 4.4, 'The Pragmatic Programmer is one of those rare tech books you''ll read, re-read, and read again over the years. Whether you''re new to the fi...', ...);
Full-Text Search with PostgreSQL
Setup
Add a column for the search vector:

SQL
ALTER TABLE books ADD COLUMN search_vector tsvector;
Update the column:

SQL
UPDATE books SET search_vector =
    setweight(to_tsvector('english', coalesce(title,'')), 'A') ||
    setweight(to_tsvector('english', coalesce(description,'')), 'B') ||
    setweight(to_tsvector('english', coalesce(isbn,'')), 'C');
Examples
Convert text to a tsvector:

SQL
SELECT to_tsvector('english', 'Introduction to Algorithms');
Use to_tsquery with a single word:

SQL
SELECT to_tsquery('english', 'algorithms');
Rank search results:

SQL
SELECT title, ts_rank(search_vector, to_tsquery('english', 'algorithms')) as rank
FROM books
WHERE search_vector @@ to_tsquery('english', 'algorithms')
ORDER BY rank DESC;
Phrase search:

SQL
SELECT title, ts_rank(search_vector, phraseto_tsquery('english', 'object-oriented software')) as rank
FROM books
WHERE search_vector @@ phraseto_tsquery('english', 'object-oriented software')
ORDER BY rank DESC;
Complex query with multiple terms:

SQL
SELECT title, ts_rank(search_vector, to_tsquery('english', 'design & (patterns | algorithms) & !pragmatic')) as rank
FROM books
WHERE search_vector @@ to_tsquery('english', 'design & (patterns | algorithms) & !pragmatic')
ORDER BY rank DESC;
