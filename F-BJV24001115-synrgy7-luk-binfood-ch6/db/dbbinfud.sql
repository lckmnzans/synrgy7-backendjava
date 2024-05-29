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
-- Name: get_merchant_report(uuid, timestamp without time zone, timestamp without time zone); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.get_merchant_report(IN "MerchantId" uuid, IN "StartDate" timestamp without time zone, IN "EndDate" timestamp without time zone)
    LANGUAGE sql
    AS $$
select * from public."order" o inner join public.order_detail od on o.id=od.order_id where o.order_time < "EndDate" and o.order_time > "StartDate";
$$;


ALTER PROCEDURE public.get_merchant_report(IN "MerchantId" uuid, IN "StartDate" timestamp without time zone, IN "EndDate" timestamp without time zone) OWNER TO postgres;

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
INSERT INTO public.users (deleted,id, "name", email_address, "password", username)
 VALUES (false,gen_random_uuid(), "Name", "Email", "Pass", "Username");
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
    deleted boolean NOT NULL,
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
t	5870986d-a73b-47e3-a0f7-96663da9bcdb	Jl. Tj. Sari VIII, Sumurboto, Kec. Banyumanik, Kota Semarang, Jawa Tengah 50269	Tanjungsari Food
\.


--
-- Data for Name: order; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."order" (completed, order_time, id, user_id, destination_address) FROM stdin;
f	2024-05-15 12:07:40.233	67a06537-df60-4d70-945b-b0c5f62f39eb	ff15ed0d-1d69-4ae9-a9a1-7152259d7307	Jl Tj Sari VI No 29, Sumurboto, Kec Banyumanik, Kota Semarang, Jawa Tengah
f	2024-05-17 16:35:58.537	6665253c-45f0-43d3-a3d6-b3a414e0236e	ff15ed0d-1d69-4ae9-a9a1-7152259d7307	Jl Tj Sari VI No 29, Sumurboto, Kec Banyumanik, Kota Semarang, Jawa Tengah
f	2024-05-17 17:08:09.31	09504bca-d5e4-4327-b514-cd33bc3dcf21	ff15ed0d-1d69-4ae9-a9a1-7152259d7307	Jl Tj Sari VI No 29, Sumurboto, Kec Banyumanik, Kota Semarang, Jawa Tengah
f	2024-05-17 17:12:45.057	80e7d513-f8f2-4b36-bc55-8f7647b8743e	ff15ed0d-1d69-4ae9-a9a1-7152259d7307	Jl Tj Sari VI No 29, Sumurboto, Kec Banyumanik, Kota Semarang, Jawa Tengah
f	2024-05-17 17:13:39.68	4bcaa929-35c3-48d8-beec-fbc3f93d2058	ff15ed0d-1d69-4ae9-a9a1-7152259d7307	Jl Tj Sari VI No 29, Sumurboto, Kec Banyumanik, Kota Semarang, Jawa Tengah
f	2024-05-17 18:59:11.023	d098133f-1b16-42cf-af0b-7a9505bf49fd	ff15ed0d-1d69-4ae9-a9a1-7152259d7307	Jl Tj Sari VI No 29, Sumurboto, Kec Banyumanik, Kota Semarang, Jawa Tengah
f	2024-05-17 19:01:15.399	cfa7b820-3dfb-491b-bc68-bbf099445434	ff15ed0d-1d69-4ae9-a9a1-7152259d7307	Jl Tj Sari VI No 29, Sumurboto, Kec Banyumanik, Kota Semarang, Jawa Tengah
f	2024-05-17 19:04:47.099	878041cd-792b-47a1-8546-9989da24ab61	ff15ed0d-1d69-4ae9-a9a1-7152259d7307	Jl Tj Sari VI No 29, Sumurboto, Kec Banyumanik, Kota Semarang, Jawa Tengah
f	2024-05-17 19:07:55.824	51e418b5-5306-4441-8b24-37fbdb7aee8d	ff15ed0d-1d69-4ae9-a9a1-7152259d7307	Jl Tj Sari VI No 29, Sumurboto, Kec Banyumanik, Kota Semarang, Jawa Tengah
\.


--
-- Data for Name: order_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_detail (quantity, total_price, id, order_id, product_id) FROM stdin;
2	30000	0c59ace7-75b0-41b1-afe8-9a44b5f8f7f1	67a06537-df60-4d70-945b-b0c5f62f39eb	ce26dbe9-feed-4dd8-b9f4-82e641072007
1	20000	6fc96ab8-e8ed-4b95-bd36-36855cd3fc99	67a06537-df60-4d70-945b-b0c5f62f39eb	b126f224-e7e4-41ba-ae5e-b5c677a94773
1	20000	5d520df8-5d19-4bdd-b235-7e15f2574b74	6665253c-45f0-43d3-a3d6-b3a414e0236e	42b074d5-405f-4738-8160-eba75ce2ae31
4	64000	92d41042-125b-4772-88d1-ecf3a799515c	6665253c-45f0-43d3-a3d6-b3a414e0236e	2488151c-4398-4256-b483-67fa50b688b3
2	40000	e4e77ec8-062e-4549-8ce9-051ef545dbd0	6665253c-45f0-43d3-a3d6-b3a414e0236e	b126f224-e7e4-41ba-ae5e-b5c677a94773
1	15000	7df1b3d9-cf7f-46a1-9b77-744c47ad4657	6665253c-45f0-43d3-a3d6-b3a414e0236e	ce26dbe9-feed-4dd8-b9f4-82e641072007
1	20000	f74abbc4-ddc4-48c9-ace4-9c042cc0dddb	09504bca-d5e4-4327-b514-cd33bc3dcf21	42b074d5-405f-4738-8160-eba75ce2ae31
2	32000	cba5f468-d209-4235-a54f-dbb01ac512a7	09504bca-d5e4-4327-b514-cd33bc3dcf21	2488151c-4398-4256-b483-67fa50b688b3
1	20000	ac187c3f-d913-4ba1-b828-83bef69ae753	09504bca-d5e4-4327-b514-cd33bc3dcf21	b126f224-e7e4-41ba-ae5e-b5c677a94773
1	20000	5b50ab55-a131-4f2f-8c5a-391dbcaae51d	80e7d513-f8f2-4b36-bc55-8f7647b8743e	42b074d5-405f-4738-8160-eba75ce2ae31
2	32000	e3183001-8f92-489a-a347-489d68b9cff6	80e7d513-f8f2-4b36-bc55-8f7647b8743e	2488151c-4398-4256-b483-67fa50b688b3
4	80000	df019eaf-3173-4558-a6a2-cfc51c711963	4bcaa929-35c3-48d8-beec-fbc3f93d2058	42b074d5-405f-4738-8160-eba75ce2ae31
4	80000	eec4b7f0-7d72-4cc7-af99-d67292824239	d098133f-1b16-42cf-af0b-7a9505bf49fd	42b074d5-405f-4738-8160-eba75ce2ae31
2	40000	ca057103-8169-40a6-b4b3-994fd9194d15	d098133f-1b16-42cf-af0b-7a9505bf49fd	b126f224-e7e4-41ba-ae5e-b5c677a94773
2	40000	cb3a8c79-4a83-44b4-ad70-fb83c0972a42	cfa7b820-3dfb-491b-bc68-bbf099445434	b126f224-e7e4-41ba-ae5e-b5c677a94773
10	200000	1f978899-c6ae-4106-9a85-d06042109f27	878041cd-792b-47a1-8546-9989da24ab61	b126f224-e7e4-41ba-ae5e-b5c677a94773
3	60000	4e9138e7-5a3a-4f18-a77e-7c38acbb25de	51e418b5-5306-4441-8b24-37fbdb7aee8d	b126f224-e7e4-41ba-ae5e-b5c677a94773
\.


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (price, id, merchant_id, product_name) FROM stdin;
20000	42b074d5-405f-4738-8160-eba75ce2ae31	5870986d-a73b-47e3-a0f7-96663da9bcdb	Ayam Cabe Garam
20000	b126f224-e7e4-41ba-ae5e-b5c677a94773	5870986d-a73b-47e3-a0f7-96663da9bcdb	Ayam Teriyaki
15000	ce26dbe9-feed-4dd8-b9f4-82e641072007	5870986d-a73b-47e3-a0f7-96663da9bcdb	Nasi Goreng Ayam
16000	2488151c-4398-4256-b483-67fa50b688b3	5870986d-a73b-47e3-a0f7-96663da9bcdb	Nasi Goreng Seafood
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (deleted, id, email_address, name, password, username) FROM stdin;
f	ff15ed0d-1d69-4ae9-a9a1-7152259d7307	lckmnzans@mail.co	Lukman Sanusi	123	lckmnzans
f	b7eb6520-7c31-405d-92e2-26b57635da9f	sayloveu@mail.co	Shaina Oline	1212	shayooo
f	dea1c120-b351-479b-bfed-38963913af86	tayloring@mail.co	Taylor Lautner	oiu123	tailoringgg
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
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


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

