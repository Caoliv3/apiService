CREATE TABLE IF NOT EXISTS book (
  id              INT     NOT NULL PRIMARY KEY,
  autor       	  VARCHAR(200) NOT NULL,
  titulo          VARCHAR(200) NOT NULL,
  categoria       VARCHAR(200) NOT NULL,
  preco			  DECIMAL(20, 2),
  dataCadastro	  DATE,
  ativo			  BOOLEAN
);