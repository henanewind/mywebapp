package spittr.db.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import spittr.db.WorldRepository;

public class JdbcWorldRepository implements WorldRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcWorldRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("SELECT count(ID) from city", Long.class);
    }
}
