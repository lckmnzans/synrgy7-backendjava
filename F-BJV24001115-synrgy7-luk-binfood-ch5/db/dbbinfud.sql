--
-- PostgreSQL database dump
--

-- Dumped from database version 14.11 (Ubuntu 14.11-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.11 (Ubuntu 14.11-0ubuntu0.22.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: delete_user_data(uuid); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.delete_user_data(IN "ID" uuid)
    LANGUAGE sql
    AS $$
delete from public.users where users.id = "ID";
$$;


ALTER PROCEDURE public.delete_user_data(IN "ID" uuid) OWNER TO postgres;

--
-- Name: insert_user_data(character varying, character varying, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.insert_user_data(IN "Username" character varying, IN "Email" character varying, IN "Pass" character varying)
    LANGUAGE sql
    AS $$
INSERT INTO public.users (id, email_address, "password", username)
 VALUES (gen_random_uuid(), "Email", "Pass", "Username");
$$;


ALTER PROCEDURE public.insert_user_data(IN "Username" character varying, IN "Email" character varying, IN "Pass" character varying) OWNER TO postgres;

--
-- Name: insert_user_data(character varying, character varying, character varying, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.insert_user_data(IN "Name" character varying, IN "Username" character varying, IN "Email" character varying, IN "Pass" character varying)
    LANGUAGE sql
    AS $$
INSERT INTO public.users (id, "name", email_address, "password", username)
 VALUES (gen_random_uuid(), "Name", "Email", "Pass", "Username");
$$;


ALTER PROCEDURE public.insert_user_data(IN "Name" character varying, IN "Username" character varying, IN "Email" character varying, IN "Pass" character varying) OWNER TO postgres;

--
-- Name: update_user_username(uuid, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.update_user_username(IN "ID" uuid, IN "Username" character varying)
    LANGUAGE sql
    AS $$
update public.users set username = "Username" where users.id = "ID";
$$;


ALTER PROCEDURE public.update_user_username(IN "ID" uuid, IN "Username" character varying) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: merchant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.merchant (
    open boolean NOT NULL,
    id uuid NOT NULL,
    merchant_location character varying(255),
    merchant_name character varying(255)
);


ALTER TABLE public.merchant OWNER TO postgres;

--
-- Name: order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."order" (
    completed boolean NOT NULL,
    order_time timestamp(6) without time zone,
    id uuid NOT NULL,
    user_id uuid,
    destination_address character varying(255)
);


ALTER TABLE public."order" OWNER TO postgres;

--
-- Name: order_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_detail (
    quantity integer NOT NULL,
    total_price double precision,
    id uuid NOT NULL,
    order_id uuid,
    product_id uuid
);


ALTER TABLE public.order_detail OWNER TO postgres;

--
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    price double precision,
    id uuid NOT NULL,
    merchant_id uuid,
    product_name character varying(255)
);


ALTER TABLE public.product OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    email_address character varying(255),
    name character varying(255),
    password character varying(255),
    username character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: merchant; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.merchant (open, id, merchant_location, merchant_name) FROM stdin;
t	895b71ec-961c-4224-9492-6283709ee4b8	Jl Sumur Boto Bar. III, Sumurboto, Banyumanik, Kota Semarang	Bakmi 99 Pak Joko
f	9b98a8e7-a27d-43d6-aaaf-13ea2e93428e	Jl. Banjarsari No.40, Tembalang, Kec. Tembalang, Kota Semarang, Jawa Tengah 50275	Burjo Lima
f	592bab1b-af98-434f-9389-11900720cd7c	Jl Tj Sari VI No.29, Sumurboto, Banyumanik, Kota Semarang	Geprek Jago
\.


--
-- Data for Name: order; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."order" (completed, order_time, id, user_id, destination_address) FROM stdin;
f	2024-05-03 19:14:58.613	b9a5d3c3-0d5e-42eb-a966-f5ee32f25fcf	d4adc60e-ac01-4703-b47a-e9265d4a8dc6	Jl Tj Sari VI No.29, Sumurboto, Banyumanik, Kota Semarang
f	2024-05-05 04:02:24.554	073177f5-5fa7-4a83-af6f-cb16e3148adf	d4adc60e-ac01-4703-b47a-e9265d4a8dc6	Jl Tj Sari VI No.29, Sumurboto, Banyumanik, Kota Semarang
\.


--
-- Data for Name: order_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_detail (quantity, total_price, id, order_id, product_id) FROM stdin;
2	20000	7ac4915f-a230-4d56-aa5d-0f9798f98fa8	b9a5d3c3-0d5e-42eb-a966-f5ee32f25fcf	ce77b5c7-f060-453c-989a-e1fdb9739871
1	10000	bbeb98e4-dba9-40e3-8d0f-23fac162297e	b9a5d3c3-0d5e-42eb-a966-f5ee32f25fcf	5314a4ba-e521-4bf6-93ce-59177c2aae1c
2	28000	a02a991b-b1c5-4f24-b973-736daaa00bb7	073177f5-5fa7-4a83-af6f-cb16e3148adf	58c95c9f-dcae-40da-b393-16df4713ac46
1	14000	196d26b7-fd59-4ed8-9ab6-e5f4e57e0162	073177f5-5fa7-4a83-af6f-cb16e3148adf	89028832-963a-4b87-8c9d-08ffe6d5c9fe
\.


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (price, id, merchant_id, product_name) FROM stdin;
10000	ce77b5c7-f060-453c-989a-e1fdb9739871	592bab1b-af98-434f-9389-11900720cd7c	Geprek Bakar
14000	58c95c9f-dcae-40da-b393-16df4713ac46	895b71ec-961c-4224-9492-6283709ee4b8	Bakmi goreng
14000	89028832-963a-4b87-8c9d-08ffe6d5c9fe	895b71ec-961c-4224-9492-6283709ee4b8	Bakmi rebus
13000	b6be3aaa-c38c-46c8-aaa4-4d98e8bc6234	895b71ec-961c-4224-9492-6283709ee4b8	Nasi goreng ayam
10000	5314a4ba-e521-4bf6-93ce-59177c2aae1c	592bab1b-af98-434f-9389-11900720cd7c	Geprek Mozarella
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, email_address, name, password, username) FROM stdin;
d4adc60e-ac01-4703-b47a-e9265d4a8dc6	lckmnzans@gmail.com	Lukman Sanusi	123	lckmnzans
9c939d58-daca-4d9b-90e8-4583cbd3f48b	synmmmo@mail.co	Sinonia Caitlyn	sin123	sinonia
ab860ed8-c2cf-49fe-977f-df74fa4fdf01	taucho@youmail.com	Hu Tao	666	wangshen
\.


--
-- Name: merchant merchant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merchant
    ADD CONSTRAINT merchant_pkey PRIMARY KEY (id);


--
-- Name: order_detail order_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT order_detail_pkey PRIMARY KEY (id);


--
-- Name: order order_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT order_pkey PRIMARY KEY (id);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: order_detail fkb8bg2bkty0oksa3wiq5mp5qnc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT fkb8bg2bkty0oksa3wiq5mp5qnc FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: order fkh3c37jq3nrv0f2edcxk0ine72; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."order"
    ADD CONSTRAINT fkh3c37jq3nrv0f2edcxk0ine72 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: product fkk47qmalv2pg906heielteubu7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fkk47qmalv2pg906heielteubu7 FOREIGN KEY (merchant_id) REFERENCES public.merchant(id);


--
-- Name: order_detail fklb8mofup9mi791hraxt9wlj5u; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT fklb8mofup9mi791hraxt9wlj5u FOREIGN KEY (order_id) REFERENCES public."order"(id);


--
-- PostgreSQL database dump complete
--

