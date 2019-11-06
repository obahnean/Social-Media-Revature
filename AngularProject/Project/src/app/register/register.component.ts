import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../shared/login.service';
import { UploadService } from '../shared/upload.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  private username = '';
  private pword = '';
  private confirm_pword = '';
  private firstname = '';
  private lastname = '';
  private email = '';
  private selectedFile;
  private picture = '';
  fail = false;

  // private setPic = function(err, data) {
  //   //console.log(data);
  //   this.picture = data.Location;
  //   console.log(this.picture);
  //   return data.Location;
  // }

  constructor(private loginServ: LoginService, private router: Router, private uploadServ: UploadService) { }

  ngOnInit() {
  }

  onFileSelected(event){
    this.selectedFile = event.target.files[0] as File;
    console.log(this.selectedFile);
  }

  onPress() {
    this.uploadFile(this.selectedFile);
  }

  uploadFile(file) {
    this.picture = this.uploadServ.doUpload(file);
    this.register();
  }

  register() {
    if (this.pword === this.confirm_pword) {
      this.loginServ.doRegister(this.username, this.pword, this.firstname, this.lastname, this.email, this.picture).subscribe(data => {
        if (data['success']) {
          this.router.navigate(['login']);
        } else {
          this.fail = true;
        }
      });

    } else {
      console.log("Screw you buddy");
    }
  }

}
