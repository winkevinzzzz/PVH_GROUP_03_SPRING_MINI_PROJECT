CREATE TABLE Users (
                       user_id SERIAL PRIMARY KEY,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       profile_image TEXT
);
CREATE TABLE Otps (
                      otp_id SERIAL PRIMARY KEY,
                      otp_code VARCHAR(20) NOT NULL,
                      issued_at TIMESTAMP NOT NULL,
                      expiration TIMESTAMP NOT NULL,
                      verify BOOLEAN,
                      user_id INT REFERENCES Users(user_id)
);
CREATE TABLE Categories (
                            category_id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            description TEXT,
                            user_id INT REFERENCES Users(user_id)
);
CREATE TABLE Expenses (
                          expense_id SERIAL PRIMARY KEY,
                          amount DECIMAL NOT NULL,
                          description TEXT,
                          date DATE NOT NULL,
                          user_id INT REFERENCES Users(user_id),
                          category_id INT REFERENCES Categories(category_id)
);