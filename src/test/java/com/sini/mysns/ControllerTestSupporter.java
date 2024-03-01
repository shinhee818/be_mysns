package com.sini.mysns;

import com.sini.mysns.global.config.security.AuthUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;

public abstract class ControllerTestSupporter {
    public MockedStatic<AuthUtil> authUtilMockedStatic;

    @BeforeEach
    public void before()
    {
        authUtilMockedStatic = mockStatic(AuthUtil.class);
        authUtilMockedStatic.when(AuthUtil::currentUserEmail).thenReturn(GlobalTestHelper.MASTER_EMAIL);
        authUtilMockedStatic.when(AuthUtil::currentUserId).thenReturn(GlobalTestHelper.MASTER_ID);
    }

    @AfterEach
    public void after()
    {
        authUtilMockedStatic.close();
    }
}
