import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, never } from 'rxjs';


interface User {
  'userId': string;
  'username': string;
  'fname': string;
  'lname': string;
  'email': string;
}

@Injectable({
  providedIn: 'root'
})

export class ProfileServService {
  private path = 'http://localhost:8080/Project2/getAllUsers.MasterServlet'; // Path to json goes here.
  private url = 'http://localhost:8080/Project2/updatPswd.MasterServlet';
  private edit = 'http://localhost:8080/Project2/updateProfile.MasterServlet';
  private getEm = 'http://localhost:8080/Project2/userJSON.MasterServlet';
  private reset =  'http://localhost:8080/Project2/resetPswd.MasterServlet';
  constructor(private httCli: HttpClient) { }

  getUser(): Observable<User> {
    const cred = {withCredentials: true};
    return this.httCli.get<User>(this.getEm, cred);
  }
  // updating password from profile
  updatePswd(uname: string, newPword: string, oldPword: string): Observable<boolean> {
    const payload = new HttpParams()
    .set('username', uname)
    .set('password', newPword)
    .set('oldPassword', oldPword);
    const success = this.httCli.post<any>(this.url, payload);
    return success;
  }
  // resetting password from login
  resetPswd(email: string): Observable<boolean> {
    const reset = new HttpParams().set('email', email);
    const sentEmail = this.httCli.post<any>(this.url, reset);
    return sentEmail;
  }
  // Edit profile
  editProfile(username: string, password: string, firstName: string, lastName: string, email: string): Observable<boolean> {
    const editUser = new HttpParams()
    .set('username', username)
    .set('password', password)
    .set('firstName', firstName)
    .set('lastName', lastName)
    .set('email', email);
    const profileSuccess = this.httCli.post<any>(this.edit, editUser);
    return profileSuccess;
  }
// forgot password reset
forgotPassword(username: string, email: string) {
  const forgot = new HttpParams()
  .set('username', username)
  .set('email', email);
  const forgotEmail = this.httCli.post<any>(this.reset, forgot);
  return forgotEmail;
}

}
