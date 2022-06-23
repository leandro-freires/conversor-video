import { Injectable } from '@angular/core';

import { FileUtil } from '../utils/file.util';


@Injectable({
  providedIn: 'root'
})
export class VideoConverterService {

  constructor() { }

  parse(data: string, targetFormat: 'mp4' | 'webm' = 'mp4'): Promise<string | ArrayBuffer> {
    return this.convert(data, targetFormat);
  }

  private convert(data: string, targetFormat: 'mp4' | 'webm'): Promise<string | ArrayBuffer> {
    return new Promise((resolve, reject) => {
      try {
        if (!FileUtil.isBase64(data)) reject(new Error('Invalid format'));
        const contentType = `video/${targetFormat}`;
        const b64Data = data.split(',')[1];
        const blob = FileUtil.getBlobFromBase64Data(b64Data, contentType);
        const reader = new FileReader();
        reader.onload = () => resolve(reader.result);
        reader.readAsDataURL(blob);
      } catch (e) {
        reject(e);
      }
    });
  }

}
