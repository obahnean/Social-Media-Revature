import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as AWS from 'aws-sdk/global';
import * as S3 from 'aws-sdk/clients/s3';
import { Observable, BehaviorSubject } from 'rxjs';
import { PostService } from './post.service';

@Injectable({
  providedIn: 'root'
})
export class UploadService {
  private bucket;

  // behavior subject
  private data;

  constructor(private httpCli: HttpClient) {
    this.bucket = new S3({
      accessKeyId: 'AKIASOPVMOQ2CMUJN3NH',
      secretAccessKey: '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',
      region: 'us-east-1'
    });
    this.data = new BehaviorSubject<string>(this.data);
    this.data = this.data.asObservable();
    this.data = this.data.toPromise();
   }

  doUpload(file) {
    const filename = 'https://socialitebucket.s3.amazonaws.com/' + file.name;
    const contentType = file.type;
    const params = {
      Bucket: 'socialitebucket',
      Key: file.name,
      Body: file,
      ACL: 'public-read',
      ContentType: contentType
    };
    this.bucket.upload(params, (err, res) => {
      console.log(res.location);
    });
    return filename;
  }
}
