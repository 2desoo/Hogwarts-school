package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/info")
public class InfoController {

    /*
    Added parameters
     */
    @Value("${server.port}")
    private int port;

    /*
    Get info for port
     */
    @GetMapping(path = "/port")
    public ResponseEntity<Integer> getPort() {
        return ResponseEntity.ok(port);
    }

    /*
    Test
     */
    @GetMapping("test")
    public ResponseEntity<Long> test() {
        Long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a +1).parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );
        Long time = System.currentTimeMillis() - start;
        return ResponseEntity.ok(time);
    }

}
