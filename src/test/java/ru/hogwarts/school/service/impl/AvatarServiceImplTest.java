package ru.hogwarts.school.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.IOException;
import static org.mockito.Mockito.*;

public class AvatarServiceImplTest {

    @Mock
    private AvatarRepository avatarRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private AvatarServiceImpl avatarService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testUploadAvatar() throws IOException {
        Long studentId = 1L;
        String avatarsDir = "/path/to/avatars";
        String originalFileName = "avatar.jpg";
        byte[] content = "image data".getBytes();
        MockMultipartFile multipartFile = new MockMultipartFile(originalFileName, content);

        Student student = new Student();
        student.setId(studentId);
        when(studentRepository.getById(studentId)).thenReturn(student);

        Avatar savedAvatar = new Avatar();
        when(avatarRepository.save(any())).thenReturn(savedAvatar);

        avatarService.uploadAvatar(studentId, multipartFile);

        verify(studentRepository, times(1)).getById(studentId);
        verify(avatarRepository, times(1)).save(any());

        assertEquals(avatarsDir + "/" + student + ".jpg", savedAvatar.getFilePath());
        assertEquals(content.length, savedAvatar.getFileSize());
        assertEquals("image/jpeg", savedAvatar.getMediaType());
        assertArrayEquals(content, savedAvatar.getData());
    }
}