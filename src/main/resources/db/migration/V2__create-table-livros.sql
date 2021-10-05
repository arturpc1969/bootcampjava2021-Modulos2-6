CREATE TABLE `livros` (
  `livroId` bigint NOT NULL AUTO_INCREMENT,
  `dataDeLancamento` date NOT NULL,
  `numeroDePaginas` int NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `autorId` bigint NOT NULL,
  PRIMARY KEY (`livroId`),
  FOREIGN KEY (`autorId`) REFERENCES `autores` (`autorId`)
);