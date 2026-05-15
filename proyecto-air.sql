-- Proyecto Bases 02
-- Version: Sprint 2 - Base funcional inicial

DROP SCHEMA IF EXISTS air CASCADE;
CREATE SCHEMA air;
SET search_path TO air;

CREATE EXTENSION IF NOT EXISTS btree_gist;

-- 1. SEGURIDAD Y AUDITORIA

CREATE TABLE sys_usuario (
    id_usuario SERIAL PRIMARY KEY,
    username VARCHAR(80) NOT NULL UNIQUE,
    passw TEXT NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE sys_rol (
    id_rol SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(80) NOT NULL UNIQUE,
    descripcion TEXT
);

CREATE TABLE sys_permiso (
    id_permiso SERIAL PRIMARY KEY,
    nombre_permiso VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT
);

CREATE TABLE sys_usuario_rol (
    id_usuario INT NOT NULL REFERENCES sys_usuario(id_usuario) ON DELETE CASCADE,
    id_rol INT NOT NULL REFERENCES sys_rol(id_rol) ON DELETE CASCADE,
    PRIMARY KEY (id_usuario, id_rol)
);

CREATE TABLE sys_rol_permiso (
    id_rol INT NOT NULL REFERENCES sys_rol(id_rol) ON DELETE CASCADE,
    id_permiso INT NOT NULL REFERENCES sys_permiso(id_permiso) ON DELETE CASCADE,
    PRIMARY KEY (id_rol, id_permiso)
);

CREATE TABLE sys_log_auditoria (
    id_log SERIAL PRIMARY KEY,
    id_usuario INT REFERENCES sys_usuario(id_usuario),
    accion VARCHAR(100) NOT NULL,
    tabla_afectada VARCHAR(100) NOT NULL,
    detalle TEXT,
    registro_id INT,
    fecha_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 2. CATALOGOS

CREATE TABLE catalogo_tipo_sesion (
    id_tipo_sesion SERIAL PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE catalogo_tipo_modalidad (
    id_tipo_modalidad SERIAL PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE catalogo_puestos (
    id_puesto SERIAL PRIMARY KEY,
    nombre_puesto VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE catalogo_sector (
    id_sector SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE catalogo_nivel_reglamento (
    id_nivel_reglamento SERIAL PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE catalogo_etapas_propuestas (
    id_etapa_propuesta SERIAL PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE catalogo_estado_propuestas (
    id_estado_propuesta SERIAL PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE catalogo_tipo_comision (
    id_tipo_comision SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE catalogo_rol_comision (
    id_rol_comision SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE catalogo_tipo_mayoria_requerida (
    id_tipo_mayoria_requerida SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE catalogo_tipo_reforma (
    id_tipo_reforma SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE catalogo_estado_vigencia (
    id_estado_vigencia SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE catalogo_tipo_tramite (
    id_tipo_tramite SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE catalogo_asistencia_sesion_comision (
    id_estado_asistencia SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

-- 3. ASAMBLEISTAS Y NOMBRAMIENTOS

CREATE TABLE asambleista (
    asambleista_id SERIAL PRIMARY KEY,
    cedula VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    correo_institucional VARCHAR(150) UNIQUE
);

CREATE TABLE bitacora_asambleistas (
    id_bitacora_asambleista SERIAL PRIMARY KEY,
    asambleista_id INT NOT NULL REFERENCES asambleista(asambleista_id),
    cedula_anterior VARCHAR(30),
    nombre_anterior VARCHAR(150),
    razon_cambio TEXT,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 4. NORMATIVA

CREATE TABLE reglamento (
    id_reglamento SERIAL PRIMARY KEY,
    nombre_normativa VARCHAR(200) NOT NULL,
    sigla VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE elemento_normativo (
    id_elemento SERIAL PRIMARY KEY,
    id_reglamento INT NOT NULL REFERENCES reglamento(id_reglamento) ON DELETE CASCADE,
    id_elemento_padre INT REFERENCES elemento_normativo(id_elemento) ON DELETE CASCADE,
    id_nivel_reglamento INT NOT NULL REFERENCES catalogo_nivel_reglamento(id_nivel_reglamento),
    numero_etiqueta VARCHAR(50),
    contenido_texto TEXT,
    orden INT NOT NULL,
    fecha_inicio_vigencia DATE NOT NULL DEFAULT CURRENT_DATE,
    fecha_fin_vigencia DATE,
    id_estado_vigencia INT NOT NULL REFERENCES catalogo_estado_vigencia(id_estado_vigencia),
    CONSTRAINT chk_elemento_orden CHECK (orden > 0),
    CONSTRAINT chk_elemento_fechas CHECK (fecha_fin_vigencia IS NULL OR fecha_fin_vigencia >= fecha_inicio_vigencia)
);

CREATE UNIQUE INDEX uq_elemento_raiz_orden
ON elemento_normativo(id_reglamento, orden)
WHERE id_elemento_padre IS NULL;

CREATE UNIQUE INDEX uq_elemento_hijo_orden
ON elemento_normativo(id_elemento_padre, orden)
WHERE id_elemento_padre IS NOT NULL;

-- 5. SESIONES, ACTAS Y AGENDA

CREATE TABLE sesiones (
    id_sesion SERIAL PRIMARY KEY,
    id_tipo_modalidad INT NOT NULL REFERENCES catalogo_tipo_modalidad(id_tipo_modalidad),
    id_tipo_sesion INT NOT NULL REFERENCES catalogo_tipo_sesion(id_tipo_sesion),
    numero_sesion VARCHAR(80) NOT NULL UNIQUE,
    fecha DATE NOT NULL,
    link_acta TEXT,
    quorum_requerido INT,
    CONSTRAINT chk_sesion_quorum CHECK (quorum_requerido IS NULL OR quorum_requerido >= 0)
);

CREATE TABLE acta (
    id_acta SERIAL PRIMARY KEY,
    id_sesion INT NOT NULL UNIQUE REFERENCES sesiones(id_sesion) ON DELETE CASCADE,
    fecha_aprobacion DATE,
    url_documento TEXT,
    observaciones TEXT
);

CREATE TABLE agenda (
    id_agenda SERIAL PRIMARY KEY,
    id_sesion INT NOT NULL REFERENCES sesiones(id_sesion) ON DELETE CASCADE,
    fecha_publicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(40) NOT NULL DEFAULT 'BORRADOR'
);

-- 6. PROPUESTAS

CREATE TABLE propuesta (
    id_propuesta SERIAL PRIMARY KEY,
    id_reglamento_base INT REFERENCES reglamento(id_reglamento),
    id_etapa_propuesta INT NOT NULL REFERENCES catalogo_etapas_propuestas(id_etapa_propuesta),
    id_estado_propuesta INT NOT NULL REFERENCES catalogo_estado_propuestas(id_estado_propuesta),
    id_propuesta_padre INT REFERENCES propuesta(id_propuesta),
    titulo VARCHAR(250) NOT NULL,
    texto_sustitutivo TEXT,
    codigo_air VARCHAR(80) UNIQUE,
    id_tipo_mayoria_requerida INT REFERENCES catalogo_tipo_mayoria_requerida(id_tipo_mayoria_requerida)
);

CREATE TABLE bitacora_propuesta (
    id_registro_bitacora SERIAL PRIMARY KEY,
    id_propuesta INT NOT NULL REFERENCES propuesta(id_propuesta) ON DELETE CASCADE,
    id_reglamento_base INT REFERENCES reglamento(id_reglamento),
    id_etapa_propuesta INT REFERENCES catalogo_etapas_propuestas(id_etapa_propuesta),
    id_estado_propuesta INT REFERENCES catalogo_estado_propuestas(id_estado_propuesta),
    titulo VARCHAR(250),
    codigo_air VARCHAR(80),
    fecha_modificacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario_modificacion INT REFERENCES sys_usuario(id_usuario)
);

CREATE TABLE proponente_propuesta (
    id_proponente_propuesta SERIAL PRIMARY KEY,
    id_propuesta INT NOT NULL REFERENCES propuesta(id_propuesta) ON DELETE CASCADE,
    id_asambleista INT NOT NULL REFERENCES asambleista(asambleista_id) ON DELETE CASCADE,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_proponente_propuesta UNIQUE (id_propuesta, id_asambleista)
);

CREATE TABLE punto_agenda (
    id_punto_agenda SERIAL PRIMARY KEY,
    id_sesion INT NOT NULL REFERENCES sesiones(id_sesion) ON DELETE CASCADE,
    id_propuesta INT REFERENCES propuesta(id_propuesta),
    orden INT NOT NULL,
    descripcion TEXT,
    CONSTRAINT chk_punto_agenda_orden CHECK (orden > 0),
    CONSTRAINT uq_punto_agenda_orden UNIQUE (id_sesion, orden)
);

CREATE TABLE resolucion (
    id_resolucion SERIAL PRIMARY KEY,
    id_agenda INT REFERENCES agenda(id_agenda),
    id_punto_agenda INT REFERENCES punto_agenda(id_punto_agenda),
    numero_resolucion VARCHAR(80) NOT NULL UNIQUE,
    fecha_emision DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE asistencia_sesion_plenaria (
    id_asistencia SERIAL PRIMARY KEY,
    id_asambleista INT NOT NULL REFERENCES asambleista(asambleista_id) ON DELETE CASCADE,
    id_sesion INT NOT NULL REFERENCES sesiones(id_sesion) ON DELETE CASCADE,
    id_estado_asistencia INT NOT NULL REFERENCES catalogo_asistencia_sesion_comision(id_estado_asistencia),
    CONSTRAINT uq_asistencia_plenaria UNIQUE (id_asambleista, id_sesion)
);

CREATE TABLE nombramiento (
    id_nombramiento SERIAL PRIMARY KEY,
    asambleista_id INT NOT NULL REFERENCES asambleista(asambleista_id) ON DELETE CASCADE,
    sector_id INT NOT NULL REFERENCES catalogo_sector(id_sector),
    resolucion_id INT REFERENCES resolucion(id_resolucion),
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE,
    estado VARCHAR(40) NOT NULL DEFAULT 'VIGENTE',
    id_puesto INT REFERENCES catalogo_puestos(id_puesto),
    id_usuario_registro INT REFERENCES sys_usuario(id_usuario),
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_nombramiento_fechas CHECK (fecha_fin IS NULL OR fecha_fin >= fecha_inicio)
);

ALTER TABLE nombramiento
ADD CONSTRAINT ex_nombramiento_sin_traslape
EXCLUDE USING gist (
    asambleista_id WITH =,
    daterange(fecha_inicio, COALESCE(fecha_fin, '9999-12-31'::date), '[]') WITH &&
);

-- 7. COMISIONES

CREATE TABLE comision (
    id_comision SERIAL PRIMARY KEY,
    id_tipo_comision INT NOT NULL REFERENCES catalogo_tipo_comision(id_tipo_comision),
    nombre_comision VARCHAR(200) NOT NULL
);

CREATE TABLE propositos_comision (
    id_proposito_comision SERIAL PRIMARY KEY,
    id_comision INT NOT NULL REFERENCES comision(id_comision) ON DELETE CASCADE,
    id_propuesta INT REFERENCES propuesta(id_propuesta),
    texto TEXT NOT NULL,
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE integrante_comision (
    id_integrante_comision SERIAL PRIMARY KEY,
    id_comision INT NOT NULL REFERENCES comision(id_comision) ON DELETE CASCADE,
    id_asambleista INT NOT NULL REFERENCES asambleista(asambleista_id) ON DELETE CASCADE,
    id_rol_comision INT NOT NULL REFERENCES catalogo_rol_comision(id_rol_comision),
    fecha_ingreso_nombramiento DATE NOT NULL,
    fecha_fin_nombramiento DATE,
    estado VARCHAR(40) NOT NULL DEFAULT 'ACTIVO',
    CONSTRAINT chk_integrante_fechas CHECK (fecha_fin_nombramiento IS NULL OR fecha_fin_nombramiento >= fecha_ingreso_nombramiento)
);

CREATE TABLE bitacora_integrante_comision (
    id_bitacora_integrante_comision SERIAL PRIMARY KEY,
    id_integrante_comision INT REFERENCES integrante_comision(id_integrante_comision),
    id_comision INT,
    id_asambleista INT,
    id_rol_comision INT,
    fecha_ingreso_nombramiento DATE,
    fecha_fin_nombramiento DATE,
    estado VARCHAR(40),
    fecha_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sesion_comision (
    id_sesion_comision SERIAL PRIMARY KEY,
    id_comision INT NOT NULL REFERENCES comision(id_comision) ON DELETE CASCADE,
    fecha_hora TIMESTAMP NOT NULL
);

CREATE TABLE punto_agenda_sesion_comision (
    id_punto_agenda_sesion_comision SERIAL PRIMARY KEY,
    id_sesion_comision INT NOT NULL REFERENCES sesion_comision(id_sesion_comision) ON DELETE CASCADE,
    id_proposito INT REFERENCES propositos_comision(id_proposito_comision),
    id_tipo_tramite INT REFERENCES catalogo_tipo_tramite(id_tipo_tramite),
    orden INT NOT NULL,
    titulo VARCHAR(250),
    descripcion TEXT,
    CONSTRAINT chk_punto_comision_orden CHECK (orden > 0),
    CONSTRAINT uq_punto_comision_orden UNIQUE (id_sesion_comision, orden)
);

CREATE TABLE asistencia_sesion_comision (
    id_asistencia_comision SERIAL PRIMARY KEY,
    asambleista_id INT NOT NULL REFERENCES asambleista(asambleista_id) ON DELETE CASCADE,
    id_sesion_comision INT NOT NULL REFERENCES sesion_comision(id_sesion_comision) ON DELETE CASCADE,
    comision_id INT NOT NULL REFERENCES comision(id_comision) ON DELETE CASCADE,
    id_estado_asistencia INT NOT NULL REFERENCES catalogo_asistencia_sesion_comision(id_estado_asistencia),
    CONSTRAINT uq_asistencia_comision UNIQUE (asambleista_id, id_sesion_comision)
);

-- 8. INFORMES Y JUSTIFICACIONES

CREATE TABLE justificacion_legal (
    id_argumento SERIAL PRIMARY KEY,
    es_considerando_p BOOLEAN NOT NULL DEFAULT TRUE,
    contenido TEXT NOT NULL
);

CREATE TABLE informe_directorio (
    id_informe SERIAL PRIMARY KEY,
    id_comision INT REFERENCES comision(id_comision),
    id_propuesta INT REFERENCES propuesta(id_propuesta),
    id_sesion INT REFERENCES sesiones(id_sesion),
    recomendacion TEXT,
    fecha_presentacion DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE justificaciones_por_informe (
    id_informe INT NOT NULL REFERENCES informe_directorio(id_informe) ON DELETE CASCADE,
    id_argumento INT NOT NULL REFERENCES justificacion_legal(id_argumento) ON DELETE CASCADE,
    orden_aparicion INT NOT NULL,
    PRIMARY KEY (id_informe, id_argumento),
    CONSTRAINT chk_justificacion_orden CHECK (orden_aparicion > 0)
);

-- 9. REFORMAS, FOLIOS Y CERTIFICACIONES

CREATE TABLE reforma_aplicada (
    id_reforma SERIAL PRIMARY KEY,
    id_resolucion INT REFERENCES resolucion(id_resolucion),
    id_elemento_normativo INT NOT NULL REFERENCES elemento_normativo(id_elemento),
    texto_anterior TEXT,
    texto_nuevo TEXT NOT NULL,
    fecha_inicio_vigencia DATE NOT NULL DEFAULT CURRENT_DATE,
    id_tipo_reforma INT NOT NULL REFERENCES catalogo_tipo_reforma(id_tipo_reforma)
);

CREATE TABLE control_folio (
    id_control SERIAL PRIMARY KEY,
    anio INT NOT NULL UNIQUE,
    ultimo_numero INT NOT NULL DEFAULT 0,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_control_folio_anio CHECK (anio >= 2000),
    CONSTRAINT chk_control_folio_numero CHECK (ultimo_numero >= 0)
);

CREATE TABLE certificacion_emitida (
    id_certificacion SERIAL PRIMARY KEY,
    id_asambleista INT NOT NULL REFERENCES asambleista(asambleista_id),
    folio_unico VARCHAR(80) NOT NULL UNIQUE,
    hash_seguridad TEXT NOT NULL UNIQUE,
    fecha_emision TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario_secretaria INT REFERENCES sys_usuario(id_usuario)
);

CREATE TABLE anulacion_certificacion (
    id_anulacion SERIAL PRIMARY KEY,
    certificacion_id INT NOT NULL UNIQUE REFERENCES certificacion_emitida(id_certificacion),
    motivo TEXT NOT NULL,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 10. VISTAS UTILES

CREATE OR REPLACE VIEW vw_arbol_normativo AS
WITH RECURSIVE arbol AS (
    SELECT
        e.id_elemento,
        e.id_reglamento,
        e.id_elemento_padre,
        e.id_nivel_reglamento,
        e.numero_etiqueta,
        e.contenido_texto,
        e.orden,
        1 AS nivel,
        LPAD(e.orden::TEXT, 4, '0') AS ruta_orden
    FROM elemento_normativo e
    WHERE e.id_elemento_padre IS NULL

    UNION ALL

    SELECT
        hijo.id_elemento,
        hijo.id_reglamento,
        hijo.id_elemento_padre,
        hijo.id_nivel_reglamento,
        hijo.numero_etiqueta,
        hijo.contenido_texto,
        hijo.orden,
        padre.nivel + 1,
        padre.ruta_orden || '.' || LPAD(hijo.orden::TEXT, 4, '0')
    FROM elemento_normativo hijo
    JOIN arbol padre ON padre.id_elemento = hijo.id_elemento_padre
)
SELECT *
FROM arbol
ORDER BY id_reglamento, ruta_orden;

CREATE OR REPLACE VIEW vw_asambleistas_nombramientos AS
SELECT
    a.asambleista_id,
    a.cedula,
    a.nombre,
    a.correo_institucional,
    n.id_nombramiento,
    s.nombre AS sector,
    p.nombre_puesto,
    n.fecha_inicio,
    n.fecha_fin,
    n.estado
FROM asambleista a
LEFT JOIN nombramiento n ON n.asambleista_id = a.asambleista_id
LEFT JOIN catalogo_sector s ON s.id_sector = n.sector_id
LEFT JOIN catalogo_puestos p ON p.id_puesto = n.id_puesto;

-- 11. DATOS SEMILLA MINIMOS

INSERT INTO sys_rol (nombre_rol) VALUES
('Secretaria AIR'),
('Asistente Secretaria'),
('Directorio AIR'),
('Asambleista'),
('Administrador');

INSERT INTO sys_permiso (nombre_permiso, descripcion) VALUES
('GESTIONAR_USUARIOS', 'Permite crear y modificar usuarios del sistema'),
('REGISTRAR_ASAMBLEISTA', 'Permite registrar asambleistas'),
('CARGAR_NORMATIVA', 'Permite cargar reglamentos y elementos normativos'),
('GESTIONAR_PROPUESTAS', 'Permite crear y modificar propuestas'),
('EMITIR_CERTIFICACION', 'Permite emitir certificaciones oficiales');

INSERT INTO catalogo_tipo_sesion (nombre) VALUES ('Ordinaria'), ('Extraordinaria');
INSERT INTO catalogo_tipo_modalidad (nombre) VALUES ('Presencial'), ('Virtual'), ('Hibrida');
INSERT INTO catalogo_puestos (nombre_puesto) VALUES ('Presidente'), ('Secretaria'), ('Representante'), ('Suplente');
INSERT INTO catalogo_sector (nombre) VALUES ('Docente'), ('Administrativo'), ('Estudiantil'), ('Consejo Institucional');
INSERT INTO catalogo_nivel_reglamento (nombre) VALUES ('Titulo'), ('Capitulo'), ('Seccion'), ('Articulo'), ('Inciso'), ('Sub-inciso');
INSERT INTO catalogo_etapas_propuestas (nombre) VALUES ('Procedencia'), ('Aprobacion');
INSERT INTO catalogo_estado_propuestas (nombre) VALUES ('Pendiente'), ('Agendada'), ('En discusion'), ('Aprobada'), ('Rechazada'), ('Pospuesta');
INSERT INTO catalogo_tipo_comision (nombre) VALUES ('Permanente'), ('Temporal'), ('Especial');
INSERT INTO catalogo_rol_comision (nombre_rol) VALUES ('Coordinador'), ('Integrante'), ('Relator');
INSERT INTO catalogo_tipo_mayoria_requerida (nombre_rol) VALUES ('Simple'), ('Calificada 66%'), ('Unanimidad');
INSERT INTO catalogo_tipo_reforma (nombre) VALUES ('Adicion'), ('Modificacion'), ('Derogacion');
INSERT INTO catalogo_estado_vigencia (nombre) VALUES ('Vigente'), ('Historico'), ('Derogado');
INSERT INTO catalogo_tipo_tramite (nombre) VALUES ('Lectura'), ('Discusion'), ('Votacion'), ('Informe');
INSERT INTO catalogo_asistencia_sesion_comision (nombre) VALUES ('Presente'), ('Ausente'), ('Justificado');

INSERT INTO reglamento (nombre_normativa, sigla)
VALUES ('Estatuto Organico del ITCR', 'EOITCR');

INSERT INTO elemento_normativo (
    id_reglamento,
    id_elemento_padre,
    id_nivel_reglamento,
    numero_etiqueta,
    contenido_texto,
    orden,
    id_estado_vigencia
)
VALUES
(1, NULL, 1, 'Titulo I', 'Disposiciones generales', 1, 1),
(1, 1, 2, 'Capitulo I', 'Naturaleza institucional', 1, 1),
(1, 2, 4, 'Articulo 1', 'Texto base del articulo 1.', 1, 1),
(1, 3, 5, 'a)', 'Texto base del inciso a).', 1, 1),
(1, 4, 6, 'i.', 'Texto base del sub-inciso i.', 1, 1);