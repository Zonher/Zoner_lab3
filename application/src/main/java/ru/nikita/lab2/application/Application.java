package ru.nikita.lab2.application;

import ru.nikita.lab2.application.command.BaseCommand;
import ru.nikita.lab2.application.command.CommandParser;
import ru.nikita.lab2.application.command.CommandProcessor;
import ru.nikita.lab2.dao.repository.AccountRepository;
import ru.nikita.lab2.dao.repository.UserRepository;
import ru.nikita.lab2.dao.repository.impl.AccountRepositoryImpl;
import ru.nikita.lab2.dao.repository.impl.UserRepositoryImpl;
import ru.nikita.lab2.service.AccountCRUDService;
import ru.nikita.lab2.service.FriendService;
import ru.nikita.lab2.service.OperationService;
import ru.nikita.lab2.service.UserCRUDService;
import ru.nikita.lab2.service.impl.AccountServiceImpl;
import ru.nikita.lab2.service.impl.FriendServiceImpl;
import ru.nikita.lab2.service.impl.OperationServiceImpl;
import ru.nikita.lab2.service.impl.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Application {

    private static final String STOP_WORD = "stop";

    private final BufferedReader reader;

    private final CommandParser commandParser;

    private final CommandProcessor commandProcessor;

    private boolean stop;

    public Application() {
        this.reader = new BufferedReader(
                new InputStreamReader(System.in)
        );

        UserRepository userRepository =
                new UserRepositoryImpl();

        AccountRepository accountRepository =
                new AccountRepositoryImpl();

        UserCRUDService userService =
                new UserServiceImpl(
                        userRepository
                );

        FriendService friendService =
                new FriendServiceImpl(
                        userRepository
                );

        AccountCRUDService accountService =
                new AccountServiceImpl(
                        accountRepository,
                        userRepository
                );

        OperationService operationService =
                new OperationServiceImpl(
                        accountRepository
                );

        this.commandParser =
                new CommandParser();

        this.commandProcessor =
                new CommandProcessor(
                        userService,
                        friendService,
                        accountService,
                        operationService
                );
    }

    public void start() throws IOException {
        readme();

        while (!stop) {
            String line = reader.readLine();

            if (line == null
                    || STOP_WORD.equalsIgnoreCase(
                    line.trim()
            )) {

                terminate();
                continue;
            }

            try {
                BaseCommand command =
                        commandParser.parseCommand(line);

                commandProcessor.process(command);

            } catch (Exception ex) {
                System.err.println(
                        "Error: " + ex.getMessage()
                );
            }
        }
    }

    public void terminate() {
        stop = true;
    }

    private void readme() {
        System.out.println(
                "Для выхода введите: stop"
        );

        System.out.println(
                "Формат: <command> <JSON>"
        );

        System.out.println(
                "Кавычки экранировать НЕ нужно."
        );

        System.out.println(
                "Пример:"
        );

        System.out.println(
                "createUser "
                        + "{\"login\":\"nikita\","
                        + "\"name\":\"Nikita\","
                        + "\"age\":20,"
                        + "\"gender\":\"MALE\","
                        + "\"hairColor\":\"BLACK\"}"
        );

        System.out.println();

        System.out.println("Команды:");

        System.out.println(
                "createUser, updateUser, "
                        + "deleteUser, readUserInfo"
        );

        System.out.println(
                "addFriend, deleteFriend"
        );

        System.out.println(
                "createAccount, deleteAccount, "
                        + "readAccountInfo, showHistory"
        );

        System.out.println(
                "deposit, withdraw, transfer"
        );

        System.out.println();
    }
}