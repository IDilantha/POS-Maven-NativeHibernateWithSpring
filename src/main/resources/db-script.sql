
                       CREATE TABLE `Customer` (\n" +
                        "  `customerId` varchar(10) NOT NULL,\n" +
                        "  `name` varchar(15) DEFAULT NULL,\n" +
                        "  `address` varchar(20) DEFAULT NULL,\n" +
                        "  PRIMARY KEY (`customerId`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n" +
                        "\n" +
                        "CREATE TABLE `Item` (\n" +
                        "  `code` varchar(10) NOT NULL,\n" +
                        "  `description` varchar(30) DEFAULT NULL,\n" +
                        "  `unitPrice` decimal(6,2) DEFAULT NULL,\n" +
                        "  `qtyOnHand` int(5) DEFAULT NULL,\n" +
                        "  PRIMARY KEY (`code`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n" +
                        "\n" +
                        "CREATE TABLE `Order` (\n" +
                        "  `id` int(11) NOT NULL,\n" +
                        "  `date` date DEFAULT NULL,\n" +
                        "  `customerId` varchar(10) DEFAULT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  KEY `FKckicmtwun913u69pha7agsgpd` (`customerId`),\n" +
                        "  CONSTRAINT `FKckicmtwun913u69pha7agsgpd` FOREIGN KEY (`customerId`) REFERENCES `Customer` (`customerId`),\n" +
                        "  CONSTRAINT `Order_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `Customer` (`customerId`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n" +
                        "\n" +
                        "CREATE TABLE `OrderDetail` (\n" +
                        "  `orderId` int(11) NOT NULL,\n" +
                        "  `itemCode` varchar(10) NOT NULL,\n" +
                        "  `qty` int(5) DEFAULT NULL,\n" +
                        "  `unitPrice` decimal(6,2) DEFAULT NULL,\n" +
                        "  PRIMARY KEY (`orderId`,`itemCode`),\n" +
                        "  KEY `FKtogd3d0kvb3mreeh4pn7qox19` (`itemCode`),\n" +
                        "  CONSTRAINT `FK21x4a3ee3h5uwombp0n7cayng` FOREIGN KEY (`orderId`) REFERENCES `Order` (`id`),\n" +
                        "  CONSTRAINT `FKtogd3d0kvb3mreeh4pn7qox19` FOREIGN KEY (`itemCode`) REFERENCES `Item` (`code`),\n" +
                        "  CONSTRAINT `OrderDetail_ibfk_2` FOREIGN KEY (`itemCode`) REFERENCES `Item` (`code`),\n" +
                        "  CONSTRAINT `OrderDetail_ibfk_3` FOREIGN KEY (`orderId`) REFERENCES `Order` (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=latin1;\n";
