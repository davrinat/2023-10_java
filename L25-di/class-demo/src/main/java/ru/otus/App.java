package ru.otus;

import ru.otus.services.*;

@SuppressWarnings({"squid:S125", "squid:S106"})
public class App {
    public static void main(String[] args) {
        EquationPreparer equationPreparer = new EquationPreparerImpl();
        IOService ioService = new IOServiceStreams(System.out, System.in); // NOSONAR
        PlayerService playerService = new PlayerServiceImpl(ioService);
        GameProcessor gameProcessor = new GameProcessorImpl(ioService, equationPreparer, playerService);

        // ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring-context.xml");
        // ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        // GameProcessor gameProcessor = ctx.getBean(GameProcessor.class);

        gameProcessor.startGame();
    }
}
