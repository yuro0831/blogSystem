-- Create blogs table
CREATE TABLE blogs (
    id SERIAL PRIMARY KEY,             -- Auto-incrementing ID
    title VARCHAR(255) NOT NULL,       -- Blog title
    content TEXT NOT NULL,             -- Blog content
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Creation timestamp
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- Last updated timestamp
    deleted_at TIMESTAMP NULL          -- Deletion timestamp
);
