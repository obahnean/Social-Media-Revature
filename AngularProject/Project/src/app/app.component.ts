import { Component } from '@angular/core';
import { User } from './models/User';
import { LoginService } from './shared/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Project2';

  public currentUser: User;

  constructor(public loginServ: LoginService) {
    this.loginServ.currentUser.subscribe(data => this.currentUser = data);
  }

  ngOnInit() {
    this.currentUser = this.loginServ.getCurrentUser();
    console.log(this.currentUser);
  }
}
