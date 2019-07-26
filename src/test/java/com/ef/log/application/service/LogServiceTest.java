package com.ef.log.application.service;

import com.ef.log.domain.repository.CommentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringJUnit4ClassRunner.class)
public class LogServiceTest {

    @Test
    public void logLoadTest() {

        LogService logService = Mockito.mock(LogService.class);
        Mockito.when(logService.load(anyString())).thenReturn(new Long(116484));
        Long result = logService.load("path/to/file");

        assertThat(result).isEqualTo(116484);
    }

    @Test
    public void commentLogTest() {

        // mocked arguments is: --accesslog=C:\Users\GeeGoG\Documents\projects\Java_MySQL_Test\access.log --startDate=2017-01-01.13:00:00 --duration=daily --threshold=250

        LogService logService = Mockito.mock(LogService.class);
        Mockito.when(logService.load(anyString())).thenReturn(new Long(116484));
        Mockito.when(logService.getRequests(any())).thenReturn(12);

        Long result = logService.load("path/to/file");
        int reqResult = logService.getRequests();

        assertThat(result).isEqualTo(116484);
        assertThat(reqResult).isEqualTo(12);
    }

}
