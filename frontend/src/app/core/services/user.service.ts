import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ContactMessageDTO, CreateUserDTO, ResetPasswordDTO, UserInfoDTO} from '@app/shared/models';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  getUserByJwt(): Observable<UserInfoDTO> {
    return this.http.get<UserInfoDTO>(`/api/v1/account`);
  }

  getUserByUsername(username: string): Observable<UserInfoDTO> {
    return this.http.get<UserInfoDTO>(`/api/v1/user/${username}`);
  }

  getAllUsers(): Observable<UserInfoDTO[]> {
    return this.http.get<UserInfoDTO[]>(`/api/v1/users`);
  }

  updateUser(username: string, user: UserInfoDTO): Observable<UserInfoDTO> {
    return this.http.patch<UserInfoDTO>(`/api/v1/user/${username}`, user);
  }

  deleteUser(username: string): Observable<any> {
    return this.http.delete(`/api/v1/user/${username}`);
  }

  deleteManyUsers(userIds: Array<string>): Observable<any> {
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: userIds,
    };
    return this.http.delete(`/api/v1/user/bulk/delete`, options);
  }

  createUser(user: CreateUserDTO): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`/api/v1/register`, user);
  }

  patchUser(username: string, user: any): Observable<UserInfoDTO> {
    return this.http.patch<UserInfoDTO>(`/api/v1/user/${username}`, user);
  }

  activateUser(username: string): Observable<{ message: string }> {
    return this.http.get<{ message: string }>(`/api/v1/activate/${username}`);
  }

  verifyEmail(key: string): Observable<{ message: string }> {
    return this.http.get<{ message: string }>(`/api/v1/verifyEmail?key=${key}`);
  }

  verifyPhoneCode(code: number): Observable<{ message: string }> {
    return this.http.get<{ message: string }>(`/api/v1/verifyPhone?code=${code}`);
  }

  sendContactMessage(contactMessageDTO: ContactMessageDTO): Observable<{ message: string }> {
    return this.http.post<{ message: string }>('/api/v1/contact', contactMessageDTO);
  }

  requestPasswordReset(email: string): Observable<{ message: string }> {
    return this.http.post<{ message: string }>('/api/v1/reset-password/init', email);
  }

  finishPasswordReset(resetPasswordDTO: ResetPasswordDTO): Observable<{ message: string }> {
    return this.http.post<{ message: string }>('/api/v1/reset-password/finish', resetPasswordDTO);
  }
}
