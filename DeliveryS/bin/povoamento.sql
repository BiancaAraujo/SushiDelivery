INSERT INTO `DBD`.`Cliente` (`cpf`, `nome_cliente`, `endereco`, `telefone`, `pontos_fedelidade`) VALUES ('12345678901', 'Fulano de Tal', 'Rua Amarga', '12345678', '100');
INSERT INTO `DBD`.`Cliente` (`cpf`, `nome_cliente`, `endereco`, `telefone`, `pontos_fedelidade`) VALUES ('23456789012', 'Fulano da Silva', 'Rua Salgada', '23456789', '0');
INSERT INTO `DBD`.`Empresa` (`empresa_Id`, `caixa`, `nome_usuario`, `senha_usuario`) VALUES ('1', '0,00', 'Atendente', 'atender');
INSERT INTO `DBD`.`Item_Cardapio` (`nome_item`, `preco`, `popularidade`, `pontos_fedelidade_item`, `ingredientes`, `disponibilidade`, `a_venda`) VALUES ('Sushi vegetariano', '90,99', '0', '80', 'Pura alga', 1, 1);
INSERT INTO `DBD`.`Item_Cardapio` (`nome_item`, `preco`, `popularidade`, `pontos_fedelidade_item`, `ingredientes`, `disponibilidade`, `a_venda`) VALUES ('Sushi ultrapassado', '5,55', '2', '40', 'Peixe com peixe e peixe', 1, 0);
INSERT INTO `DBD`.`Item_Cardapio` (`nome_item`, `preco`, `popularidade`, `pontos_fedelidade_item`, `ingredientes`, `disponibilidade`, `a_venda`) VALUES ('Sushi de ouro', '199,99', '10', '160', 'Ouro de mentira', 1, 1);
INSERT INTO `DBD`.`Item_Cardapio` (`nome_item`, `preco`, `popularidade`, `pontos_fedelidade_item`, `ingredientes`, `disponibilidade`, `a_venda`) VALUES ('Sushi de papel', '0,00', '30', '0', 'Papel toalha e cartão', 0, 1);
INSERT INTO `DBD`.`Entrada` (`nome_item`) VALUES ('Sushi de papel');
INSERT INTO `DBD`.`Principal` (`nome_item`) VALUES ('Sushi de ouro');
INSERT INTO `DBD`.`Sobremesa` (`nome_item`) VALUES ('Sushi ultrapassado');
INSERT INTO `DBD`.`Bebida` (`nome_item`) VALUES ('Sushi vegetariano');