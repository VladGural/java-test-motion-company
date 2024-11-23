CREATE TABLE company (
    id                      varchar(36)     NOT NULL,
    name                    text            NOT NULL,
    status                  text            NOT NULL,
    contact_information     text,
    industry                text,
    PRIMARY KEY (id)
);

CREATE TABLE company_address (
    id                      varchar(36)     NOT NULL,
    company_id              varchar(36)     NOT NULL,
    country                 text,
    city                    text,
    street                  text,
    zip                     text,
    category                text,
    PRIMARY KEY (id),
    CONSTRAINT fk_company_address_company FOREIGN KEY (company_id) REFERENCES company (id)
);
