CREATE DATABASE testdevsu WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';

-- public.type_account definition

-- Drop table

-- DROP TABLE public.type_account;

CREATE TABLE IF NOT EXISTS public.type_account (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" varchar NOT NULL,
	enabled bool DEFAULT true NOT NULL,
	CONSTRAINT type_account_pk PRIMARY KEY (id),
	CONSTRAINT type_account_unique UNIQUE (name)
);

INSERT INTO public.type_account ("name", enabled) VALUES('SAVING', true);
INSERT INTO public.type_account ("name", enabled) VALUES('CURRENT', true);

-- public.type_transaction definition

-- Drop table

-- DROP TABLE public.type_transaction;

CREATE TABLE IF NOT EXISTS public.transaction_type (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"name" varchar NOT NULL,
	enabled bool DEFAULT true NOT NULL,
	CONSTRAINT type_transaction_pk PRIMARY KEY (id),
	CONSTRAINT type_transaction_unique UNIQUE (name)
);

INSERT INTO public.transaction_type ("name", enabled) VALUES('CREDIT', true);
INSERT INTO public.transaction_type ("name", enabled) VALUES('DEBIT', true);

-- public.person definition

-- Drop table

-- DROP TABLE public.person;

CREATE TABLE IF NOT EXISTS public.person (
    id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"identity" varchar UNIQUE NOT NULL,
	"name" varchar NOT NULL,
	phone varchar NULL,
	direction varchar NULL,
	age int4 NULL,
	country_code int4 DEFAULT 57 NOT NULL,
	enabled bool DEFAULT true NOT NULL,
    created_date timestamp default now() not null,
    last_modified_date timestamp default now() not null,
    CONSTRAINT person_pk PRIMARY KEY (id)
);

-- public.client definition

-- Drop table

-- DROP TABLE public.client;

CREATE TABLE IF NOT EXISTS public.client (
    "id" int4 PRIMARY KEY NOT NULL,
	"password" varchar NOT NULL,
    created_date timestamp default now() not null,
    last_modified_date timestamp default now() not null,
	CONSTRAINT fk_client_person FOREIGN KEY (id) REFERENCES public."person"(id)
);

-- public.account definition

-- Drop table

-- DROP TABLE public.account;

CREATE TABLE IF NOT EXISTS public.account (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	"number" varchar UNIQUE NOT NULL,
	opening_balance numeric NOT NULL,
	balance numeric default 0 NOT NULL,
	description varchar NULL,
	state varchar NULL,
	client_id int4 NOT NULL,
	type_account_id int4 NOT NULL,
	enabled bool DEFAULT true NOT NULL,
    created_date timestamp default now() not null,
    last_modified_date timestamp default now() not null,
	CONSTRAINT product_pk PRIMARY KEY (id),
	CONSTRAINT fk_account_client FOREIGN KEY (client_id) REFERENCES public."client"(id),
	CONSTRAINT fk_account_type FOREIGN KEY (type_account_id) REFERENCES public."type_account"(id)
);


-- public.transaction definition

-- Drop table

-- DROP TABLE public.transaction;

CREATE TABLE IF NOT EXISTS public.transaction (
	id int4 GENERATED ALWAYS AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START 1 CACHE 1 NO CYCLE) NOT NULL,
	total_amount numeric NOT NULL,
	description varchar NULL,
	transaction_type_id int4 NOT NULL,
	account_id int4 NOT NULL,
	enabled bool DEFAULT true NOT NULL,
    created_date timestamp default now() not null,
    last_modified_date timestamp default now() not null,
	CONSTRAINT transaction_pk PRIMARY KEY (id),
	CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES public."account"(id),
	CONSTRAINT fk_transaction_transaction_type FOREIGN KEY (transaction_type_id) REFERENCES public."transaction_type"(id)
);
