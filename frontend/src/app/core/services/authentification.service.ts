import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {JWT, LoginDTO} from '@app/shared/models';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {

  private currentUserSubject: BehaviorSubject<any>;
  private currentTokenSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;
  public currentToken: Observable<any>;

  constructor(private http: HttpClient,
              private jwtService: JwtHelperService) {
    this.currentUserSubject =
      new BehaviorSubject<any>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();

    this.currentTokenSubject =
      new BehaviorSubject<any>(localStorage.getItem('currentUserRole'));
    this.currentToken = this.currentTokenSubject.asObservable();
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  public get currentTokenRoleValue(): string {
    return this.currentTokenSubject.value;
  }

  loginUser(loginData: LoginDTO): Observable<HttpResponse<JWT>> {
    return this.http.post<any>('/api/v1/authenticate', loginData, {observe: 'response'})
      .pipe(map((data: any) => {
        // login successful if there's a jwt token in the response
        if (data.status === 200) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(data.body));
          this.currentUserSubject.next(data.body);

          // = Roles
          const payload = this.jwtService.decodeToken(data.body?.token);
          localStorage.setItem('currentUserRole', payload.roles);
          this.currentTokenSubject.next(payload.roles);
        }
        return data;
      }));
  }

  logout(): void {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);

    // remove token from local storage
    localStorage.removeItem('currentUserRole');
    this.currentTokenSubject.next(null);
  }

}
