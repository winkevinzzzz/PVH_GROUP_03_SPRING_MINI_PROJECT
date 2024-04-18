CREATE TABLE Users (
                       user_id UUID default uuid_generate_v4() PRIMARY KEY,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       profile_image TEXT
);
CREATE TABLE Otps (
                      otp_id UUID default uuid_generate_v4() PRIMARY KEY,
                      otp_code VARCHAR(20) NOT NULL,
                      issued_at TIMESTAMP NOT NULL,
                      expiration TIMESTAMP NOT NULL,
                      verify BOOLEAN,
                      user_id UUID REFERENCES Users(user_id)
);

CREATE TABLE Categories (
                            category_id UUID default uuid_generate_v4() PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            description TEXT,
                            user_id UUID REFERENCES Users(user_id)
);
CREATE TABLE Expenses (
                          expense_id UUID default uuid_generate_v4() PRIMARY KEY,
                          amount DECIMAL NOT NULL,
                          description TEXT,
                          date DATE NOT NULL,
                          user_id UUID REFERENCES Users(user_id),
                          category_id UUID REFERENCES Categories(category_id)
);

create extension if not exists "uuid-ossp";
