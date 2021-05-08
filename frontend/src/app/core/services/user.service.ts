import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CreateUserDTO, UserDTO, UserInfoDTO} from '@app/shared/models';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  getUserByJwt(): Observable<UserInfoDTO> {
    return this.http.get<UserInfoDTO>(`/api/v1/account`);
  }

  createUser(user: CreateUserDTO): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`/api/v1/register`, user);
  }

  verifyEmailKey(key: string): Observable<{ message: string }> {
    return this.http.get<{ message: string }>(`/api/v1/activate?key=${key}`);
  }

  verifyPhoneCode(code: number): Observable<{ message: string }> {
    return this.http.get<{ message: string }>(`/api/v1/verifyPhone?verificationCode=${code}`);
  }
}
