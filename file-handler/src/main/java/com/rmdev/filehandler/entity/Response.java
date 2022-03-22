package com.rmdev.filehandler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;
}
