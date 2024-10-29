package com.login;

import com.login.model.User;
import com.login.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("dev")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = User.builder()
                .name("user")
                .userId("test")
                .userPassword("test")
                .validityDays(3)
                .build();

        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);
        System.out.println("Saved User: " + savedUser);
    }
}
