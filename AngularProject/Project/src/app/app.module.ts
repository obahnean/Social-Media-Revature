import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { FeedComponent } from './feed/feed.component';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './navbar/navbar.component';
import { PostService } from './shared/post.service';
import { SideBarComponent } from './side-bar/side-bar.component';
import { ProfileInfoComponent } from './profile-info/profile-info.component';
import { ProfileServService } from './shared/profile-serv.service';
import { RouterModule } from '@angular/router';
import { FriendsComponent } from './friends/friends.component';
import { AuthGuard } from './util/Authguard';
import { RegisterComponent } from './register/register.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';

@NgModule({
  declarations: [
    AppComponent,
    FeedComponent,
    LoginComponent,
    ProfileInfoComponent,
    NavbarComponent,
    SideBarComponent,
    ChangePasswordComponent,
    FriendsComponent,
    RegisterComponent,
    EditProfileComponent,
    ForgotPasswordComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot([
      {path: 'profileInfo/:user', component: ProfileInfoComponent, canActivate: [AuthGuard]},
      {path: 'login', component: LoginComponent },
      {path: 'register', component: RegisterComponent },
      {path: 'feed', component: FeedComponent, canActivate: [AuthGuard] },
      {path: 'User', component: SideBarComponent, canActivate: [AuthGuard] },
      {path: 'change', component: ChangePasswordComponent, canActivate: [AuthGuard] },
      {path: 'edit', component: EditProfileComponent, canActivate: [AuthGuard] },
      {path: 'navbar', component: NavbarComponent, canActivate: [AuthGuard]},
      {path: 'friends', component: FriendsComponent, canActivate: [AuthGuard]},
      {path: 'forgot', component: ForgotPasswordComponent },
      {path: '', component: LoginComponent }
       ])
  ],
  providers: [PostService,  ProfileServService],
  bootstrap: [AppComponent]
})
export class AppModule { }
