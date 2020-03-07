package com.in28minutes.springboot;

import com.in28minutes.springboot.entity.User;
import com.in28minutes.springboot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCommandLineRunner.class);
    private final UserRepository userRepository;

    @Autowired
    public UserCommandLineRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("Yaser", "Admin"));
        userRepository.save(new User("Saman", "User"));
        userRepository.save(new User("Elahe", "Admin"));
        userRepository.save(new User("Minoo", "User"));

        for (User user : userRepository.findAll()) {
            LOGGER.info(user.toString());
        }

        LOGGER.info("Admin users are:");
        for (User user: userRepository.findByRole("Admin")){
            LOGGER.info(user.toString());
        }
    }
}
