import { Component, OnDestroy } from '@angular/core';

import { Subscription } from 'rxjs';

import { VideoConverterService } from './shared/services/video-converter.service';
import { FileUploadService } from './shared/services/file-upload.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnDestroy {

  private _converterSubscription: Subscription;

  url: string | ArrayBuffer;

  constructor(
    private _service: FileUploadService,
    private _converterService: VideoConverterService
  ) { }

  onUpload(event): void {
    const blob: Blob = event.target.files && event.target.files[0];
    if (blob) {
      const fileInput = new FormData();
      fileInput.append('file', blob);
      this._converterSubscription = this._service.upload(fileInput).subscribe((file: Blob) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = async (e: ProgressEvent<FileReader>) => {
          this.url = await this._converterService.parse(e.target.result as string, 'webm');
        }
      });
    }
  }

  ngOnDestroy(): void {
    if (this._converterSubscription) this._converterSubscription.unsubscribe();
  }
}
