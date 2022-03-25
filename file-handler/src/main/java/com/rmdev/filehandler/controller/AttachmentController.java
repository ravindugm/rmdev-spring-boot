package com.rmdev.filehandler.controller;

import com.rmdev.filehandler.entity.Attachment;
import com.rmdev.filehandler.entity.Response;
import com.rmdev.filehandler.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class AttachmentController {

    // Upload files to the database

    private AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public Response uploadFile(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        Attachment attachment = null;
        String downloadURL = null;

        attachment = attachmentService.saveAttachment(multipartFile);
        downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(attachment.getId())
                .toUriString();

        return new Response(attachment.getFileName(),
                downloadURL,
                multipartFile.getContentType(),
                multipartFile.getSize());

    }

    @GetMapping("download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        Attachment attachment = null;

        attachment = attachmentService.getAttachment(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName() + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }

}
