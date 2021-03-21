import {Component, HostListener, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {ReplaySubject} from 'rxjs';
import {AuthentificationService, EToastSeverities} from '@app/core/services';
import {ToastService} from '@app/core/services';
import {LoginDTO} from '@app/shared/models';
import {filter, takeUntil} from 'rxjs/operators';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  pseudo: string;
  password: string;

  // Observable used to notify subscription when to end
  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);

  constructor(private router: Router,
              public toastService: ToastService,
              private readonly toast: ToastService,
              private messageService: MessageService,
              private authService: AuthentificationService) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationStart))
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
          await this.router.navigate(['/gallery']);
          this.reload();
        },
        error => {
          console.error(error);
          // this.toast.show(EToastSeverities.ERROR, 'Authenticate failed !');
          this.toastService.show(EToastSeverities.ERROR, 'Authentification failed !');
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
