DROP TABLE IF EXISTS TABELA_PROTESTO;

CREATE TABLE TABELA_PROTESTO (
  idprotestos INT NOT NULL,
  documento INT NULL,
  tipo_documento TINYINT(1) NULL,
  id_codigo_cartorio INT(11) NULL,
  data_protesto CHAR(10) NULL,
  data_vencimento CHAR(10) NULL,
  valor_protestado CHAR(20) NULL,
  uf_devedor CHAR(2) NULL,
  especie CHAR(10) NULL,
  data_inclusao CHAR(10) NULL,
  PRIMARY KEY (idprotestos));