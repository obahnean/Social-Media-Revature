import { Component, OnInit } from '@angular/core';
import { PostService } from '../shared/post.service';
import { Post } from '../models/Post';
import { LoginService } from '../shared/login.service';
import { Router } from '@angular/router';
import { UploadService } from '../shared/upload.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
  pagenum = 0;
  pagemax = 20;
  posts: Post[];
  page: Post[];
  perpage = 10;
  collectionSize;

  private newPostBody = '';
  private selectedFile;

  constructor(private postService: PostService, private loginserv: LoginService, private uploadServ: UploadService) {
  }

  onFileSelected(event) {
    this.selectedFile = event.target.files[0] as File;
    console.log(this.selectedFile);
  }

  ngOnInit() {

    this.postService.getposts().subscribe(
      data => {
        this.posts = data;
        for (const p of this.posts) {
          p.timestamp = new Date(p.timestamp).toLocaleString();
        }
        this.pagemax = this.posts.length;
        this.setpage();
        this.collectionSize = this.posts.length;
      }
    );

  }

  decrement() {
    if (this.pagenum > 0) {
      this.pagenum--;
      this.setpage();
    }

  }
  likepost(p: Post) {
    console.log(p.likes);
    if (!(p.likes.indexOf(this.loginserv.getCurrentUser()) > -1)) {
      console.log('made it to the push and update');
      this.postService.updatePost(p).subscribe(data => {
        console.log(data);
        if (data) {
          p.likes.push(this.loginserv.getCurrentUser());
          //
        } else {
          // Set error message
        }
      });
    }
  }
  increment() {
    if (this.pagenum < this.pagemax - 1) {
      this.pagenum++;
      this.setpage();
    }
  }
  setpage() {
    let start = this.pagenum * this.pagenum;
    let end = start + this.perpage;
    this.page = [];
    for (start; start < end; start++) {
      this.page.push(this.posts[start]);
    }
  }

  // Send over a new post
  submitPost() {
    console.log('Submitting post...')
    // Call service and send post
    if (this.newPostBody.length <= 250 && this.newPostBody !== '') {
      if (this.selectedFile) {
        this.postService.sendPost(this.newPostBody, this.uploadServ.doUpload(this.selectedFile)).subscribe(data => {
          console.log(data);
        });
      } else {
        this.postService.sendPost(this.newPostBody, null).subscribe(data => {
          console.log(data);
        });
      }
    } else {
      // Set error message in post modal
      console.log("I ain't sending that shit");
    }
  }
}
