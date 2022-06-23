package com.github.leandro.converterapi.models.dtos;

import lombok.Builder;
import lombok.Getter;
import org.springframework.core.io.ByteArrayResource;

import java.io.File;

@Builder
@Getter
public class FileMetadataDto {

    ByteArrayResource resource;

    File target;

}
