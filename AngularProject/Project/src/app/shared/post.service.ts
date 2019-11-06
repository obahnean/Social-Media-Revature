import { Injectable, ɵCompiler_compileModuleAndAllComponentsSync__POST_R3__ } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Post } from '../models/Post';
import { UploadService } from './upload.service';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private selectedFile;
  private picture = '';

  constructor(private httpcli: HttpClient, private uploadServ: UploadService) { }

  getposts() {
    // https://localhost:8080/Project2/getPosts.MasterServlet
    // https://api.myjson.com/bins/kp1gl
    return this.httpcli.get<Post[]>('http://localhost:8080/Project2/getPosts.MasterServlet');
    // return this.posts;
  }
  updatePost(p: Post) {
    console.log(p);
    return this.httpcli.post('http://localhost:8080/Project2/updatePost.MasterServlet', p.postId, { withCredentials: true });
  }

  // sendPostWithImage(body, image) {
  //   this.picture = this.uploadServ.doUpload(image);
  //   console.log("Done waiting");
  //   //return this.picture.toPromise();
  // }

  // sendImgPost(body) {
  //   this.sendPost(body, this.picture).subscribe((data) => {
  //     console.log("In sendimgpost");
  //     console.log(data);
  //   });
  // }

  sendPost(body, image) {
    const payload = new HttpParams()
      .set('body', body)
      .set('image', image);
    const options = {withCredentials: true};
    return this.httpcli.post('http://localhost:8080/Project2/makePost.MasterServlet', payload, options);
  }
}
