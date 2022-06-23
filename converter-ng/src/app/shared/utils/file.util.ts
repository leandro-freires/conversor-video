export class FileUtil {

    constructor() { }

    static isBase64(data: string): boolean {
        if (!!data && /^\s*$/.test(data)) return false;

        try {
            const stringEncoded = data.split(',')[1];
            return  btoa(atob(stringEncoded)) === stringEncoded;
        } catch (e) {
            return false;
        }
    }

    static getBlobFromBase64Data(b64Data: string, contentType: string, sliceSize: number = 512): Blob {
        const byteCharacters = atob(b64Data);
        const byteArrays = [];

        for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
            const slice = byteCharacters.slice(offset, offset + sliceSize);
            const byteNumbers = new Array(slice.length);

            for (let i = 0; i < slice.length; i++) {
                byteNumbers[i] = slice.charCodeAt(i);
            }

            byteArrays.push(new Uint8Array(byteNumbers));
        }

        return new Blob(byteArrays, { type: contentType });
    }
}
