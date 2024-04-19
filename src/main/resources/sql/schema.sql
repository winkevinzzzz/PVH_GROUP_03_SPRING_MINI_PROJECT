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
                          date timestamp NOT NULL,
                          user_id UUID REFERENCES Users(user_id),
                          category_id UUID REFERENCES Categories(category_id)
);

create extension if not exists "uuid-ossp";

select * from otps where otp_code = '975523'
SELECT o.*, u.email -- Select all Otp columns (o.*) and username from User (u)
FROM otps o
         INNER JOIN users u ON o.user_id = u.user_id -- Join otps and users on user_id
WHERE o.user_id ='767d5fc6-876b-4bfd-bdd9-e34155cc04e2'
