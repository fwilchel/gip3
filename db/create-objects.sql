
CREATE SEQUENCE public.centro_trabajo_id_seq;

CREATE TABLE public.centro_trabajo (
                id BIGINT NOT NULL DEFAULT nextval('public.centro_trabajo_id_seq'),
                nombre VARCHAR(100) NOT NULL,
                descripcion VARCHAR(500),
                direccion VARCHAR(100) NOT NULL,
                estado BOOLEAN DEFAULT true NOT NULL,
                CONSTRAINT centro_trabajo_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.centro_trabajo_id_seq OWNED BY public.centro_trabajo.id;

CREATE SEQUENCE public.departamento_id_seq;

CREATE TABLE public.departamento (
                id BIGINT NOT NULL DEFAULT nextval('public.departamento_id_seq'),
                nombre VARCHAR(100) NOT NULL,
                descripcion VARCHAR(500),
                CONSTRAINT departamento_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.departamento_id_seq OWNED BY public.departamento.id;

CREATE SEQUENCE public.municipio_id_seq;

CREATE TABLE public.municipio (
                id BIGINT NOT NULL DEFAULT nextval('public.municipio_id_seq'),
                departamento BIGINT NOT NULL,
                nombre VARCHAR(100) NOT NULL,
                descripcion VARCHAR(500),
                CONSTRAINT municipio_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.municipio_id_seq OWNED BY public.municipio.id;

CREATE SEQUENCE public.centro_medico_id_seq;

CREATE TABLE public.centro_medico (
                id BIGINT NOT NULL DEFAULT nextval('public.centro_medico_id_seq'),
                nit VARCHAR(50) NOT NULL,
                nombre VARCHAR(100) NOT NULL,
                direccion VARCHAR(100) NOT NULL,
                municipio BIGINT NOT NULL,
                telefono VARCHAR(50) NOT NULL,
                contacto VARCHAR(100) NOT NULL,
                estado BOOLEAN DEFAULT true NOT NULL,
                CONSTRAINT centro_medico_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.centro_medico_id_seq OWNED BY public.centro_medico.id;

CREATE SEQUENCE public.lista_id_seq;

CREATE TABLE public.lista (
                id BIGINT NOT NULL DEFAULT nextval('public.lista_id_seq'),
                nombre VARCHAR(100) NOT NULL,
                descripcion VARCHAR(500),
                CONSTRAINT lista_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.lista_id_seq OWNED BY public.lista.id;

CREATE SEQUENCE public.item_id_seq;

CREATE TABLE public.item (
                id BIGINT NOT NULL DEFAULT nextval('public.item_id_seq'),
                lista BIGINT NOT NULL,
                nombre VARCHAR(100) NOT NULL,
                descripcion VARCHAR(500),
                estado BOOLEAN DEFAULT true NOT NULL,
                CONSTRAINT item_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.item_id_seq OWNED BY public.item.id;

CREATE SEQUENCE public.archivo_id_seq;

CREATE TABLE public.archivo (
                id BIGINT NOT NULL DEFAULT nextval('public.archivo_id_seq'),
                nombre VARCHAR(100) NOT NULL,
                ext VARCHAR(10) NOT NULL,
                archivo_base64 VARCHAR NOT NULL,
                content_type VARCHAR(50) NOT NULL,
                url VARCHAR(500),
                titulo VARCHAR(100),
                descripcion VARCHAR(500),
                CONSTRAINT archivo_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.archivo_id_seq OWNED BY public.archivo.id;

CREATE SEQUENCE public.empleado_id_seq;

CREATE TABLE public.empleado (
                id BIGINT NOT NULL DEFAULT nextval('public.empleado_id_seq'),
                identificacion VARCHAR(25) NOT NULL,
                identificacion_tipo BIGINT NOT NULL,
                identificacion_lugar_expedicion BIGINT,
                nombres VARCHAR(50) NOT NULL,
                apellidos VARCHAR(50) NOT NULL,
                estado BOOLEAN DEFAULT true NOT NULL,
                email VARCHAR(100),
                estado_civil BIGINT NOT NULL,
                rh BIGINT NOT NULL,
                genero BIGINT NOT NULL,
                municipio BIGINT NOT NULL,
                lugar VARCHAR(100),
                direccion VARCHAR NOT NULL,
                tipo_contrato BIGINT NOT NULL,
                cargo BIGINT NOT NULL,
                centro_trabajo BIGINT NOT NULL,
                macro_proyecto BIGINT NOT NULL,
                curriculum BIGINT NOT NULL,
                centro_medico BIGINT NOT NULL,
                CONSTRAINT empleado_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.empleado_id_seq OWNED BY public.empleado.id;

CREATE SEQUENCE public.formacion_id_seq;

CREATE TABLE public.formacion (
                id BIGINT NOT NULL DEFAULT nextval('public.formacion_id_seq'),
                empleado BIGINT NOT NULL,
                tipo_formacion BIGINT NOT NULL,
                titulo VARCHAR(100) NOT NULL,
                descripcion VARCHAR(500) NOT NULL,
                numero VARCHAR(100) NOT NULL,
                institucion VARCHAR NOT NULL,
                fecha DATE NOT NULL,
                CONSTRAINT formacion_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.formacion_id_seq OWNED BY public.formacion.id;

CREATE SEQUENCE public.contacto_id_seq;

CREATE TABLE public.contacto (
                id BIGINT NOT NULL DEFAULT nextval('public.contacto_id_seq'),
                empleado BIGINT NOT NULL,
                tipo_contacto BIGINT NOT NULL,
                numero VARCHAR(50) NOT NULL,
                municipio BIGINT NOT NULL,
                CONSTRAINT contacto_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.contacto_id_seq OWNED BY public.contacto.id;

CREATE SEQUENCE public.dotacion_id_seq;

CREATE TABLE public.dotacion (
                id BIGINT NOT NULL DEFAULT nextval('public.dotacion_id_seq'),
                empleado BIGINT NOT NULL,
                tipo_dotacion BIGINT NOT NULL,
                tall VARCHAR(10) NOT NULL,
                cantidad NUMERIC NOT NULL,
                fecha_entrega DATE NOT NULL,
                CONSTRAINT dotacion_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.dotacion_id_seq OWNED BY public.dotacion.id;

CREATE SEQUENCE public.cesantias_id_seq;

CREATE TABLE public.cesantias (
                id BIGINT NOT NULL DEFAULT nextval('public.cesantias_id_seq'),
                empleado BIGINT NOT NULL,
                fecha_solicitud DATE NOT NULL,
                descripcion VARCHAR(500) NOT NULL,
                monto NUMERIC NOT NULL,
                fecha_aprobacion DATE NOT NULL,
                observaciones VARCHAR(1000) NOT NULL,
                CONSTRAINT cesantias_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.cesantias_id_seq OWNED BY public.cesantias.id;

CREATE SEQUENCE public.contrato_id_seq;

CREATE TABLE public.contrato (
                id BIGINT NOT NULL DEFAULT nextval('public.contrato_id_seq'),
                empleado BIGINT NOT NULL,
                tipo_soporte BIGINT NOT NULL,
                descripcion VARCHAR(500) NOT NULL,
                fecha_inicio DATE NOT NULL,
                fecha_fin DATE NOT NULL,
                sueldo NUMERIC NOT NULL,
                soporte BIGINT NOT NULL,
                CONSTRAINT contrato_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.contrato_id_seq OWNED BY public.contrato.id;

CREATE SEQUENCE public.experiencia_id_seq;

CREATE TABLE public.experiencia (
                id BIGINT NOT NULL DEFAULT nextval('public.experiencia_id_seq'),
                empleado BIGINT NOT NULL,
                tipo_empresa BIGINT NOT NULL,
                cargo VARCHAR(100) NOT NULL,
                observaciones VARCHAR(500) NOT NULL,
                fecha_inicio DATE NOT NULL,
                fecha_fin DATE NOT NULL,
                confirmado BOOLEAN NOT NULL,
                fecha_confirmacion DATE NOT NULL,
                CONSTRAINT experiencia_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.experiencia_id_seq OWNED BY public.experiencia.id;

CREATE SEQUENCE public.archivo_x_experiencia_id_seq;

CREATE TABLE public.archivo_x_experiencia (
                id BIGINT NOT NULL DEFAULT nextval('public.archivo_x_experiencia_id_seq'),
                archivo BIGINT NOT NULL,
                experiencia BIGINT NOT NULL,
                CONSTRAINT archivo_x_experiencia_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.archivo_x_experiencia_id_seq OWNED BY public.archivo_x_experiencia.id;

CREATE SEQUENCE public.beneficiario_id_seq;

CREATE TABLE public.beneficiario (
                id BIGINT NOT NULL DEFAULT nextval('public.beneficiario_id_seq'),
                empleado BIGINT NOT NULL,
                tipo_identificacion BIGINT NOT NULL,
                identificacion VARCHAR(100) NOT NULL,
                nombres VARCHAR(100) NOT NULL,
                apellidos VARCHAR(100) NOT NULL,
                genero BIGINT NOT NULL,
                fecha_nacimiento DATE NOT NULL,
                parentesco BIGINT NOT NULL,
                CONSTRAINT beneficiario_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.beneficiario_id_seq OWNED BY public.beneficiario.id;

ALTER TABLE public.empleado ADD CONSTRAINT centro_trabajo_empleado_fk
FOREIGN KEY (centro_trabajo)
REFERENCES public.centro_trabajo (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.municipio ADD CONSTRAINT departamento_municipio_fk
FOREIGN KEY (departamento)
REFERENCES public.departamento (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empleado ADD CONSTRAINT municipio_empleado_fk
FOREIGN KEY (municipio)
REFERENCES public.municipio (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.centro_medico ADD CONSTRAINT municipio_centro_medico_fk
FOREIGN KEY (municipio)
REFERENCES public.municipio (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empleado ADD CONSTRAINT id_lugar_exp_empleado_fk
FOREIGN KEY (identificacion_lugar_expedicion)
REFERENCES public.municipio (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empleado ADD CONSTRAINT centro_medico_empleado_fk
FOREIGN KEY (centro_medico)
REFERENCES public.centro_medico (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.item ADD CONSTRAINT lista_item_fk
FOREIGN KEY (lista)
REFERENCES public.lista (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.beneficiario ADD CONSTRAINT genero_beneficiario_fk
FOREIGN KEY (genero)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.beneficiario ADD CONSTRAINT tipo_identificacion_beneficiario_fk
FOREIGN KEY (tipo_identificacion)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.formacion ADD CONSTRAINT tipo_formacion_formacion_fk
FOREIGN KEY (tipo_formacion)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.contacto ADD CONSTRAINT tipo_contacto_contacto_fk
FOREIGN KEY (tipo_contacto)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.contrato ADD CONSTRAINT tipo_soporte_contrato_fk
FOREIGN KEY (tipo_soporte)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.experiencia ADD CONSTRAINT tipo_empresa_experiencia_fk
FOREIGN KEY (tipo_empresa)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.dotacion ADD CONSTRAINT tipo_dotacion_dotacion_fk
FOREIGN KEY (tipo_dotacion)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empleado ADD CONSTRAINT tipo_identificacion_empleado_fk
FOREIGN KEY (identificacion_tipo)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empleado ADD CONSTRAINT tipo_contrato_empleado_fk
FOREIGN KEY (tipo_contrato)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empleado ADD CONSTRAINT rh_empleado_fk
FOREIGN KEY (rh)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empleado ADD CONSTRAINT estado_civil_empleado_fk
FOREIGN KEY (estado_civil)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empleado ADD CONSTRAINT genero_empleado_fk
FOREIGN KEY (genero)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empleado ADD CONSTRAINT cargo_empleado_fk
FOREIGN KEY (cargo)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.beneficiario ADD CONSTRAINT parentesco_beneficiario_fk
FOREIGN KEY (parentesco)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empleado ADD CONSTRAINT macro_proyecto_empleado_fk
FOREIGN KEY (macro_proyecto)
REFERENCES public.item (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.contrato ADD CONSTRAINT archivo_contrato_fk
FOREIGN KEY (soporte)
REFERENCES public.archivo (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.archivo_x_experiencia ADD CONSTRAINT archivo_archivo_x_experiencia_fk
FOREIGN KEY (experiencia)
REFERENCES public.archivo (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.empleado ADD CONSTRAINT archivo_empleado_fk
FOREIGN KEY (curriculum)
REFERENCES public.archivo (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.beneficiario ADD CONSTRAINT empleado_beneficiario_fk
FOREIGN KEY (empleado)
REFERENCES public.empleado (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.experiencia ADD CONSTRAINT empleado_experiencia_fk
FOREIGN KEY (empleado)
REFERENCES public.empleado (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.contrato ADD CONSTRAINT empleado_contrato_fk
FOREIGN KEY (empleado)
REFERENCES public.empleado (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.cesantias ADD CONSTRAINT empleado_cesantias_fk
FOREIGN KEY (empleado)
REFERENCES public.empleado (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.dotacion ADD CONSTRAINT empleado_dotacion_fk
FOREIGN KEY (empleado)
REFERENCES public.empleado (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.contacto ADD CONSTRAINT empleado_contacto_fk
FOREIGN KEY (empleado)
REFERENCES public.empleado (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.formacion ADD CONSTRAINT empleado_formacion_fk
FOREIGN KEY (empleado)
REFERENCES public.empleado (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.archivo_x_experiencia ADD CONSTRAINT experiencia_archivo_x_experiencia_fk
FOREIGN KEY (archivo)
REFERENCES public.experiencia (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
