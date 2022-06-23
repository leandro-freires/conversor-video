package com.github.leandro.converterapi.services;

import com.github.leandro.converterapi.models.dtos.FileMetadataDto;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.VideoSize;

import java.io.File;
import java.io.IOException;

@Service
public class FileService {

    public FileService() { }

    public FileMetadataDto parse(MultipartFile file) {
        File source;
        File target;

        try {
            source = File.createTempFile("source", ".avi");
            target = File.createTempFile("target", ".webm");
            file.transferTo(source);
        } catch (IllegalStateException | IOException e) {
            throw new RuntimeException(e);
        }

        EncodingAttributes encodingAttributes = new EncodingAttributes();
        encodingAttributes.setOutputFormat("webm");
        encodingAttributes.setAudioAttributes(getAudioAttributes());
        encodingAttributes.setVideoAttributes(getVideoAttributes());

        ByteArrayResource resource;

        try {
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, encodingAttributes);
            resource = new ByteArrayResource(FileCopyUtils.copyToByteArray(target));
        } catch (IOException | EncoderException e) {
            throw new RuntimeException(e);
        }

        return FileMetadataDto.builder().resource(resource).target(target).build();
    }

    private AudioAttributes getAudioAttributes() {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libvorbis");
        audio.setBitRate(327680);
        audio.setChannels(2);
        audio.setSamplingRate(44100);
        return audio;
    }
    private VideoAttributes getVideoAttributes() {
        VideoAttributes video = new VideoAttributes();
        video.setCodec("libvpx-vp9");
        video.setBitRate(409600);
        video.setFrameRate(30);
        video.setSize(new VideoSize(320, 240));
        return video;
    }

}
