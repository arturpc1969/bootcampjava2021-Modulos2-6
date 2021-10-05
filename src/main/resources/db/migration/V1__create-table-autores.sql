CREATE TABLE `autores` (
  `autorId` bigint NOT NULL AUTO_INCREMENT,
  `dataDeNascimento` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `miniCurriculo` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`autorId`)
);