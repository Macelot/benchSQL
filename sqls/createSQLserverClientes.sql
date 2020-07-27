USE [tempdb]
GO

/****** Object:  Table [dbo].[clientes]    Script Date: 11/18/2012 23:11:10 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[clientes](
	[id] [int] NOT NULL,
	[nome] [varchar](50) NULL,
	[endereco] [varchar](49) NULL,
	[versao] [int] NULL,
	[ativo] [int] NULL,
	[cidade] [int] NULL,
	[dataCadastro] [timestamp] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


--criando tablas no postgresql caso ela n‹o exista:grava = "--DROP DATABASE \"bench001\";\n"                    +"BEGIN;\n"                    +"SAVEPOINT crt;\n"                    + "CREATE DATABASE \"bench001\""                    + "  WITH OWNER = postgres"                    + "  ENCODING = \'UTF8\'"                    + "  TABLESPACE = pg_default"                    + "  LC_COLLATE = \'"+lc+"\'"                    + "  LC_CTYPE = \'"+lc+"\'"                    + "  CONNECTION LIMIT = -1;\n"                            +" if it generates error"                    +" ROLLBACK TO SAVEPOINT crt;\n"                    +" and continue\n"                    +" if there is no error\n"                    +" RELEASE SAVEPOINT crt\n"                            +" and continue \n"                    +" at the end COMMIT;";CREATE DATABASE "bench001"WITH OWNER = postgresENCODING = 'UTF8'TABLESPACE = pg_defaultLC_COLLATE = 'Portuguese_Brazil.1252'LC_CTYPE = 'Portuguese_Brazil.1252'CONNECTION LIMIT = -1;  

grava ="CREATE FUNCTION CriaBanco() RETURNS integer AS $$\n"                            +"DECLARE \n"                            +"foiCriada INTEGER :=0;\n"                            +"tabela RECORD;\n"                            +"BEGIN \n"                            +" SELECT INTO tabela tablename FROM pg_tables where tablename='empregado' and schemaname = ANY (current_schemas(true));\n"                            +" IF tabela.tablename IS NULL THEN \n"                            +"CREATE DATABASE \"bench001\""                            + "  WITH OWNER = postgres"                            + "  ENCODING = \'UTF8\'"                            + "  TABLESPACE = pg_default"                            + "  LC_COLLATE = \'"+lc+"\'"                            + "  LC_CTYPE = \'"+lc+"\'"                            + "  CONNECTION LIMIT = -1;\n"                            +" foiCriada = 1;\n"                            +" END IF;\n"                            +" RETURN foiCriada;\n"                            +" END;\n"                            +" $$ LANGUAGE plpgsql;";
