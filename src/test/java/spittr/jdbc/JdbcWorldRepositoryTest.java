package spittr.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spittr.db.jdbc.JdbcWorldRepository;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=JdbcMysqlConfig.class)
@ActiveProfiles("dev")
public class JdbcWorldRepositoryTest {
    @Autowired
    private JdbcWorldRepository worldRepository;

    @Test
    public void count() {
        assertEquals(4079, worldRepository.count());
    }
}
