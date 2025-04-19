-- we are using PostregSQL 16.4
-- CSV Header is below

-- bookId,title,author,rating,description,language,isbn,bookFormat,edition,pages,publisher,publishDate,firstPublishDate,likedPercent,price

-- Create the books table

CREATE TABLE books (
    book_id SERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL,
    rating DECIMAL(3, 2) NOT NULL, 
    description TEXT NOT NULL,
    language VARCHAR(50) NOT NULL,
    isbn VARCHAR(20) NOT NULL,
    book_format VARCHAR(50) NOT NULL,
    edition VARCHAR(50) NOT NULL,
    pages INT NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    publish_date DATE NOT NULL,
    first_publish_date DATE NOT NULL,
    liked_percent DECIMAL(5, 2) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Create the authors table

CREATE TABLE authors (
    author_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);
-- Create the book_authors table

CREATE TABLE book_authors (
    book_id INT NOT NULL,
    author_id INT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors(author_id) ON DELETE CASCADE
);