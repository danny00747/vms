import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {ReplaySubject} from 'rxjs';
import {AuthentificationService, EToastSeverities, ToastService} from '@app/core/services';
import {LoginDTO, ResetPasswordDTO} from '@app/shared/models';
import {filter, takeUntil} from 'rxjs/operators';
import {MessageService} from 'primeng/api';
import {UserService} from '@app/core/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  pseudo: string;
  password: string;

  userEmail: string;
  emailKey: string;
  newPassword: string;
  confirmPassword: string;

  showResetPasswordSection = false;
  showResetPassword = false;

  verifiedEmailKey = false;
  completedPasswordReset = false;


  // Observable used to notify subscription when to end
  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);

  constructor(private router: Router,
              public toastService: ToastService,
              private userService: UserService,
              private readonly toast: ToastService,
              private messageService: MessageService,
              private authService: AuthentificationService) {
    this.router.events
      .pipe(takeUntil(this.destroyed$),
        filter(event => event instanceof NavigationStart))
      .subscribe(() => this.reload());
  }

  ngOnInit(): void {
  }

  onLoginSubmit(): void {
    const loginForm: LoginDTO = {
      pseudo: this.pseudo,
      password: this.password
    };

    // takeUntil is used to “short-circuit” the subscription when a value is emitted from the destroyed$ variable.
    this.authService.loginUser(loginForm)
      .pipe(takeUntil(this.destroyed$))
      .subscribe(
        async () => {
          this.toastService.show(EToastSeverities.INFO, 'Welcome back !');
          setTimeout(async () => await this.router.navigate(['/gallery']), 1000);
        },
        error => {
          console.error(error);
          this.toastService.show(EToastSeverities.ERROR, 'Authentification failed !');
        });
  }

  checkEmailKeyAndPassword(): boolean {
    return this.emailKey !== undefined && this.emailKey.length === 36
      && this.newPassword !== undefined && this.newPassword.length >= 4 && this.newPassword === this.confirmPassword;
  }

  displayResetPassword(): void {
    this.showResetPasswordSection = true;
  }

  requestPasswordReset(): void {
    this.userService.requestPasswordReset(this.userEmail)
      .subscribe(
        () => {
          this.showResetPassword = true;
          this.verifiedEmailKey = true;
          this.toastService.show(EToastSeverities.INFO, 'A verification key has been sent your email');
        },
        error => {
          console.error(error);
          this.toastService.show(EToastSeverities.ERROR, 'Something went wrong !');
        });
  }

  completePasswordReset(): void {
    const resetPasswordDTO: ResetPasswordDTO = {
      key: this.emailKey,
      newPassword: this.newPassword
    };
    this.userService.finishPasswordReset(resetPasswordDTO)
      .subscribe(
        () => {
          this.completedPasswordReset = true;
          this.showResetPasswordSection = false;
          this.toastService.show(EToastSeverities.SUCCESS, 'Reset completed !');
        },
        error => {
          console.error(error);
          this.toastService.show(EToastSeverities.ERROR, 'Something went wrong !');
        });
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  /*
  Stays subscribed until the component is about to be unload.
  A subject is used to notify a subscription to end
 */
  @HostListener('window:beforeunload')
  async ngOnDestroy(): Promise<any> {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }
}
