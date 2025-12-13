import {Injectable} from '@angular/core';
import {Camera, CameraResultType, CameraSource} from "@capacitor/camera";

@Injectable({
  providedIn: 'root'
})
export class CameraService {

  async pickImage(): Promise<File | null>{
    try{
      const image = await Camera.getPhoto({
        quality:90,
        allowEditing: true,
        resultType: CameraResultType.DataUrl,
        source: CameraSource.Prompt
      });

      if(image && image.dataUrl){
        const blob = this.dataURLtoBlob(image.dataUrl);
        return new File([blob], 'profile_' + Date.now() + '.png', {type: blob.type});
      }
      return null;
    }catch (err){
      return null;
    }
  }

  private dataURLtoBlob(dataurl: string): Blob {
    const arr = dataurl.split(',');
    const mime = arr[0].match(/:(.*?);/)?.[1] ?? 'image/png';
    const bstr = atob(arr[1]);
    let n = bstr.length;
    const u8arr = new Uint8Array(n);
    while (n--) u8arr[n] = bstr.charCodeAt(n);
    return new Blob([u8arr], { type: mime });
  }

}
