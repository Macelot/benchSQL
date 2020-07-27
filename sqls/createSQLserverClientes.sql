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


--criando tablas no postgresql caso ela n�o exista:

grava ="CREATE FUNCTION CriaBanco() RETURNS integer AS $$\n"