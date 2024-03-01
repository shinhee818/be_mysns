package com.sini.mysns;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@Sql("/member.sql")
@WithUserDetails(
        value = GlobalTestHelper.MASTER_EMAIL,
        setupBefore = TestExecutionEvent.TEST_EXECUTION,
        userDetailsServiceBeanName = "customUserDetailsService"
)
@SpringBootTest
@ActiveProfiles("test")
public abstract class IntegrationTestSupporter {
}
