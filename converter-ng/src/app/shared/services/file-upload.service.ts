import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { VideoConverterService } from './video-converter.service';


@Injectable({
  providedIn: "root"
})
export class FileUploadService {

  constructor(
    private _http: HttpClient,
    private _converterService: VideoConverterService
  ) { }

  upload(form: FormData): Observable<Blob> {
    return this._http.post('api/file/upload', form, { responseType: 'blob'});
  }

}
