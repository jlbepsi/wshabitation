package devops.kilroywashere.wshabitation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "/integrationtest.properties")
class WshabitationApplicationTests {

    @Test
    void contextLoads() {
    }

}
