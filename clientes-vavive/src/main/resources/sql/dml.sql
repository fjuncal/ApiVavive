-- Table: public.cliente

-- DROP TABLE public.cliente;

CREATE TABLE public.cliente
(
    id integer NOT NULL DEFAULT nextval('cliente_id_seq'::regclass),
    bairro character varying(40) COLLATE pg_catalog."default" NOT NULL,
    cep character varying(255) COLLATE pg_catalog."default" NOT NULL,
    cpf character varying(11) COLLATE pg_catalog."default" NOT NULL,
    email character varying(70) COLLATE pg_catalog."default" NOT NULL,
    endereco character varying(255) COLLATE pg_catalog."default" NOT NULL,
    nome character varying(150) COLLATE pg_catalog."default" NOT NULL,
    observacao character varying(255) COLLATE pg_catalog."default",
    origem_cliente character varying(255) COLLATE pg_catalog."default",
    ponto_de_referencia character varying(255) COLLATE pg_catalog."default",
    telefone character varying(9) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT cliente_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.cliente
    OWNER to postgres;
    
INSERT INTO public.cliente(
	id, bairro, cep, cpf, email, endereco, nome, observacao, origem_cliente, ponto_de_referencia, telefone)
	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);


-----------------------------------ATIVIDADES----------------------------------------
-- Table: public.atividade

-- DROP TABLE public.atividade;

CREATE TABLE public.atividade
(
    id integer NOT NULL DEFAULT nextval('atividade_id_seq'::regclass),
    data_atividade date NOT NULL,
    data_cadastro date NOT NULL,
    duracao character varying(255) COLLATE pg_catalog."default" NOT NULL,
    hora character varying(255) COLLATE pg_catalog."default" NOT NULL,
    nome character varying(150) COLLATE pg_catalog."default" NOT NULL,
    nota integer NOT NULL,
    observacao character varying(255) COLLATE pg_catalog."default",
    plano character varying(255) COLLATE pg_catalog."default",
    responsavel character varying(255) COLLATE pg_catalog."default" NOT NULL,
    valor numeric(19,2) NOT NULL,
    id_cliente integer,
    CONSTRAINT atividade_pkey PRIMARY KEY (id),
    CONSTRAINT fkch9afq2t7a8x4yyv5t7nd9x84 FOREIGN KEY (id_cliente)
        REFERENCES public.cliente (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.atividade
    OWNER to postgres;
    
INSERT INTO public.atividade(
	id, data_atividade, data_cadastro, duracao, hora, nome, nota, observacao, plano, responsavel, valor, id_cliente)
	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);