package ru.hogwarts.school.service;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.entity.Avatar;

import java.io.IOException;
/*
Methods for the service
 */
public interface AvatarService {

    void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException;

    Avatar findAvatar(long studentId);

    Page<Avatar> getAvatarsByPage(int pageNumber, int pageSize);
}