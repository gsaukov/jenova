package com.pro.jenova.data.repository;

import com.pro.jenova.data.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.pro.jenova.util.generator.UniqueId.uuid;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveCustomer() {
        String username = uuid();

        User persistedUser = userRepository.save(new User.Builder().withUsername(username).withPassword(uuid()).build());

        Optional<User> lookedUpUser = userRepository.findByUsername(username);

        assertTrue(lookedUpUser.isPresent());
        assertEquals(persistedUser, lookedUpUser.get());
    }

}
