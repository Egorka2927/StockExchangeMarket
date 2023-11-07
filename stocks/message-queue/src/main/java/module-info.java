module messagequeue {
    requires static lombok;
    requires org.mockito;
    requires com.google.gson;
    exports nl.rug.aoop.messagequeue.Queues to stock.exchange;
    exports nl.rug.aoop.messagequeue.Messages to stock.exchange;
    exports nl.rug.aoop.messagequeue.Users.Producers to stock.exchange;
    exports nl.rug.aoop.messagequeue.Users.Consumers to stock.exchange;
    exports nl.rug.aoop.messagequeue.Commands to stock.exchange;
    exports nl.rug.aoop.messagequeue.Commands.CommandFactory to stock.exchange;
    requires networking;
    requires command;
}